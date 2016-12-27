![alt text](http://cdn0.agoda.net/images/MVC/default/logo-agoda-mobile@2X.png "agoda")

Hotel API Programming
===================
In this exam, you are provided with hotels database in CSV (Comma Separated Values) format. 

We need you implement HTTP service, according to the API requirements described below.
You may use any language or platform that you like: C#/Java/Scala/etc.

Good luck!

Application is built using Spring boot and Java 8.


API
======
  1. Search hotels by CityId
    API Rest URL: http://<hostname>:<portname>/city/{cityId}
    Ex: http://localhost:8080/city/Bangkok
  2. Provide optional sorting of the result by Price (both ASC and DESC order).
    Will pass as request parameter "order". Permissible values are "ASC" and "DESC".
     For example http://localhost:8080/city/Bangkok?order=ASC
     
     By default there would not be any ordering

API Rate Limit:
Every API is bounded by rate limit defined in application.properties file.
This properties file will contain rate per duration (in seconds) for each API.
Every API is uniquely identified by a key called API Key. Valid API keys are defined in com.agoda.hotel.model.GET_HOTEL_BY_CITY.

Each request to API must be provided with following Header param (apart from application/json as Content-Type):
API_KEY: <API KEY NAME>

Also every API must have their unqiue key defined in application.properties file

For example, for search hotel by city ID. Caller must provide following as Header param:
**API_KEY: GET_HOTEL_BY_CITY**

So if user exceeds rate limit, then operation would be halted for 5 minutes. Service would come back again after 5 minutes.

**_I have used Token bucket algorithm to implement Rate limit feature._**



Build and Run instructions
===========================
Make sure system has maven 3 and JDK 8 deployed
To build this code, run following command inside the project (where pom.xml is located)
 mvn clean install
 
Once build is successful, run following command to up app server
java -jar target/hotel-rest-service-0.1.0.jar

App server would start running at localhost:8080

API Testing
===========================
Use any REST Client and make an HTTP request at following details:
URL: http://localhost:8080/city/Bangkok (Bangkok is example city. It could be any valid city ID)
Method: GET
Header: Content-Type: application/json
        API_KEY: GET_HOTEL_BY_CITY
Response:
200 OK: Result in JSON format for all hotel details in given city ID. Result could be put in ASC or DESC by passing "order" as request parameter.
403: Forbidden, If you caller exceed API call rate limit
500: Internal Server Error


All material herein © 2005 – 2014 Agoda Company Pte. Ltd., All Rights Reserved.<br />
AGODA ® is a registered trademark of AGIP LLC, used under license by Agoda Company Pte. Ltd.<br />
Agoda is part ofPriceline (NASDAQ:PCLN)<br />
