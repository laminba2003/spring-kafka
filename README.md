# Spring Kafka

## Start the Kafka broker

run this command to start all services in the correct order.

```bash
$ docker-compose up -d
```

## Create a topic

Kafka stores messages in topics. It’s good practice to explicitly create them before using them, even if Kafka is configured to automagically create them when referenced.

Run this command to create a new topic into which we’ll write and read some test messages.

```bash
$ docker exec broker kafka-topics --bootstrap-server broker:9092 --create --topic quickstart
```

## Write messages to the topic

```bash
$ docker exec --interactive --tty broker kafka-console-producer --bootstrap-server broker:9092 --topic quickstart
```

## Read messages from the topic

```bash
$ docker exec --interactive --tty broker kafka-console-consumer --bootstrap-server broker:9092 --topic quickstart --from-beginning
```

## Write some more messages

```bash
$ docker exec --interactive --tty broker kafka-console-producer --bootstrap-server broker:9092 --topic quickstart
```

## Stop the Kafka broker

```bash
docker-compose down
```
