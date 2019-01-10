

# single jar app with @WebServlet and manually embedded tomcat


1.create maven web project

	mvn archetype:generate 
	-DarchetypeArtifactId=maven-archetype-webapp 
	-DarchetypeGroupId=org.apache.maven.archetypes 
	-DinteractiveMode=false 
	-DgroupId=com.example.employees 
	-DartifactId=employee-tomcat -DarchetypeVersion=1.0


2.add embedded tomcat dependency

3.add build plugin

	maven-compiler-plugin
	maven-assembly-plugin, used to create a single jar package

4.add resources element to inlcude jsp js css ...

5.run

	mvn clean package
	cd target
	java -jar employee-tomcat-1.0-SNAPSHOT-jar-with-dependencies.jar.

6.localhost:8080








