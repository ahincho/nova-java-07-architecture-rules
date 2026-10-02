# Nova Architecture Rules

JUnit 5 abstract test classes that enforce the architectural styles
supported by the Nova Platform meta-framework. Built on top of
[ArchUnit](https://www.archunit.org/), the library turns architectural
drift into a failed CI run.

## What's inside

| Style | Abstract test class | Package layout |
|---|---|---|
| **Layered** | `LayeredArchitectureTest` | `controller..`, `service..`, `repository..`, `entity..`, `dto..` |
| **Clean** *(phase 2)* | `CleanArchitectureTest` | `domain..`, `application..`, `infrastructure..`, `api..` |
| **Hexagonal** *(phase 2)* | `HexagonalArchitectureTest` | `domain..`, `application..`, `adapters..` |

Only the layered rules exist so far. `NovaArchitectureStyle` already names the
other two.

## Install

Published to GitHub Packages, so the repository needs to be declared and
authenticated with a token that has `read:packages`.

<!-- x-release-please-start-version -->
```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/ahincho/nova-java-07-architecture-rules")
        credentials {
            username = providers.gradleProperty("gpr.user").orNull ?: System.getenv("GITHUB_ACTOR")
            password = providers.gradleProperty("gpr.key").orNull ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    testImplementation("pe.edu.nova.java.libs:nova-architecture-rules:1.2.0")
}
```
<!-- x-release-please-end -->

A Spring Boot service that applies `pe.edu.nova.java.spring-boot-service` from the
[Nova Gradle toolchain](https://github.com/ahincho/nova-java-24-gradle-toolchain)
declares none of this: the plugin puts the library on the test classpath at the
version it pins.

## Use

Subclass the abstract test that matches your application's style and
declare the base package to scan:

```java
package com.acme.my;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import pe.edu.nova.java.archunit.LayeredArchitectureTest;

@AnalyzeClasses(
    packages = "com.acme.my",
    importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest extends LayeredArchitectureTest {

    @Override
    protected String basePackage() {
        return "com.acme.my";
    }
}
```

Run `./gradlew test`. ArchUnit reports every violation as a JUnit failure
with the exact offending class.

## Layered rules enforced

| Layer | What the rules say |
|---|---|
| `controller..` | never depends on `repository..`, and only reaches an allow-list: the layers and `shared..` of the service, `java..`, `jakarta..`, Spring, Quarkus, the test libraries (JUnit, Mockito, AssertJ) and the framework-free Nova libraries, `pe.edu.nova.java.libs..` |
| `service..` | never depends on `controller..`; no public method declares `throws Exception`; every instance field is `final`, which rules out field injection |
| `repository..` | never depends on `controller..` or `service..` |
| `entity..` | never depends on `controller..`, `service..`, `repository..` or `dto..` |
| `dto..` | never depends on `entity..` |
| any class | does not throw a generic `Throwable`, `Exception` or `RuntimeException` |

**The controller is the only layer with an allow-list.** The rules of the other
layers forbid specific layers and nothing else, so whatever they do not forbid is
allowed. That is why an entity, a service or a repository can throw the layered
errors of
[ADR-031](https://github.com/ahincho/nova-shared-01-docs/blob/main/adrs/shared/ADR-031-modulo-de-errores-por-capas-con-trazabilidad.md),
such as `DomainError`, and why a controller needed the Nova libraries added to its
list to throw `ApplicationError.invalidInput(...)`.

The allowance is `pe.edu.nova.java.libs..`, the framework-free libraries, and not
the whole `pe.edu.nova..`. A service lives under `pe.edu.nova` too, and allowing
the prefix would exempt the service's own packages from the rule. The Nova
starters, `pe.edu.nova.java.starters..`, wire the framework, so a controller does
not reach them either.

## A layer with no classes

A rule that finds no class in its layer fails with `failed to check any classes`
instead of passing. That is on purpose. The same check catches a `basePackage()`
that stopped matching the code after a rename, and a layer named `controllers` or
`model`, which would otherwise pass without checking a single class. A service
without persistence keeps its `repository` and `entity` packages, each with at
least one class. Two of the service rules fail the same way when the service
layer has no instance field or no public method to check.

A service that really has no such layer can allow it for its whole test run, with
the switch of ArchUnit itself in `src/test/resources/archunit.properties`:

```properties
archRule.failOnEmptyShould=false
```

It turns off the protection above for every rule, so it is a decision the service
writes down, not a default.

## Develop

```bash
./gradlew build
```

`LayeredArchitectureRulesTest` runs the rules through the ArchUnit engine over the
sample services in `src/test/java/pe/edu/nova/java/archunit/fixtures`, and checks
which rule fails and why. Gradle does not run those samples as tests, because some
of them break the rules on purpose.

## License

Eclipse Public License 2.0 — see [LICENSE](LICENSE).

Copyright © 2026 Angel Hincho.
