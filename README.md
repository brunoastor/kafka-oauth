# Kafka with OAuth Bearer Authentication using Keycloak

This project demonstrates the integration of Kafka with OAuth Bearer Authentication, utilizing Keycloak as the identity and access management solution. The Kafka producer application in this project communicates with a Kafka broker that is secured with OAuth 2.0 authentication, ensuring only authorized users can interact with the Kafka cluster.

## Project Overview

- **Kafka**: A distributed streaming platform for building real-time data pipelines and streaming applications.
- **OAuth Bearer Authentication**: The project uses OAuth 2.0 authentication to secure Kafka endpoints. Keycloak is used as the authentication server for managing OAuth tokens.
- **Keycloak**: An open-source identity and access management solution that provides OAuth 2.0 and OpenID Connect protocols.
- **Kafka Producer**: The project includes a Kafka producer application that sends messages to Kafka topics after being authenticated using OAuth Bearer tokens issued by Keycloak.

## Prerequisites

Make sure you have the following installed:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html) or [OpenJDK](https://openjdk.org/)
- [Maven](https://maven.apache.org/install.html)

## Steps to Start the Environment

**Clone the repository**

1. Open a terminal and navigate to the directory containing the `docker-compose.yml` file.

   ```bash
   cd /path/to/your/project
   ```

2. Run the following command to start the Docker Compose environment:

   ```bash
   docker-compose up
   ```

   This will build (if necessary) and start all the services defined in your `docker-compose.yml` file.

3. To run the containers in detached mode (in the background), add the `-d` flag:

   ```bash
   docker-compose up -d
   ```

## Running the Kafka Producer Application

The project includes a Kafka producer application located in the `kafka-producer` directory. Follow these steps to run it:

### Using an IDE (e.g., IntelliJ IDEA or Eclipse)

1. Open your IDE and import the project located in `kafka-producer`.
2. Ensure the required dependencies are downloaded (your IDE should handle this automatically if Maven is configured).
3. Navigate to the main class of the application (e.g., `org.example.Main.java`).
4. Run the application directly from the IDE.

### Using Maven

1. Open a terminal and navigate to the `kafka-producer` directory:

   ```bash
   cd /path/to/your/project/kafka-producer
   ```

2. Build the application:

   ```bash
   mvn clean install
   ```

3. Run the application:

   ```bash
   mvn exec:java -Dexec.mainClass="org.example.Main.java"
   ```

## Stopping the Environment

To stop and remove the containers, run:

```bash
docker-compose down
```

## Additional Commands

- To view the logs of a specific service:

  ```bash
  docker-compose logs <service_name>
  ```

- To rebuild the containers:

  ```bash
  docker-compose up --build
  ```
