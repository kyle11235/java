MAVEN_OPTS="-Xms1M -Xmx1M -XX:+PrintCommandLineFlags -verbose:gc -XX:+HeapDumpOnOutOfMemoryError "

mvn -f jvm.xml spring-boot:run -Dspring-boot.run.arguments="static,10000" 

# it creates file java_pidxxx.hprof for HeapDumpOnOutOfMemoryError

