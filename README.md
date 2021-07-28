# springboot-interview-test
Commerce Services - Technical Interview

This solution is based on a Clean Architecture approach with a strong separtion of responsabilities between layers. In order to keep the domain and application layers decoupled and try to respect the SOLID principles, different patterns have been applied, such as dependency inversion and injection or Data Access Objects (preventing tight ORM coupling). 

Use Cases recieve Request classes as a parameter and return Response classes as a reponse. This allows an easier encapsulation of the responsability to communicate between the Client and the Use Case to the Controller class. Mocking UseCase entry parameters also becomes easier while testing.

Because updating stock should not be a blocker for replying to the customer, a new class Logger has been added with the objective of capturing the inconsistent state in which the CreateOrderUseCase can leave the system in case a stock update fails when creating a new order. This allows us to at least have the kwnoledge that the stock has become invalid.

Spring JPA and Hibernate have been used as a basis for the persistence layer. 

A Swagger Documentation is automatically generated when building and running the Spring Boot app and can be consulted at: http://localhost:8080/swagger-ui/

The database (H2) console is also accesible via: http://localhost:8080/h2-console

The scope of the testing is limited to use cases and repositories. All tests are unit tests.

Some functionalities that have not been included in this solution but could improve its overall quality:

* ConsoleLoggerService should be replaced by a more complete logger instead of just a System printer.
* A queue system could be implemented to guarantee that, when a stock update fails, the stock update will be retried every few minutes.
* Of course, a higher testing coverage should be accomplished, including functional and integration tests.
