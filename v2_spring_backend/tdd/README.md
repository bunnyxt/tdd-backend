# tdd v2 Spring backend

Maven module that builds `target/tdd-backend.jar`.

## Local configuration

Committed `src/main/resources/application.properties` holds non-secret defaults
(localhost datasource, port, MyBatis, HikariCP) and empty values for mail,
reCAPTCHA, and auth-salt keys. Those empty keys are packaged into the JAR on
purpose so a release artifact does not carry credentials.

For local overrides:

1. Copy `application-local.properties.example` to `application-local.properties`
   in this directory (next to `pom.xml`). The filename is gitignored.
2. Never put that file under `src/main/resources/`. Maven packages that
   directory into the JAR; a gitignored copy there is how previous artifacts
   shipped real credentials.
3. Fill in only what you need. The active profile is `local`, so Spring Boot
   loads `application-local.properties` from the working directory or
   `./config/`.
4. Run with JDK 11:

```bash
mvn clean package
java -jar target/tdd-backend.jar
```

Do not put real credentials in any committed file. The CI secret-scan job
rejects non-empty secret keys in `application.properties` and in the packaged
JAR.

Production loads an additional config directory outside the repository via
systemd; that file is not part of this module.
