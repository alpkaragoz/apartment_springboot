application.properties under resources is missing, here is a template:

spring.application.name=apartment
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/apartmentdb
spring.datasource.username=example
spring.datasource.password=example
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation= true
spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.PostgreSQLDialect

# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto= update

security.jwt.secret-key=example
# 1h in millisecond
security.jwt.expiration-time=3600000

spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=example
spring.mail.password=example
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
