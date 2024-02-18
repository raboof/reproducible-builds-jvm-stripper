reproducible-builds-jvm-stripper
================================

[![Build Status](https://github.com/raboof/reproducible-builds-jvm-stripper/actions/workflows/maven.yml/badge.svg)](https://github.com/raboof/reproducible-builds-jvm-stripper/actions/workflows/maven.yml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.bzzt/reproducible-builds-jvm-stripper/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.bzzt/reproducible-builds-jvm-stripper)

Library to make your JVM artifacts byte-for-byte reproducible.

Heavily based on http://zlika.github.io/reproducible-build-maven-plugin/ , but
published as a standalone library rather than a Maven plugin.

See also [moot](https://github.com/Zlika/moot), a script that downloads given versions of Maven and the JDK and runs the build with them. This script helps fixing non-reproducibilities that cannot be fixed by the reproducible-build-maven-plugin.

### Requirements

* Java 8 or newer

### How to compile

To compile the project and run its integration tests:

```
mvn clean install #-Prun-its
```

