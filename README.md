# Modules

## Installation via Gradle Composite Build

1.  Add this repository as a
    [git submodule](https://git-scm.com/book/en/v2/Git-Tools-Submodules)
    to your project

1.  Add these lines to your `settings.gradle.kts`:

    ```kotlin
    includeBuild("Backend-common")
    ```

1.  Use TLK modules as dependencies of your project

    ```kotlin
    dependencies {
        implementation("ru.touchin:common")
    }
    ```

## common

Набор утилит, структур данных, исключений без привязки к `spring`

* `ExecutionContext` - класс для хранения/получения данных из текущего потока
* `SecureRandomStringGenerator` - генератор случайной строки по словарю
* `errors.*` - базовые исключения
* `<type>.*Utils` - утилиты для различных типов данных

## common-spring

Набор утилит, структур данных для `spring`, без привязки к доп. модулям, таким как `jpa` и `web`

## common-spring-jpa

* `models.*` - базовые `Entity`
* `repositories` - утилиты и доп. интерфейсы для репозиториев
* `liquibase.LiquibaseStart` - для подключения этого компонента необходимо объявить `liquibase.LiquibaseParams`.
  Указать путь к файлу с миграцией и схему, для которой эта миграция будет применяться. С помощью `LiquibaseStart`
  можно применить кастомную миграцию не конфликтуя с основной.
* `EnableJpaAuditingExtra` - подключение `JpaAuditing` с поддержкой типа `ZoneDateTime`

## common-spring-web

* `request.Utils` - различные `extensions` для работы с `HttpServletRequest`
* `errors.*` - исключения и типы данных для `web`
* `webclient.*` - классы для расширения webclient, включая логирование

## common-spring-security

* `configurations.DefaultSecurityConfiguration` - дефолтная реализация WebSecurity,
 определяет для каких request path надо ограничить доступ.
 Использует `url.interceptors.UrlExpressionRegistryInterceptor` для принятия решения.
* `auditor.AuditorResolver` - служит для преобразования `principal` в строку, используется с `JpaAuditing`

## common-spring-security-jpa

* `auditor.SecurityAuditorAware` - резолвит имя пользователя для полей `@CreatedBy`, `@LastModifiedBy`.
 Требуется явно создать бин `AuditorAware<String>` в проекте.

## common-spring-test

Утилиты для тестирования в среде `spring-test`

## common-spring-test-jpa

Утилиты для тестирования репозиториев

## logger

Основные компоненты логирования:

* layout
* context
* format

## logger-spring

Встраивание системы логирования в `spring`

* autologging
* serializer

## logger-spring-web

Interceptor для логирования запросов/ответов.

## exception-handler-spring-web

Перехватывает ошибки сервера, определяет код ошибки и возвращает их в правильный `response`.
Подключается с помощью аннотации `@EnableSpringExceptionHandler`

## exception-handler-logger-spring-web

Добавляет логирование в обработку ошибок.
Подключается с помощью аннотации `@EnableSpringExceptionHandlerLogger` до подключения основного модуля.

## validation-spring

Добавляет аннотации для валидации запросов.

## version-spring-web

Добавляет возможность задавать версию апи через `properties` без необходимости явно указывать в каждом маппинге

## response-wrapper-spring-web

Добавляет обертку для успешного ответа

## common-measure

Утилиты для работы с `measure`

## common-measure-spring

Возможность задавать `measure` через `properties`

## common-geo

Интерфейс для работы с гео-данными

## common-geo-spatial4j-spring

Реализация интерфейса `GeoCalculator` с помощью библиотеки `spatial4j`

## settings-spring-jpa

Модуль для хранения настроек

## security-authorization-server-core

Модуль авторизации

## security-authorization-server-jwt-core

Добавляет поддержку jwt-токенов (создание/хранение). Для работы этого модуля требуется прописать в пропертях:

``` yaml
token.access:
  issuer: ${app.issuer}
  timeToLive: PT15M # 15 minutes
  signatureAlgorithm: RS256
  keyPair:
    public: |
      -----BEGIN PUBLIC KEY-----
      -----END PUBLIC KEY-----
    private: |
      -----BEGIN PRIVATE KEY-----
      -----END PRIVATE KEY-----
token.refresh:
  length: 20
  prefix: RT-
  timeToLive: PT2H # 2 hours
```

Генерация ключей:

```bash
openssl genrsa -out private.pem 4096
openssl rsa -in private.pem -pubout -out public.pem
openssl pkcs8 -topk8 -inform PEM -in private.pem -out private_key.pem -nocrypt

cat private_key.pem
cat public.pem
```

## security-authorization-server-oauth2-metadata

OAuth2 metadata support.

## security-jwt-common

JWT related utilities.

## security-resource-server-default-configuration

Default configuration for the Spring OAuth2 resource server with JWT auth.

## security-resource-server-custom-configuration

Custom configuration for the Spring OAuth2 resource server with JWT auth. Requires the following properties:

``` yaml
token.access:
  issuer: ${app.issuer}
  signatureAlgorithm: RS256
  keyPair:
    public: |
      -----BEGIN PUBLIC KEY-----
      -----END PUBLIC KEY-----
```

## security-resource-server-test-configuration

Disables Spring OAuth2 resource server for testing.

## s3-storage

Amazon S3 support.

## server-info-spring-web

Allow include headers with information about the server in responses

To get started you need:
1) Add annotation to configuration
2) Add property to yml/properties file:
```
server.info:
    buildVersion: ${buildVersion}
```
3) Implement ServerInfoService (optional. If you want to add other headers)
4) Add dir with impl ServerInfoService in ComponentScan annotation 

## push-message-provider

Интерфейсы и компоненты для модулей по обеспечению интеграции с сервисами отправки пуш-уведомлений.

## push-message-provider-fcm

Модуль по обеспечению интеграции с Firebase Cloud Messaging. 
1) Подключение компонентов Spring осуществляется при помощи аннотации `@EnablePushMessageProviderFcm`.
2) Необходимо добавление конфигурации для модуля с выбранным способом хранения данных для авторизации. Пример файла конфигурации в формате yaml:
``` yaml
push-message-provider:
  platformProviders:
    ANDROID_GOOGLE:
      - FCM
    IOS:
      - FCM
  fcm:
    appName: # Название приложения
    auth:
      # Выбранный тип авторизации
    client:
      readTimeout: 10s
      connectionTimeout: 1s
```
3) Настраивается способ предоставления авторизации для Firebase Cloud Messaging.

А) Токен доступа из консоли Google, добавляемый в конфигурацию настроек:
``` yaml
    auth:
      token:
        value: testValue
        expiresAt: 2023-01-01 23:59:59 +00:00
```
B) Данные в файле из консоли Firebase, добавляемые в resources с обозначением пути в конфигурации настроек:
``` yaml
    auth:
      credentialsFile:
        path: credentials/firebase-admin.json
```
C) Данные из файла консоли Firebase, добавляемые в конфигурацию настроек:
``` yaml
    auth:
      credentialsData:
        type: service_account
        projectId: testProjectId
        privateKeyId: testPrivateKeyId
        privateKey: |
          -----BEGIN PRIVATE KEY-----
          MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALfBshaLMW2yddmAZJRNXTZzcSbwvY93Dnjj6naWgoBJoB3mOM5bcoyWwBw12A4rwecorz74OUOc6zdqX3j8hwsSyzgAUStKM5PkOvPNRKsI4eXAWU0fmb8h1jyXwftl7EzeBjEMBTpyXkgDk3wLfHN6ciCZrnQndOvS+mMl3b0hAgMBAAECgYEAmIQZByMSrITR0ewCDyFDO52HjhWEkF310hsBkNoNiOMTFZ3vCj/WjJ/W5dM+90wUTYN0KOSnytmkVUNh6K5Yekn+yRg/mBRTwwn88hU6umB8tUqoNz7AyUltAOGyQMWqAAcVgxV+mAp/Y018j69poEHgrW4qKol65/NRZyV7/J0CQQD4rCDjmxGEuA1yMzL2i8NyNl/5vvLVfLcEnVqpHbc1+KfUHZuY7iv38xpzfmErqhCxAXfQ52edq5rXmMIVSbFrAkEAvSvfSSK9XQDJl3NEyfR3BGbsoqKIYOuJAnv4OQPSODZfTNWhc11S8y914qaSWB+Iid9HoLvAIgPH5mrzPzjSowJBAJcw4FZCI+aTmOlEI8ous8gvMy8/X5lZWFUf7s0/2fKgmjmnPsE+ndEFJ6HsxturbLaR8+05pJAClARdRjN3OL0CQGoF+8gmw1ErztCmVyiFbms2MGxagesoN4r/5jg2Tw0YVENg/HMHHCWWNREJ4L2pNsJnNOL+N4oY6mHXEWwesdcCQCUYTfLYxi+Wg/5BSC7fgl/gu0mlx07AzMoMQLDOXdisV5rpxrOoT3BOLBqyccv37AZ3e2gqb8JYyNzO6C0zswQ=
          -----END PRIVATE KEY-----
        clientEmail: testClientEmail
        clientId: testClientId
        authUri: testAuthUri
        tokenUri: testTokenUri
        authProviderX509CertUrl: testAuthProviderX509CertUrl
        clientX509CertUrl: testClientX509CertUrl
```
