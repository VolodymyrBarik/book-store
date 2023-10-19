# Book store <img height="50" src="C:\Users\barma\IdeaProjects\book-store\images\logo small.png"/>

The project was established with the goal to help book lovers to choose and to buy books online. 
Inspired by Amazon of the early days. Book-store service is written in Java and this means that it has unlimited
potential for further development. By now it has only basic functionality. 
The selection of books is very large, but the interface is easy to understand and not overloaded with functionality,
so you can quickly find the book you like.

# Technologies have been used <img height="50" src="C:\Users\barma\IdeaProjects\book-store\images\spring logo.png"/> <img height="50" src="C:\Users\barma\IdeaProjects\book-store\images\java 17 logo.png"/>
* Java 17 - as the main language of the project.
* Spring Boot - as main framework to build this RESTful service 
* Spring Security - user as provider of user authentication, authorization, and role-based access control 
* Spring Data JPA - as source of high-level solutions, easy-to-use interfaces for working with databases
* Liquibase - tool that maintains database schema version control. 
* Helps maintain consistency and automate database schema modifications across different environments and application releases.
* Maven - used to manage project dependencies.
* OpenAPI\SwaggerUi - as a way to visualize book-service through a user-friendly interface. By running an application and opening 
http://localhost:6868/swagger-ui/index.html#/ you will have this functionality.

# Controllers functionality
Authentication section is the entering point into the application. 
You have two options:
1. log in to the system if you are already registered
2. register in the system if you are a new user, and you don't have login yet

After registering users have the ability to log into the system and have access to all endpoints configured for the user 
with role USER. USER is the default role, all the users receiving after the registration.

![](C:\Users\barma\IdeaProjects\book-store\images\Authentication_management_2.jpg)

After passing authentication you have functionality listed below:

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
One of the possible ways to use this book-service is to make an image of this part of the application.
After that image can be pushed to one of the cloud services for example AWS. Database connection will be configured
there. This will be the back-end part of the web app. Just like that there can be configured front-end parts.
Different parts of the application communicate between each other through the http protocol.

![](C:\Users\barma\IdeaProjects\book-store\images\architecture.jpg)

It could be as a stand-alone application consisting of front-end, back-end parts only, or it could be used as
a microservice, a small part of a more complicated ecosystem where book-store service will be responsible only for
book selling. But this will have common security and some other settings with other microservices.

![](C:\Users\barma\IdeaProjects\book-store\images\Untitled-3.jpg)

# How to start
As it is app configured to run in Docker container. To start it you have to  

[//]: # (How to run your application? - didn't find about this)

[//]: # (Add a Loom video &#40;2-4 min&#41; where you show how your project works)

[//]: # (Not all technologies included)

[//]: # (You can make your readme more engaging with the different capabilities of .md.)

[//]: # (Make more topics, group your texts, add code examples &#40;e.g. how to run your app&#41;, notations, order/unordered lists)


 