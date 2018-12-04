reproducible-builds-jvm-stripper
================================

[![Build Status](https://travis-ci.org/raboof/reproducible-build-jar-stripper.svg?branch=master)](https://travis-ci.org/raboof/reproducible-build-jar-stripper)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.bzzt/reproducible-build-jar-stripper/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.bzzt/reproducible-build-jar-stripper)

Library to make your JVM artifacts byte-for-byte reproducible.

Heavily based on http://zlika.github.io/reproducible-build-maven-plugin/ , but
published as a standalone library rather than a Maven plugin.

See also [moot](https://github.com/Zlika/moot), a script that downloads given versions of Maven and the JDK and runs the build with them. This script helps fixing non-reproducibilities that cannot be fixed by the reproducible-build-maven-plugin.

### Requirements

* Java 8 or newer

### How to compile

To compile the project and run its integration tests:

```
mvn clean install -Prun-its
```
