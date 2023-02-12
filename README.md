# Repository contains full sbt-projects on Spark/Scala #

### 1. KAFKA CONSUMER ###

**Description**: The application connects to the Kafka topic,
fetches data log, transforms it according to a task, then writes the final dataframe into HDFS/Hive.
Uses Spark Streaming. Can work in two modes: with batches or in continues processing.
Arguments and credentials are passed via Airflow dag.

**Business value**: Delivering real-time data for analytics.

### 2. A LOOP FOR PARTITIONS ###

**Description**: The application uploads historical data on partitions and appends new daily partitions
Contains simple logic for checking already existing partitions.

**Business value**: Allows to upload historical data convenient for further analytics, avoid duplicates, due to append mode saves computing resources.
