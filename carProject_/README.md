# CarProject_

CarProject_ is a web application that enables users to manage a collection of cars through Create, Read, Update, and Delete (CRUD) operations. The application is designed to be user-friendly and does not require user authentication, making it accessible to all users.

## Overview

The application is developed using the ZK Framework for the frontend, making for a responsive user interface. The backend is powered by Java with JPA for object-relational mapping, interacting with an H2 Database to perform CRUD operations. The architecture is simple yet effective, leveraging Spring Boot for ease of application setup and deployment.

## Features

- View a list of cars with details including model, make, preview (image), and price
- Add new cars to the list
- Edit existing car details
- Delete cars from the list

## Getting started

### Requirements

- JDK 11
- Maven
- An IDE of your choice (Eclipse, IntelliJ IDEA, etc.)

### Quickstart

1. Clone the repository to your local machine.
2. Navigate to the project directory and run `mvn clean install` to build the project.
3. Run `mvn spring-boot:run` to start the application.
4. Access the web application at `http://localhost:8080`.

### License

Copyright (c) 2024. All rights reserved.