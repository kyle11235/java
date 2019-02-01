

# springboot, test

- test

	mvn spring-boot:run -Dspring-boot.run.arguments="8,1,mysql"   mysql/druid/hikari/ucp/oracle
	
- run
	
	mvn clean package
	java -jar target/test-0.0.1-SNAPSHOT.jar 8 1 mysql

	
## todo
	
- close statement
- pool properties


