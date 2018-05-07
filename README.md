# backend-tech-assessment

Skeleton project for Backend Technical Assessment.

Includes
--------
- Maven - [pom.xml](pom.xml)
- Application properties - [application.yml](src/main/resources/application.yml)
- Runnable Spring Boot Application - [BackendTechAssessmentApplication](src/main/java/com/intuit/cg/backendtechassessment/BackendTechAssessmentApplication.java)
- REST endpoints - [RequestMappings.java](src/main/java/com/intuit/cg/backendtechassessment/controller/requestmappings/RequestMappings.java)

Requirements
------------
See Backend Technical Assessment document for detailed requirements.

How to Run
-----------
It will run on port http://localhost:8080
How to access rest api : http://localhost:8080/swagger-ui.html

Note: i did not written any test cases as it took almost than 4 hours. I have strong experience in writing unit test cases.

Auto winner
------------
Check com.intuit.cg.backendtechassessment.util.AutoWinerThread.java
A thread runs after every 60 seconds and check the buyer with the lowest bid automatically wins the bid when the deadline is reached. A user can bid more than once. 
There can be more than 1 bid and there can be multiple bids.
If two bids are same and minimum for a project then it checks best user rating among those users.
If those users do not have user rating then it choose the winner based on whoever bided first

Buyer And seller rest API
---------------------
I did not wrote much in Buyer And seller rest API. But i can move those from other rest api classes.

============
1. I used swagger to do rest documentation and execution for testing
2. I used spring interceptor com.intuit.cg.backendtechassessment.util.WebConfig.java. Here i was creating unique 
request id for each request. It will help to identify specific request log in micro service environment.
3. All rest api returns response in similar pattern (check com.intuit.cg.backendtechassessment.util.ServiceResponseVO.java)
============