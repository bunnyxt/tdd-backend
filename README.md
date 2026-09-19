# tdd-backend

## Status

The production backend is the legacy Spring service in
[`v2_spring_backend/tdd`](v2_spring_backend/tdd). It receives only minimal,
security-focused maintenance while its replacement is designed.

The former v3 GraphQL experiment was never deployed and has been removed. Any
replacement backend must be designed and implemented independently.

For the limited dependency check used before a v2 dependency-maintenance
release, run `v2_spring_backend/tdd/verify-dependencies.sh` from a clean
checkout with Maven and authenticated `gh` available. It builds the JAR,
records the resolved dependency tree under `target/`, and fails if Dependabot
has an open alert for the v2 Maven manifest. It does not audit transitive
advisories.
