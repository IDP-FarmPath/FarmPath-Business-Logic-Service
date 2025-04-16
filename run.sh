sudo docker volume prune  # y
sudo docker stop mobylab-app-db_mobylab-app-db_1
sudo docker rm mobylab-app-db_mobylab-app-db_1
sudo docker-compose -f ./docker-compose.yml -p mobylab-app-db up -d
mvn clean compile
mvn clean mvn clean spring-boot:run


