docker stop testapp
docker rm testapp
docker run --name testapp -d -p 8080:8080 -v $ENV_CONFIG_HOME:/root/.app kyle11235/testapp