# Kafka with OAuth Bearer Authentication using Keycloak

This project demonstrates the integration of Kafka with OAuth Bearer Authentication, utilizing Keycloak as the identity and access management solution. The Kafka producer application in this project communicates with a Kafka broker that is secured with OAuth 2.0 authentication, ensuring only authorized users can interact with the Kafka cluster.

## Project Overview

- **Kafka**: A distributed streaming platform for building real-time data pipelines and streaming applications.
- **OAuth Bearer Authentication**: The project uses OAuth 2.0 authentication to secure Kafka endpoints. Keycloak is used as the authentication server for managing OAuth tokens.
- **Keycloak**: An open-source identity and access management solution that provides OAuth 2.0 and OpenID Connect protocols.
- **Kafka Producer**: The project includes a Kafka producer application that sends messages to Kafka topics after being authenticated using OAuth Bearer tokens issued by Keycloak.

   ```bash
   docker-compose up -d
   ```
