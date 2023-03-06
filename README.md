Hotel Searches
==============

Following the given specification, the web service exposes two endpoints:

- `POST /search`: sends a hotel search.
- `GET /count?searchId=aSearchId`: gets the number of searches that are equals to the search with the given ID.

The project uses Docker to set up Kafka, Kafka-Connect, Zookeeper and MongoDB. One instance of each service on its own
container.

New hotel searches are sent to a Kafka topic and are stored through Kafka-Connect on a MongoDB database. The number of 
reservations is retrieved directly from the database.

Before running the integration tests it is required to deploy the docker containers.

> **warning:** The application is not production ready.

## Requirements

- Java 17
- Docker


## Building and execution

To deploy the containers execute:
```
docker-compose up -d
```

To build the project execute:
```
./mvnw clean install
```
Options:
- Skip unit test: ```-DskipTests```
- Run integration test: ```-Pintegraion```

