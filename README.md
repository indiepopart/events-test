# Fever Challenge


## Assumptions

- About the event model from the provider: Different `base_event` might contain the same `event_id`. For simplicity it is assumed `base_event_id` is unique. `event_id` is ignored.
- About latest known values: it means the last known values while the event was online. Min and max price will be derived from the last set of zones returned from the provider in the event.
- An invalid date returned from the provider is ignored and the value considered as empty (null).

## Design

The proposed solution has 3 components:

**Fetch Application**: A standalone Spring Boot application that calls the provider endpoint at a fixed configurable delay and inserts documents into Elasticsearch.

**Search Mircoservice**: A Spring Boot web API implementing the `/search` endpoint as required. Retrieves events from Elasticsearch.

**Elasticsearch**: As the data store and search engine for events history.


## Prerequisites

- [Java 21](https://jdk.java.net/java-se-ri/21)
- [Docker](https://docs.docker.com/desktop/)

## How to Run

First run the `fetch` standalone application, as it will start the `elasticsearch` and `kibana` containers:

```shell
cd fecth
./mvnw spring-boot:run
```

Then run the `search` microservice:

```shell
cd search
./mvnw spring-boot:run
```

You can query the `search` microservice with the following cURL line:

```shell
curl -G -d "starts_at=2017-07-21T17:32:28Z" -d "ends_at=2026-11-21T17:32:28Z" http://localhost:8080/search
```

Or with HTTPie:

```shell
http -vv :8080/search starts_at==2017-07-21T17:32:28Z ends_at==2026-11-21T17:32:28Z
```

You can inspect the `events` index through Kibana at http://localhost:5601.


## Scalability Strategies

How to scale this application to focus on performance. The provider response can contain thousands of events with hundreds of zones each. The endpoint must support peaks of traffic between 5k/10k request per second. The endpoint should be fast in hundred of ms magnitude order.

- Provider data processing optimizations: Avoid min, max recalculations. Parallelize min, max calculations.
- Elasticsearch sharding: Documents are distributed in an index across ultiple shards, across multiple nodes, to increase capacity.
- Follow Elasticsearch recommendations for search speed
- Elasticsearch location: Deploy as close to the consumer service to minimize network delays
- Horizontal microservice scalability through replicas. Load balancing and autoscaling are common techniques.
