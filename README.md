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

You can use the kafka-console-producer command line tool to write messages to a topic. This is useful for experimentation (and troubleshooting), but in practice you’ll use the Producer API in your application code, or Kafka Connect for pulling data in from other systems to Kafka.

Run this command. You’ll notice that nothing seems to happen—fear not! It is waiting for your input.

```bash
$ docker exec --interactive --tty broker kafka-console-producer --bootstrap-server broker:9092 --topic quickstart
```

Type in some lines of text. Each line is a new message.

## Read messages from the topic

Now that we’ve written message to the topic, we’ll read those messages back. Run this command to launch the kafka-console-consumer. The --from-beginning argument means that messages will be read from the start of the topic.

```bash
$ docker exec --interactive --tty broker kafka-console-consumer --bootstrap-server broker:9092 --topic quickstart --from-beginning
```

As before, this is useful for trialling things on the command line, but in practice you’ll use the Consumer API in your application code, or Kafka Connect for reading data from Kafka to push to other systems.

You’ll see the messages that you entered in the previous step.

## Write some more messages

Leave the kafka-console-consumer command from the previous step running. If you’ve already closed it, just re-run it.

Now open a new terminal window and run the kafka-console-producer again.

```bash
$ docker exec --interactive --tty broker kafka-console-producer --bootstrap-server broker:9092 --topic quickstart
```

Enter some more messages and note how they are displayed almost instantaneously in the consumer terminal.

## Stop the Kafka broker

```bash
docker-compose down
```
