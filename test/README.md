

# springboot, test

## connection pool

- test

	mvn spring-boot:run -Dspring-boot.run.arguments="8,1,hikari"  
	hikari/druid//ucp/c3p0/dbcp/mysql/oracle
	
- run
	
	mvn clean package
	java -jar target/test-0.0.1-SNAPSHOT.jar 8 1 hikari

- druid

	initSize/minSize/maxSize
	getConnection -> init -> takeLast -> emptySignal & notEmpty.await() -> timeout & retry -> return connections[poolingCount]

- config

	- 1M tps	
	- worker_processes = 1 x worker_connections = 512 for nginx by default
	- max connections for pool
	- max_connections of mysql is 151 by default for better performance with apache web server

## jvm

- test

	mvn -f jvm.xml spring-boot:run -Dspring-boot.run.arguments="static,1"  
	static/statement
	
- run

	./jvm/xxx.sh

## nio

- test

	mvn -f nio.xml spring-boot:run
	curl localhost:9999	
	
## thread pool

	