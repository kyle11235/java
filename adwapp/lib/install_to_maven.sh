mvn install:install-file -Dfile=ojdbc8.jar -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=1.0.0.1 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=oraclepki.jar -DgroupId=com.oracle -DartifactId=oraclepki -Dversion=1.0.0.1 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=osdt_cert.jar -DgroupId=com.oracle -DartifactId=osdt_cert -Dversion=1.0.0.1 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=osdt_core.jar -DgroupId=com.oracle -DartifactId=osdt_core -Dversion=1.0.0.1 -Dpackaging=jar -DgeneratePom=true