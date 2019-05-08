mvn clean package
rm -rf ./tmp
mkdir ./tmp
cp -r target/app.war ./tmp/
docker build -t kyle11235/adwapp:latest .
rm -rf ./tmp