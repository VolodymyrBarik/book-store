# Spring Boot REST API containerised BookStore example project

## Book store <img height="50" src="C:\Users\barma\IdeaProjects\book-store\images\logo small.png"/>

The project was established with studying purposes. The goal is to learn Java and frameworks from the Java orbit. 
This is the back-end part of a book-store web service. Inspired by Amazon of the early days. By now this part has only 
basic not overcomplicated functionality.

# Technologies have been used <img height="50" src="C:\Users\barma\IdeaProjects\book-store\images\spring logo.png"/> <img height="50" src="C:\Users\barma\IdeaProjects\book-store\images\java 17 logo.png"/> <img height="50" src="C:\Users\barma\IdeaProjects\book-store\images\hibernate_logo_icon_169034.png"/>
* **[Java 17](https://www.oracle.com/java/)** - the main language of the project.
* **[Spring Boot](https://spring.io/projects/spring-boot)** - main framework to build RESTful service.
* **[Spring Security](https://spring.io/projects/spring-security)** - provider of user authentication, authorization, and role-based access control. 
* **[Spring Data JPA](https://spring.io/projects/spring-data-jpa)** - source of high-level solutions, easy-to-use interfaces to work with database.
* **[Hibernate](https://hibernate.org/)** - orm framework that simplifies mapping objects from code into database tables. 
* **[Mysql](https://www.mysql.com/)** - the most popular DBMS was chosen to operate database.
* **[Liquibase](https://www.liquibase.org/)** - tool that maintains database schema version control.
* **[Lombok](https://projectlombok.org/)** - used to make code look better and decrease amount boilerplate.
* **[Maven](https://maven.apache.org/)** - used to build project and to manage dependencies.
* **[OpenAPI\SwaggerUi](https://www.openapis.org/)** - as a way to visualize book-service with a user-friendly interface. 
* **[Testcontainers](https://testcontainers.com/)** - used for testing the repository level not using project database.
* **[Mockito](https://site.mockito.org/)** - used on service levels to make mock objects and test specific components.
* **[MapStruct](https://mapstruct.org/)** - used to simplify mapping between objects and avoid boilerplate.
* **[Docker](https://www.docker.com/)** - used to develop, test and ship project as container that independent of the environment configuration.

# Features
Authentication section is the entering point into the application. 
You have two options:
1. log in to the system if you are already registered
2. register in the system if you are a new user, and you don't have login yet

After registering users have the ability to log into the system and have access to all endpoints configured for the user 
with role USER. USER is the default role, all the users receiving after the registration.

![](C:\Users\barma\IdeaProjects\book-store\images\Authentication_management_2.jpg)

Project has security configured. This means that users that are not authenticated have access only to
authentication controller. But for demonstration purpose you don't need to register in the system while observing
project via OpenAPI. In normal workflow functionality listed below available only for authenticated users:

In the Shopping Cart section you have access to your previously formed shopping cart, if you haven't one there will 
be your empty shopping cart. You can add books to your shopping cart, change their quantity or delete books from 
the shopping cart.

![](C:\Users\barma\IdeaProjects\book-store\images\ShoppingCart_management_2.jpg)

In Order management you can get a list of all previously placed orders, you can see items in certain order from 
that list, you can also see detailed information about that item. You can place a new order. Users with the role admin
have the ability to change the status of the orders.

![](C:\Users\barma\IdeaProjects\book-store\images\Order_management_2.jpg)

In the Books section you have the ability to see all the books available at the moment, watch detailed information
about the book chosen or search for the book. The ability to add, update or delete books is available only for users
with the role ADMIN.

![](C:\Users\barma\IdeaProjects\book-store\images\Book_management_2.jpg)

In the Category sections you can see all the categories/genres of books available, you can peek at one and see for
detailed information or to see all the books that are present in this particular category.  The ability to add,
update or delete new categories is available only for users with the role ADMIN.

![](C:\Users\barma\IdeaProjects\book-store\images\Categories_management_2.jpg)
    
# Possible usage <img height="50" src="C:\Users\barma\IdeaProjects\book-store\images\docker logo.png"/> <img height="50" src="C:\Users\barma\IdeaProjects\book-store\images\aws-logo.png"/>
* You can use this project as a template for your own book store application or as a reference for learning how to build similar web applications.
* One of the possible ways to use this book-service is to make an image of this part of the application.
After that image can be pushed to one of the cloud services for example AWS. Database connection will be configured
there. This will be the back-end part of the web app. Just like that there can be configured front-end parts.
Different parts of the application communicate between each other through the http protocol.
* it could be used as a microservice, a small part of a more complicated ecosystem where book-store service will 
be responsible only for book selling. But this will have common security and some other settings with other microservices.

# How to start
Application is configured to run in Docker container. 
To run it there should be [**IntellijIDEA**](https://www.jetbrains.com/idea/download/?section=windows) and 
**[Docker desktop](https://www.docker.com/products/docker-desktop/)** preinstalled and running on your machine.
To run it from IntellijIDEA you simply have to type in the terminal:
* ```mvn clean package``` - with Docker turned on to build .jar file of the web app.
* ```docker compose build``` - to build Docker image from the .jar file composed with the image of mysql database.
* ```docker compose up``` - to run application in container and make it available for demo.
*  http://localhost:6868/swagger-ui/index.html#/ - go to this address to see detailed functionality of the app in user-friendly format.

# [To see video review click here](https://www.loom.com/share/1be7f9540a8e42baa75a52392d787066?sid=f0bc2c2e-483a-48fa-97a0-88fc75307475)


 