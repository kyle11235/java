
# springboot app, mock database or oracle database

1. install jdbc

    cd lib && ./install.sh

2. mock dao/database dao

    EmployeeController.java

3. config

    place config into ~/.config or export ENV_CONFIG_HOME=/foo/bar/config

4. use jar

    to test:
    mvn spring-boot:run / java -jar target/employee.jar

    to package:
    mvn clean package

5. use war

    uncomment AppWar.java, comment out App.java

    to test:
    mvn -f war.xml spring-boot:run / mvn -f war.xml clean test exec:java

    to package:
    mvn -f war.xml clean package

6. deploy to tomcat/weblogic 12.2.1.2/JCS/ACCS/OCCS
