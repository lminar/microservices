# Job service

Every `5s` is executed scheduler that search for `NEW` job. Job message is send to rabbitMQ exchange `job-exchange` with default or specific routing key.

## Exchange binding
* If routing key is not present in job, the message is send will be deliver to default queue `job-queue`
* IF routing key is set in job, the message is will be deliver to queue with name `<routingKey>-queue`

## REST API

### Get job
```http
GET /v1/jobs/{id}
```

### Create job 

```http
POST /v1/jobs/{routingKey}?
```

```requestBody
{ 
  "id": string,
  "data": object
}
```

## Configuration parameters

