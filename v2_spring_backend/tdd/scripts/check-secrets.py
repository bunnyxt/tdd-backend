#!/usr/bin/env python3
"""Reject real credentials in committed config and packaged JARs.

Prints key names and classifications only. Never prints property values.
"""

from __future__ import print_function

import argparse
import os
import re
import sys
import zipfile

if sys.version_info[0] < 3:
    raise SystemExit("python3 required")

SECRET_KEYS = (
    "tdd.mail.username",
    "tdd.mail.password",
    "tdd.mail.smtp.host",
    "tdd.recaptcha.secret",
    "tdd.auth.salt",
)

RETIRED_PREFIXES = ("tdd.acs.",)

LOCAL_DATASOURCE_URL_NEEDLE = "localhost"
ALLOWED_DATASOURCE_USERNAMES = frozenset({"root", ""})
ALLOWED_DATASOURCE_PASSWORDS = frozenset({"root", ""})

PACKAGED_PROPERTIES = "BOOT-INF/classes/application.properties"
FORBIDDEN_PACKAGED = (
    "BOOT-INF/classes/application-local.properties",
    "application-local.properties",
)

# Field or local assignment of a credential-named variable to a string literal.
# Does not match URL query concatenation such as `?secret="+SECRET`.
JAVA_ASSIGNMENT = re.compile(
    r"""^\s*(?:(?:public|protected|private|static|final)\s+)*(?:[\w.]+\s+)?(\w+)\s*=\s*["']([^"']+)["']""",
    re.MULTILINE,
)
CREDENTIAL_NAME = re.compile(
    r"(secret|password|salt|access[_-]?key|mailfrom|mail_host|username)$",
    re.IGNORECASE,
)


def parse_properties(text):
    props = {}
    for raw in text.splitlines():
        line = raw.strip()
        if not line or line.startswith("#") or "=" not in line:
            continue
        key, value = line.split("=", 1)
        props[key.strip()] = value
    return props


def fail(message, errors):
    errors.append(message)


def check_properties(props, source, errors):
    for key in SECRET_KEYS:
        value = props.get(key)
        if value is None:
            fail("%s missing required empty secret key %s" % (source, key), errors)
        elif value != "":
            fail("%s secret key %s must be empty" % (source, key), errors)

    for key in props:
        for prefix in RETIRED_PREFIXES:
            if key.startswith(prefix):
                fail("%s contains retired secret key %s" % (source, key), errors)

    url = props.get("spring.datasource.url", "")
    if LOCAL_DATASOURCE_URL_NEEDLE not in url:
        fail(
            "%s spring.datasource.url must stay on localhost in committed/packaged config"
            % source,
            errors,
        )

    username = props.get("spring.datasource.username", "")
    if username not in ALLOWED_DATASOURCE_USERNAMES:
        fail(
            "%s spring.datasource.username is not a local-dev default" % source,
            errors,
        )

    password = props.get("spring.datasource.password", "")
    if password not in ALLOWED_DATASOURCE_PASSWORDS:
        fail(
            "%s spring.datasource.password is not a local-dev default" % source,
            errors,
        )


def check_java_tree(java_root, errors):
    if not os.path.isdir(java_root):
        fail("java tree missing: %s" % java_root, errors)
        return
    for dirpath, _, filenames in os.walk(java_root):
        for name in filenames:
            if not name.endswith(".java"):
                continue
            path = os.path.join(dirpath, name)
            with open(path, "r", encoding="utf-8") as handle:
                text = handle.read()
            for match in JAVA_ASSIGNMENT.finditer(text):
                name = match.group(1)
                if CREDENTIAL_NAME.search(name):
                    rel = os.path.relpath(path, java_root)
                    fail(
                        "hardcoded credential assignment to %s in %s" % (name, rel),
                        errors,
                    )


def check_file(path, errors):
    with open(path, "r", encoding="utf-8") as handle:
        props = parse_properties(handle.read())
    check_properties(props, path, errors)


def check_jar(path, errors):
    with zipfile.ZipFile(path) as jar:
        names = set(jar.namelist())
        for forbidden in FORBIDDEN_PACKAGED:
            if forbidden in names:
                fail("%s must not package %s" % (path, forbidden), errors)
        leaked = [name for name in names if name.endswith("/application-local.properties")]
        for name in leaked:
            if name not in FORBIDDEN_PACKAGED:
                fail("%s must not package %s" % (path, name), errors)
        if PACKAGED_PROPERTIES not in names:
            fail("%s missing %s" % (path, PACKAGED_PROPERTIES), errors)
            return
        text = jar.read(PACKAGED_PROPERTIES).decode("utf-8")
    check_properties(
        props=parse_properties(text),
        source="%s:%s" % (path, PACKAGED_PROPERTIES),
        errors=errors,
    )


def default_module_root():
    return os.path.abspath(os.path.join(os.path.dirname(__file__), os.pardir))


def main(argv):
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "--module-root",
        default=default_module_root(),
        help="v2 Spring module root (directory that contains pom.xml)",
    )
    parser.add_argument(
        "--jar",
        action="append",
        default=[],
        help="packaged Spring Boot JAR to inspect (repeatable)",
    )
    args = parser.parse_args(argv)

    module_root = os.path.abspath(args.module_root)
    properties_path = os.path.join(
        module_root, "src", "main", "resources", "application.properties"
    )
    resources_local = os.path.join(
        module_root, "src", "main", "resources", "application-local.properties"
    )
    java_root = os.path.join(module_root, "src", "main", "java")

    errors = []
    if not os.path.isfile(properties_path):
        fail("missing %s" % properties_path, errors)
    else:
        check_file(properties_path, errors)
    if os.path.isfile(resources_local):
        fail(
            "%s must not exist; Maven packages src/main/resources into the JAR"
            % resources_local,
            errors,
        )
    check_java_tree(java_root, errors)

    for jar_path in args.jar:
        if not os.path.isfile(jar_path):
            fail("JAR not found: %s" % jar_path, errors)
        else:
            check_jar(jar_path, errors)

    if errors:
        for item in errors:
            print("FAIL: %s" % item, file=sys.stderr)
        return 1

    print("OK: committed config and scanned artifacts have no production secret values")
    return 0


if __name__ == "__main__":
    sys.exit(main(sys.argv[1:]))
