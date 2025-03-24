Bu proje, Zuul Edge Server uygulama Örneğidir.


NOT: Eureka server ve Product-service projelerinin Docker'da deploy edildiği varsayılmaktadır.

# Gereksinimler:
- Docker
- Docker Compose
- IntelliJ

# Projenin Çalıştırılması

## Docker Image'larının Oluşturulması

  Projeyi lokalimize çekelim maven ile clean-install yapalım ve intelliJ'e ait terminalde aşağıdaki komutu yazarak uygulama için image oluşturalım.

`    docker build -t zuul-edge-server:0.0.1 .
`

##  Docker Compose File dosyası ile deploy etme

-  zuul-edge-server.yml dosyası aşağıdaki gibidir.

```
version: '3.3'

services:
  zuul-edge-server:
    container_name: zuul-edge-server
    hostname: zuul-edge-server
    image: zuul-edge-server:0.0.1
    networks:
      - springboot-mysql-network
    expose:
      - 8762
    ports:
      - 8762:8762
    deploy:
      replicas: 1
      restart_policy:
        condition: on-failure
        delay: 5s
        max_attempts: 3
        window: 120s
      resources:
        reservations:
          cpus: "0.50"
          memory: 512M
        limits:
          cpus: "1.0"
          memory: 1G
    environment:
      - "SPRING_PROFILES_ACTIVE=stage"
    entrypoint: [ "java","-jar","app.jar" ]
networks:
  springboot-mysql-network:
    name: springboot-mysql-network
```

Docker deploy komutu aşağıdaki gibidir:

> docker-compose -p eureka-example  -f zuul-edge-server.yml up -d



