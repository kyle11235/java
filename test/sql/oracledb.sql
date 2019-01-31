
create user root identified by root;

grant dba to root;

conn root/root

CREATE TABLE test (
  id number(11) NOT NULL,
  name varchar(200) NOT NULL,
  address varchar(200) NOT NULL,
  email varchar(200) NOT NULL,
  phone varchar(20) NOT NULL,
  PRIMARY KEY (id)
); 

CREATE SEQUENCE s_test start with 1 increment by 1;

INSERT INTO test (id, name, address, email, phone) VALUES(s_test.nextval, 'kyle', 'z', 'x@x.com', '123');


