# jersey, solr, oracle database, hcm search app

1.prepare database

  - create a database instance
  - myhub/java/hcm/sql/sql_table.sql/sql_data.sql

2.start solr
  
  - myhub/java/hcm/solr651.zip
  - update database information in solr/example/example-DIH/solr/db/conf/db-data-config.xml  
  - start solr by `solr651/bin/solr.cmd -e dih` on windows or `solr651/bin/solr -e dih` on linux
  - update index by admin page (no need to do this if there's no data change in database)

    http://localhost:8983/solr/#/db/dataimport

3.develop

  - download by `git clone https://github.com/kyle11235/hcm`
  - update database information in hcm/src/main/resources/config.properties
  - `mvn jetty:run -Djetty.port=8080`
  - visit http://localhost:8080/hcm/

4.deploy
 
  - package by `mvn clean package`
  - put the target/hcm.war into tomcat 
  - visit http://your_ip:8080/hcm/
  	
	
	

	
