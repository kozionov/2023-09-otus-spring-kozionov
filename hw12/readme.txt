docker build -t hello-world .

docker run --name hello -d --rm -p 8080:8080 hello-world

docker-compose up -d

Создать папку на диске
docker volume create --driver local --opt type=none --opt device=/mnt/docker/volumes/mysql-database --opt o=bind mysql-database
docker volume create --driver local --opt type=none --opt device=C:/test/mnt/docker/volumes/mysql-database --opt o=bind mysql-database


Использовать эту папку
volumes:
  database:
    name: mysql-database
    external: true