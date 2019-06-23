build:
	cd client/client && mvn package
	cd consumer/consumer && mvn package
	cd config-server/config-server && mvn package
	cd eureka/eureka && mvn package
	cd rest-service/rest-service && mvn package