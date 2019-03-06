

# springboot, test


## db

- test

	mvn -f db.xml spring-boot:run -Dspring-boot.run.arguments="8,1,hikari"  
	hikari/druid//ucp/c3p0/dbcp/mysql/oracle
	
- run
	
	mvn -f db.xml clean package
	java -jar target/db-0.0.1-SNAPSHOT.jar 8 1 hikari

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
	
- run

	mvn -f nio.xml clean package
	java -jar target/nio-0.0.1-SNAPSHOT.jar
	curl localhost:9999
		
## sync

- test

	mvn -f sync.xml spring-boot:run -Dspring-boot.run.arguments="1,4,4"
	1/2/3/4/5  

	