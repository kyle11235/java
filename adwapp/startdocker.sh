docker stop adwapp
docker rm adwapp
docker run --name adwapp -d -p 8080:8080 -v $ENV_CONFIG_HOME:/root/.adw kyle11235/adwapp