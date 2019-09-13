FROM fnproject/fn-java-fdk-build:jdk9-1.0.83 as build-stage
WORKDIR /function

COPY pom.xml .
COPY src ./src
COPY lib ./lib
COPY wallet ./wallet

RUN cd lib && ./install.sh
RUN cd /function
RUN ["mvn", "package", "dependency:copy-dependencies", "-DincludeScope=runtime", "-DskipTests=true", "-Dmdep.prependGroupId=true", "-DoutputDirectory=target", "--fail-never"]

FROM fnproject/fn-java-fdk:jdk9-1.0.83
WORKDIR /function
COPY wallet ./wallet
COPY --from=build-stage /function/target/*.jar /function/app/

CMD ["io.fnproject.example.CreateEmployeeFunction::handle"]