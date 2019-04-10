

### based on the old testmybatis

	- use DaoImpl to extend DaoSupport (the old testmybatis calls dao interface directly from service layer)
	- add jetty plugin for testing
	- use oracle UCP as connection pool (included in pom.xml, the old testmybatis uses druid or simple jdbc)
	- upgrade spring version to support jdk8 (the old one is jdbc 6)

### table
            CREATE TABLE "APP_SCHEMA"."CUSTOMERS" 
            (	"SYS_HASHVAL" NUMBER INVISIBLE GENERATED ALWAYS AS (ORA_HASH("CUSTID")) VIRTUAL , 
                "CUSTID" VARCHAR2(60 BYTE), 
                "FIRSTNAME" VARCHAR2(60 BYTE), 
                "LASTNAME" VARCHAR2(60 BYTE), 
                "CLASS" VARCHAR2(10 BYTE), 
                "GEO" VARCHAR2(8 BYTE), 
                "CUSTPROFILE" VARCHAR2(4000 BYTE), 
                "PASSWD" RAW(60)
            ) 

### test
            mvn clean jetty:run -Djetty.port=8080
            http://localhost:8080/testmybatis/api/customer/99


