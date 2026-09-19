#!/bin/sh
# Build the v2 JAR, record its resolved dependency tree, and check the current
# Dependabot result for this Maven manifest.  It deliberately does not claim
# to audit transitive advisories; see the private dependency inventory.
set -eu

repo=${TDD_BACKEND_REPOSITORY:-bunnyxt/tdd-backend}
project_dir=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)

if ! command -v gh >/dev/null 2>&1; then
  echo "error: gh CLI is required to check Dependabot alerts" >&2
  exit 2
fi

cd "$project_dir"
mvn --batch-mode clean package -DskipTests
mvn --batch-mode dependency:tree -DoutputFile=target/dependency-tree.txt
test -f target/tdd-backend.jar

alerts=$(gh api --paginate "repos/${repo}/dependabot/alerts?state=open&per_page=100" \
  --jq '[.[] | select(.dependency.manifest_path | test("^v2_spring_backend/tdd/pom\\.xml$"))] | length')

if [ "$alerts" -ne 0 ]; then
  echo "fail: ${alerts} open Dependabot alert(s) for v2_spring_backend/tdd/pom.xml" >&2
  exit 1
fi

echo "pass: built target/tdd-backend.jar; wrote target/dependency-tree.txt; no open Dependabot alerts for v2 pom"
