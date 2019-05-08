
# connect to/manage adw using java

- config

		update files in adwapp/config and make below environment variable point to where you placed config folder. e.g.
		export ENV_CONFIG_HOME=/Users/kyle/workspace/java/adwapp/config
		if not set, $HOME/.adw will be used

- develop

		cd app/lib
		./install_to_maven.sh
		cd app
		mvn spring-boot:run
		http://localhost:8080
		
- package

		cd app
		mvn clean package
		
- start

		nohup java -jar app.war > out.log 2>&1 &
		
- build docker image

		cd app
		chmod +x builddocker.sh && ./builddocker.sh

- start in docker

		cd app
		chmod +x startdocker.sh && ./startdocker.sh

- visit app

		http://localhost:8080