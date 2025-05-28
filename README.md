# ResearchBucks Backend
Swagger URL - UserService-Api: http://localhost:8080/swagger-ui/index.html  
Swagger URL - ResearcherService-Api: http://localhost:8081/swagger-ui/index.html  
Swagger URL - AdminService-Api: http://localhost:8091/swagger-ui/index.html  
Service-Registry URL: http://localhost:8761  
  
# API Gateway
We can use API Gateway to route to the backend services easily.  
  
User-Service: http://localhost:8060/respondent/**  
Researcher-Service: http://localhost:8060/researcher/**  
Admin-Service: http://localhost:8060/admin/**  

# Services order
Please run services according to the below order.  
&nbsp;&nbsp;&nbsp;&nbsp;    1. Service-Registry  
&nbsp;&nbsp;&nbsp;&nbsp;    2. Api-Gateway  
&nbsp;&nbsp;&nbsp;&nbsp;    3. Admin-Service (To create DB and seed data)  
&nbsp;&nbsp;&nbsp;&nbsp;    4. User-Service and Researcher-Service
