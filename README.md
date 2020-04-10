# Spring_JDBC_Ride-App

This repository contains an app that uses MYSQL database and simple JDBC Connector without Hibernate. 

1. Instructions to start the application:
	a. docker-compose up;
	b. start the app with an IDE

2. The RideController class contains the CRUD endpoints:
- GET ALL: "http://localhost:8080/rides";
- GET ONE BY ID: "http://localhost:8080/ride/{id}"; 
- GET FOR BATCH: "http://localhost:8080/ride/batch" (for update with batchTemplate);
- GET FOR TEST THE EXCEPTION HANDLING: "http://localhost:8080/exception";
- POST:  "http://localhost:8080/ride";
- PUT: "http://localhost:8080/ride";
- DELETE: "http://localhost:8080/ride/{id}";

3. The RideRepositoryImpl class contains the main logic:
- createRide(Ride ride) {...}: 
	#1: inserting data with JdbcTemplate.update() method;
	#2: inserting data with SimpleJdbcInsert class to retieve the key;
	#3: inserting data with JdbcTemplate.update() method and KeyHolder;
	#4: solution based on #2 and combined with getRide() method;

- getRide(Integer id) {...}:
	#5: get one row by using JdbcTemplate.queryForObject() method;

- getRides() {...}:
	#6: get all records by using JdbcTemplate.query() method;
	#7: get all records by using JdbcTemplate.query() method with anonymus innerclass implementation;

- updateRide(Ride ride) {...}:
	#8: update record with JdbcTemplate.update() method;

- updateRides(List<Object[]> pairs) {...}:
	#9: update records with JdbcTemplate.batchUpdate() method;

- deleteRides(Integer id) {...}:
	#10: delete record with JdbcTemplate.update() method;
	#11: delete record with NamedParameterJdbcTemplate() class;

4. The RideControllerTest class contains the tests of endpoints;

5. References:
	This project is based on Bryan Hansen: Building Application Using Spring JDBC course.











