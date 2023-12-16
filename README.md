# e-Puskesmas REST API

This repository contains e-Puskemas REST API a DigiTalent Smart-Health Project

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Prequisites package:
* Java Development Kit (JDK) 1.8
* Maven 3
* Spring 2.4.4

### Installing

Below is the instructions to make this code running:

* Clone this repository to your local
```shell script
$ git clone https://github.com/dimaskiddo/springboot-epuskesmas-rest.git
$ cd e-puskesmas-api
```
* Installing dependencies using Maven
```shell script
$ mvn dependency:resolve
$ mvn clean
```
* Use your IDE using Intellij or Spring Tools to edit the project and open the `pom.xml` file
* To run the code locally
```shell script
$ mvn spring-boot:run
```
* To build as `*.jar` file
```shell script
$ mvn package
```
* To build with Docker
```shell script
$ docker build -t e-puskesmas/api:latest .
```

## Authors

* **Dimas Restu Hidayanto** - *Initial Work* - [@dimaskiddo](https://github.com/dimaskiddo)
* **Tommy Kurniawan** - *Maintainer* - [@tommykurniawans](https://github.com/tommykurniawans)
