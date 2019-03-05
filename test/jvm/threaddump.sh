ulimit -c unlimited

export MAVEN_OPTS="-XX:+PrintCommandLineFlags -verbose:gc"

mvn -f jvm.xml spring-boot:run -Dspring-boot.run.arguments="static,1" 

# dump thread/heap info
# ctrl + \

# dump core file
# kill -6 $PID 

