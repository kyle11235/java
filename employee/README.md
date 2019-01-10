
# springboot app, mock database or oracle database

1.install oracle jdbc and init database

2.use mock dao or database dao in EmployeeController.java
	
4.use war

	to test: 
	mvn spring-boot:run / cd target && java -jar employee.war
	
	to package:
	mvn clean package

3.use jar

	uncomment App.java, comment out AppWar.java
	
	to test: 
	mvn -f jar.xml spring-boot:run / mvn -f jar.xml clean test exec:java
	
	to package:
	mvn -f jar.xml clean package
	
5.deploy to tomcat/weblogic 12.2.1.2/JCS/ACCS/OCCS

