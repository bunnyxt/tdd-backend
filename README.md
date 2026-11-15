# tdd-backend

## Status

The production backend is the legacy Spring service in
[`v2_spring_backend/tdd`](v2_spring_backend/tdd). It receives only minimal,
security-focused maintenance while its replacement is designed.

The former v3 GraphQL experiment was never deployed and has been removed. Any
replacement backend must be designed and implemented independently.

The former PHP backend has been retired. Existing compatibility endpoints are
implemented by the Spring service; PHP is no longer required to run this backend.

For the limited dependency check used before a v2 dependency-maintenance
release, run `v2_spring_backend/tdd/verify-dependencies.sh` from a clean
checkout with Maven and authenticated `gh` available. It builds the JAR,
records the resolved dependency tree under `target/`, and fails if Dependabot
has an open alert for the v2 Maven manifest. It does not audit transitive
advisories.


## PostgreSQL development

Build with JDK 11 and Maven; the generated classes target Java 8:

```sh
cd v2_spring_backend/tdd
mvn clean package
```

The database must already contain the PostgreSQL schema, including the three
`*_record_latest` snapshot tables and `tdd_member.last_video_aid`. Set
`TDD_DATABASE_URL` to a PostgreSQL JDBC URL, `TDD_DATABASE_USERNAME`, and
`TDD_DATABASE_PASSWORD`, or provide the equivalent `spring.datasource.*`
properties in an external file. Database configuration is required. The pool
uses at most four read-only connections by default.

```sh
java -Xmx256m -jar target/tdd-backend.jar \
  --spring.config.additional-location=file:/path/to/application.properties \
  --server.address=127.0.0.1 --server.port=1437
```

Keep credentials outside the repository and JAR. The resource name
`application-local.properties` is excluded from packaging. For offline replay of
the two compatibility tools, set `tdd.bilibili.view-url` to a local HTTP fixture
endpoint; its normal default is the public video metadata endpoint.
Set `tdd.cors.allowed-origins` to a comma-separated list when using a different
frontend origin; existing origins remain the default.

API timestamps remain integer Unix seconds and identifiers and counts remain
JSON numbers. Java uses 64-bit values for identifiers, timestamps, totals, and
pagination offsets, and decimal values for donation amounts. JavaScript
consumers still need to account for numbers above their safe integer range.
Ordinary history keeps its existing page limits and sorts by `added` descending;
rows observed in the same second have no promised ordering, and offset pages
across requests do not provide a stable export snapshot. Compatibility sprint
history retains its complete file-backed streaming response.
