

# jndi

## wesphere docker install
https://hub.docker.com/r/ibmcom/websphere-traditional
docker run --name test -h test   -p 9043:9043 -p 9443:9443 -d ibmcom/websphere-traditional:latest
docker exec -it test /bin/bash
cat /tmp/PASSWORD
user ID=wsadmin
password=Jz/27/tI
https://x.x.x.x:9043/ibm/console/login.do?action=secure


## deploy
jar -cvf test.war *
context-root=test
https://x.x.x.x:9443/test/

