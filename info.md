http://localhost:8091/notifications/send
{
"email": "test@test.ru",
"subject": "test sub",
"text": "это тестовое сообщение"
}

http://localhost:8091/notifications/create
{
"email": "user@example.com"
}

http://localhost:8091/notifications/delete
{
"email": "user@example.com"
}

https://docs.spring.io/spring-kafka/reference/testing.html

https://stackoverflow.com/questions/69036197/embedded-kafka-integration-test-consumer-never-completes

https://docs.spring.io/spring-boot/reference/messaging/kafka.html#messaging.kafka.embedded