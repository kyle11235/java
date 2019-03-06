mvn -f jvm.xml clean package

java -Xms1M -Xmx1M -XX:+PrintCommandLineFlags -verbose:gc -jar target/jvm-0.0.1-SNAPSHOT.jar static 100000
