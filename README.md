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

Перехватывает ошибки сервера, определяет код ошибки и возвращает их в правильный `response`

## exception-handler-logger-spring-web

Добавляет логирование в обработку ошибок

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

## auth-core

Модуль авторизации

## auth-jwt-core

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
