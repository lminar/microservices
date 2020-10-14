# Microservices

## Environment
Setup `PostgreSQL` and `RabbitMQ`
```command
docker-compose up -d
```

### PostgreSQL
```detail
port: 5432
credentials: user/a
database: job-data
```

### RabbitMQ
```detail
port: 5672
credentials: guest/guest
administration: http://localhost:15672
```