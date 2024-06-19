# Spring Cloud Gateway API Routing

이 프로젝트는 Spring Cloud Gateway를 사용하여 Open API 대한 라우팅을 설정하는 방법을 보여줍니다.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Configuration](#configuration)
    - [application.yml](#applicationyml)
    - [Route Configuration](#route-configuration)
- [Running the Application](#running-the-application)

## Introduction

Spring Cloud Gateway는 현재 Spring 6 및 Spring Boot 3를 포함하여 Spring 생태계 위에 구축된 API 게이트웨이를 제공합니다.
API로 라우팅하는 간단하면서도 효과적인 방법을 제공하고, 이 코드에서는 Open API에 라우팅하는 것을 목표로 합니다.

ITS 국가교통정보센터의 가변형 속도제한표지정보(VSL) Open API를 활용하였습니다.
(https://www.its.go.kr/opendata/opendataList?service=vsl)


## Features

- 들어오는 요청을 다른 마이크로서비스로 라우팅합니다.
- `application.yml`을 사용하여 쉽게 구성할 수 있습니다.
- Open API에 라우팅하는 방법을 제공합니다.

## Getting Started

### Prerequisites

- Java 17
- Maven
- Spring Boot

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/spring-cloud-gateway-practice.git
   cd spring-cloud-gateway-practice

2. Build the project:
   ```bash
   ./mvnw clean install


## Configraion

application.yml
```
server:
  port: 8080

spring:
  cloud:
    gateway:
      routes:
        - id: vsl_service
          uri: https://openapi.its.go.kr:9443
          predicates:
            - Path=/vslInfo
          filters:
            - name: AddVSLOpenApiFilter
            - name: LoggingFilter

  main:
    web-application-type: reactive


logging:
  level:
    org.springframework.cloud.gateway: DEBUG
```

각 경로는 다음으로 구성됩니다:
- id: 경로의 고유 식별자입니다.
- uri: 라우팅할 URI 대상입니다.
- predicates: 경로가 일치하려면 충족해야 하는 조건입니다.
- filters: 요청 또는 응답에 적용할 수정 사항입니다.

추가적으로 application-secret.yml을 작성하여 Open Api - Key를 추가해줍니다.
(해당 파일은 project에 포함되어 있지 않습니다.)
application-secret.yml
```
spring:
  config:
    activate:
      on-profile: secret

vslOpenApi:
  api-key: *****************
```
api-key는 https://www.its.go.kr/opendata/opendataList?service=vsl 에서 신청하여 발급 받을 수 있습니다.


## Running the Application

애플리케이션을 실행하려면 다음 명령을 사용하세요.:
./mvnw spring-boot:run

