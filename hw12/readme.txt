docker build -t hello-world .

docker run --name hello -d --rm -p 8080:8080 hello-world

docker-compose up -d
