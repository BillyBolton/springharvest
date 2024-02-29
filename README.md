# Spring Harvest :corn: :farmer: :tomato:

## Synopsis

This project is an opinionated framework that extends Spring Boot to empower developers to implement RESTful apis with CRUD and/or search support with ease.

If using these libraries for your own components (Controller, Service, Mapper, Repository), you can also use the testing libraries we ship to further reduce the
amount of code you need to write.

## Code Example

An example project using these libraries is also included, and can be
inspected [here](https://github.com/BillyBolton/springharvest/tree/main/examples/books-catalogue/src/main/java/dev/springharvest/library).

## Motivation

Many Spring Boot RESTful APIs contain the same design patterns across microservices, their respective domains. These libraries are meant to reduce boilerplate
code that are necessary to support CRUD and search operations. This project also has plans to

A similar approach is taken for the integration tests included in this project. Many developers write tests ad-hoc for the business logic that they require,
however, when looking at the design patterns at high level, this logic can also be abstracted. Therefore, we are also shipping an integration testing framework
to easily provide coverage for the libraries contained in this project.

This project also has plans to generate code for components using only entity models, thereby reducing the amount of code a developer needs to write even
further.

## Installation

1. Install Java 21.+ with GraalVM support, such as the open-source [graalce distribution](https://github.com/graalvm/graalvm-ce-builds/releases).

- TIP: Use [SDKMAN](https://sdkman.io/install) to manage your SDKs more easily.

2. TODO

## API Reference

Running the `books-catalogue` project locally make the OpenApi Swagger Documentation accessible on `localhost:8080`.

## Tests

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=BillyBolton_springharvest&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=BillyBolton_springharvest)

The abstracted code is difficult to test on its own, despite it garnering coverage within the `books-catalogue` example project. I am looking for developer
support for the following, however:

- Write unit tests for abstracted code
- Identify a way that test coverage can be consolidated for SonarCloud so that the coverage we obtain in `books-catalogue` can be extended to the other library
  packages.

Please also note that we use Java Reflect for the `components-search` package. Using reflection often flags code smells, however is usually only an issue if you
are changing any values by brute force using this approach. Because this is not applicable for this project, many of the code smells that are reported are false
positives.

## Contributors

Please reach out to [Billy Bolton](billybolton16@gmail.com) to inquire how to contribute.

## License

Apache License, Version 2.0

License is subject to change.

