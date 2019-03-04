ulimit -c unlimited

export MAVEN_OPTS="-Xms20M -Xmx20M -XX:+PrintCommandLineFlags -verbose:gc"

mvn -f jvm.xml spring-boot:run -Dspring-boot.run.arguments="static,1" 

# kill -6 $PID
# or ctrl + \

