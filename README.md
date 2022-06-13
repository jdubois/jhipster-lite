[![Logo JHipster Lite][jhipster-image]][jhipster-url]

# JHipster Lite ⚡

[![JHipster Lite version][jhipster-lite-release-version]][jhipster-lite-release-url]
[![Build Status][github-actions-jhlite-image]][github-actions-url]
[![Coverage Status][codecov-image]][codecov-url]

[![sonarcloud-quality-gate][sonarcloud-quality-gate]][sonarcloud-url]
[![sonarcloud-maintainability][sonarcloud-maintainability]][sonarcloud-url]

[![sonarcloud-bugs][sonarcloud-bugs]][sonarcloud-url]
[![sonarcloud-vulnerabilities][sonarcloud-vulnerabilities]][sonarcloud-url]
[![sonarcloud-security][sonarcloud-security]][sonarcloud-url]
[![sonarcloud-code-smells][sonarcloud-code-smells]][sonarcloud-url]
[![sonarcloud-coverage][sonarcloud-coverage]][sonarcloud-url]

## Description

JHipster is a development platform to quickly generate, develop & deploy modern web applications & microservice architectures.

**JHipster Lite** will help you to start your project, by generating step by step only what you need.

- the generated code uses Hexagonal Architecture
- the technical code is separated from your business code
- you will only generate the code you want, no additional unused code
- best quality as possible: 💯% coverage, 0 code smell, no duplication 😎

## Deploy to Heroku

Click on this button to deploy your own instance of JHipster Lite:

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

## Prerequisites

### Java

You need to have Java 17 :

- [JDK 17](https://openjdk.java.net/projects/jdk/17/)

### Node.js and NPM

- [Node.js](https://nodejs.org/): we use Node to run a development web server and build the project. Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.

```
npm ci
```

You will only need to run this command when dependencies change in [package.json](package.json).

```
npm install
```

## Test the project

To launch tests:

```
./mvnw clean test
```

To launch tests and integration tests:

```
./mvnw clean verify
```

## Prettier

We use [prettier](https://github.com/prettier/prettier) and [prettier-java](https://github.com/jhipster/prettier-java) to format our code.

To check format:

```
npm run prettier:check
```

To launch format all code:

```
npm run prettier:format
```

## Sonar Analysis

To launch local Sonar Analysis:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Then:

```
./mvnw clean verify sonar:sonar
```

So you can check the result at http://localhost:9001

## Run the project

You can run the project using Maven, as `spring-boot:run` is the default target:

```
./mvnw
```

Or, first, you can package as jar:

```
./mvnw package
```

Then, run:

```
java -jar target/*.jar
```

So you can navigate to http://localhost:7471 in your browser.

## e2e tests

You need to run the project first. Then, you can run the end-to-end tests:

```
npm run e2e
```

Or in headless mode:

```
npm run e2e:headless
```

## Generate your project

Go to http://localhost:7471, select your option and generate the code you want, step by step, and only what you need.

If needed more advanced features, go to http://localhost:7471/swagger-ui.html and use your own JSON to generate the code you want. Here an example:

<!-- prettier-ignore-start -->
```yaml
{
  "folder": "/tmp/beer",
  "generator-jhipster": {
    "baseName": "beer",
    "projectName": "Beer Project",
    "packageName": "tech.jhipster.beer",
    "serverPort": 8080
  }
}
```
<!-- prettier-ignore-end -->

You can use the different existing APIs to:

- init the project

Then, for a Java project, you can add:

- Maven as Build Tool
- Gradle as Build Tool
- Java Base classes and Domain Error

List of optional tools for Java:

- ArchUnit tests
- JaCoCo configuration for minimum coverage
- Sonar configuration

Setup:

- GitHub Codespaces
- Gitpod

Spring Boot:

- Spring Boot as Server Framework
- Spring Boot Actuator
- Tomcat or Undertow as Spring Boot MVC
  - Spring Security with JWT
    - Basic Auth
  - OAuth 2.0 and OIDC Authentication
- AOP Logging
- Asynchronous execution and scheduling configuration
- Spring Cache
  - Simple
  - Ehcache
- Developer Tools
- Logstash TCP appender
- Springdoc without or with Security JWT
- Jib to build Docker image

Spring Boot Database:

- PostgreSQL, MySQL or MariaDB as Database
  - Liquibase or Flyway as Database Migration Tool
  - User and Authority tables (depending on Liquibase or Flyway)
- MongoDB as NoSQL Database

Spring Cloud:

- Eureka client
- Spring Cloud Config client
- Spring Cloud Consul

Broker:

- Apache Kafka

Client:

- Angular
- React with Vite
- Vue with Vite

Continuous Integration:

- GitHub Actions

[jhipster-lite-release-version]: https://img.shields.io/github/v/release/jhipster/jhipster-lite
[jhipster-lite-release-url]: https://github.com/jhipster/jhipster-lite/releases
[github-actions-jhlite-image]: https://github.com/jhipster/jhipster-lite/workflows/build/badge.svg
[github-actions-url]: https://github.com/jhipster/jhipster-lite/actions
[codecov-image]: https://codecov.io/gh/jhipster/jhipster-lite/branch/main/graph/badge.svg
[codecov-url]: https://codecov.io/gh/jhipster/jhipster-lite
[jhipster-image]: https://raw.githubusercontent.com/jhipster/jhipster-artwork/main/logos/lite/JHipster-Lite-neon-blue.png
[jhipster-url]: https://www.jhipster.tech/
[sonarcloud-url]: https://sonarcloud.io/project/overview?id=jhipster_jhipster-lite
[sonarcloud-quality-gate]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=alert_status
[sonarcloud-maintainability]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=sqale_rating
[sonarcloud-bugs]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=bugs
[sonarcloud-vulnerabilities]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=vulnerabilities
[sonarcloud-security]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=security_rating
[sonarcloud-code-smells]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=code_smells
[sonarcloud-coverage]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=coverage

<!-- jhipster-needle-readme -->
