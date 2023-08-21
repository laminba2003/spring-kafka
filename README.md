# Spring Kafka

Traditional messaging queues like ActiveMQ, RabbitMQ can handle high throughput usually used for long-running or background jobs and communicating between services.

Kafka is a stream-processing platform built by LinkedIn and currently developed under the umbrella of the Apache Software Foundation. Kafka aims to provide low-latency ingestion of large amounts of event data.

We can use Kafka when we have to move a large amount of data and process it in real-time. An example would be when we want to process user behavior on our website to generate product suggestions or monitor events produced by our micro-services.

Kafka is built from ground up with horizontal scaling in mind. We can scale by adding more brokers to the existing Kafka cluster.

The key terminologies of Kafka are the following:

- Producer: A producer is a client that sends messages to the Kafka server to the specified topic.
- Consumer: Consumers are the recipients who receive messages from the Kafka server.
- Broker: A broker receives messages from producers and consumers fetch messages from the broker by topic, partition, and offset.
- Cluster: Kafka is a distributed system. A Kafka cluster contains multiple brokers sharing the workload.
- Topic: A topic is a category name to which messages are published and from which consumers can receive messages.
- Partition: Messages published to a topic are spread across a Kafka cluster into several partitions. Each partition can be associated with a broker to allow consumers to read from a topic in parallel.
- Offset: Offset is a pointer to the last message that Kafka has already sent to a consumer.

## Kafka Connect

Kafka Connect is a tool for scalable and reliable streaming data between Apache Kafka and other systems. It makes it simple to quickly define connectors that move large collections of data into and out of Kafka. Kafka Connect can ingest entire databases or collect metrics from all your application servers into Kafka topics, making the data available for stream processing with low latency. An export job can deliver data from Kafka topics into secondary storage and query systems or into batch systems for offline analysis.

## Kafka Streams

Kafka Streams is a client library for building applications and microservices, where the input and output data are stored in Kafka clusters. It combines the simplicity of writing and deploying standard Java and Scala applications on the client side with the benefits of Kafka's server-side cluster technology.

## KsqlDB

ksqlDB greatly reduces the operational complexity required to build stream processing applications, which enables you to build real-time systems without
requiring significant time and overhead. It combines the power of real-time stream processing with the approachable feel of a database, through
a familiar, lightweight SQL syntax. And because ksqlDB is natively powered by Apache Kafka®, it seamlessly leverages the underlying, battle-tested event
streaming platform.

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
