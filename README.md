# JWT-Authentication-SpringBoot [Rest API]

This App is the basic setup JWT Auth for Rest API. You can easly run using springboot IDE or any supported IDE.

# TOOLS

## IDE
You can download from [here]][https://spring.io/tools]

## Postman

Download postman for testing the application. Click [here][https://www.postman.com/downloads/]

---
# How to Run?
1. git clone https://github.com/SeeTheC/JWT-Authentication-SpringBoot.git 
2. Open "SpringToolSuite4"
3. One project "File->Open Project From File System" & select <your path>/JWT-Authentication-SpringBoot/SimpleJwtAuth
4. Run Project: Right Click on Project "SimpleJwtAuth" > Run As > Sprint Boot App

# How to Test?
1. Open Postman
2. Create WorkSpace
3. Create Collection
4. Create Request-1:
  Post Request. URl http://localhost:8080/auth
  Body JSON:
  {
    "username": "foo",
    "password": "foo"
  }
5. Send Request-1 by clicking on send button
  You will get response something like this
  {
    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmb28iLCJleHAiOjE2MjIzNTYwNzEsImlhdCI6MTYyMjMyMDA3MX0.RzOb5TUHym7jDCB1-uRMCtDggq8g7V2DRwvygwDim64"
 }
6. Create Request-2
  Get Request. Url http://localhost:8080/hello
  Header: 
  Key as "Authorization"
  Value as "Bearer <your jwt token result of step 5>"
7. Send Request-1 by clicking on send button
   You will get response as "Hello Word" for 200 Response code
  
