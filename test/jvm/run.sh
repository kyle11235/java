export MAVEN_OPTS="-Xms200M -Xmx200M -XX:+PrintCommandLineFlags -verbose:gc -XX:+HeapDumpOnOutOfMemoryError"

mvn -f jvm.xml spring-boot:run -Dspring-boot.run.arguments="static,1" 


