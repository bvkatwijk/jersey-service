# Jersey Service

[![Build Status](https://travis-ci.org/bvkatwijk/jersey-service.svg?branch=master)](https://travis-ci.org/bvkatwijk/jersey-service)
[![codecov](https://codecov.io/gh/bvkatwijk/jersey-service/branch/master/graph/badge.svg)](https://codecov.io/gh/bvkatwijk/jersey-service)

Want to quicky deploy your jersey API to `http://localhost:8080/api`?

```java
MicroService.builder(this.getClass())
    .servletPackage("your.servlets.package")
    .build()
    .start();
```

# Options

AMicroService fields have defaults which can be altered using the builder pattern:

```java
MicroService.builder(this.class) //Reference your base class
    .applicationName("Application") //Application identifier
    .configuration(new NoConfiguration()) //Additional Configuration Steps
    .homePageFileName("index.html") //Name of home page file
    .homePageFolder("web") //Name of home page folder
    .port(8080) //Application Target Port
    .servletsUrlPath("/api/*") //Desired API subpath
    .servletPackage("your.servlets.package") //Jersey API source package
```
