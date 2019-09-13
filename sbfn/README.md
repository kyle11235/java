
# sbfn

- db

        create user fnusername identified by "xxx";
        grant dwrole to fnusername;
        ALTER USER fnusername quota unlimited on DATA;

        login with fnusername

        create table EMPLOYEES (
                EMP_EMAIL    varchar2(100),  
                EMP_NAME    varchar2(100),  
                EMP_DEPT    varchar2(50),
                constraint pk_emp primary key (EMP_EMAIL)
        )

- local test

        cd lib && ./install.sh
        cd .. && mvn test

- docker test

        remove skip test in Dockerfile

- local server test

        memory counting bug

- remote server

        fn create app --annotation oracle.com/oci/subnetIds='["x.x.x.x"]' --config DB_PASSWORD=Abcde,12345. --config DB_SERVICE_NAME=sr081400adw_medium --config DB_USER=fnusername sbfn

        ./deploy.sh
        ./test.sh

- error

        - could not resolve the connect identifier  "sr081400adw_medium" -> wrong wallet location
        - no suitable driver -> ucp is not included
