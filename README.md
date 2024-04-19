# Spring Harvest :corn: :farmer: :tomato:

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=BillyBolton_springharvest&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=BillyBolton_springharvest)

## Synopsis

This project is an opinionated framework that extends Spring Boot to empower developers to implement APIs with CRUD and/or search support with ease.

If using these libraries for your own components (Controller, Service, Mapper, Repository), you can also use the testing libraries we ship to further reduce the
amount of code you need to write.

## Motivation

Many Spring Boot RESTful APIs contain the same design patterns across microservices, their respective domains. These libraries are meant to reduce boilerplate
code that are necessary to support CRUD and search operations. This project also has plans to

A similar approach is taken for the integration tests included in this project. Many developers write tests ad-hoc for the business logic that they require,
however, when looking at the design patterns at high level, this logic can also be abstracted. Therefore, we are also shipping an integration testing framework
to easily provide coverage for the libraries contained in this project.

This project also has plans to generate code for components using only entity models, thereby reducing the amount of code a developer needs to write even
further.

# More Information

Please see the repository [Wiki]() for more information about Getting Started, Contributing, and more.