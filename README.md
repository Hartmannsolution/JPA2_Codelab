# JPA2 Codelab

## Requirements

- Java 17
- Maven
- Docker running for tests
- PostgreSQL running locally for `Populator.main`

## Local Database

The local development database settings are in `src/main/resources/config.properties`:

```properties
db.name=university_w1
db.username=dev
db.password=ax2
db.host=localhost
db.port=5432
```

## Commands

Run the tests with Testcontainers:

```bash
mvn clean test
```

Build without running tests:

```bash
mvn -DskipTests package
```
