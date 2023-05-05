# 1. Usage
## 1.1. Start a postgres server and create a database.
Start a postgres server and create a database.

```shell
% ls | grep docker-compose.yml
docker-compose.yml
% docker-compose up -d
% docker ps 
CONTAINER ID   IMAGE               COMMAND                  CREATED         STATUS         PORTS                    NAMES
d04bf0306a0b   ktor-app-postgres   "docker-entrypoint.s…"   4 minutes ago   Up 4 minutes   0.0.0.0:5432->5432/tcp   local-pg
% 
```

## 1.2. Migrate database schema
Run `./gradlew flywayMigrate` if environments are passed in terminal or set directly in `application.conf`.

```shell
% JWT_SECRET=this-is-a-secret DB_URL=jdbc:postgresql://localhost:5432/app_db DB_USER=super-user DB_PASSWORD=dummyPass ./gradlew flywayMigrate

BUILD SUCCESSFUL in 908ms
1 actionable task: 1 executed
% 
```

## 1.3. Run the app
Run `./gradlew run` if environments are passed in terminal or set directly in `application.conf`.

```shell
% JWT_SECRET=this-is-a-secret DB_URL=jdbc:postgresql://localhost:5432/app_db DB_USER=super-user DB_PASSWORD=dummyPass ./gradlew run          

> Task :compileKotlin
...
...
...

```


# 2. Check the operation of each API endpoint
## 2.1. healthRoute > `GET /health`

```shell
% curl -s -X GET \
 'http://localhost:8081/health'
REST API health check succeeded!!
%
```

## 2.2. healthRoute > `GET /`

```shell
% curl -s -X GET \
 'http://localhost:8081/'
<!DOCTYPE html>
<html>
  <body>OK</body>
</html>
%
```

## 2.3. authRoute > `POST /auth/register`

```shell
% curl -s -X POST \
   -H "Content-Type:application/json" \
   -d \
'{
  "email": "abc01@test.com",
  "password": "123"
}' \
 'http://localhost:8081/auth/register'
Success!
%
```

## 2.4. authRoute > `POST /auth/login`

```shell
% curl -s -X POST \
   -H "Content-Type:application/json" \
   -d \
'{
  "email": "abc01@test.com",
  "password": "123"
}' \
 'http://localhost:8081/auth/login' jq -r '.'
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJ0aG91Z2h0cyIsImlzcyI6Imh0dHA6Ly8wLjAuMC4wOjgwODAvIiwiZW1haWwiOiJhYmMwMUB0ZXN0LmNvbSIsImV4cCI6MTY4MzI5NDgxOH0.9tmNN-fF4no_MzHoYnky2uhmyeCojtaHpUyGysk5uSM"
}
%
```

## 2.5. userRoute > `GET /users/profiles`

```shell
% curl -s -X GET \
   -H "Authorization:Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJ0aG91Z2h0cyIsImlzcyI6Imh0dHA6Ly8wLjAuMC4wOjgwODAvIiwiZW1haWwiOiJhYmNAdGVzdC5jb20iLCJleHAiOjE2ODMyOTQ4MTh9.zCQ-yLGHDiJHfqtZHAv7-y2wnCzgLp_oJeVgX2Ktmfw" \
 'http://localhost:8081/users/profiles' | jq -r '.'
[
  {
    "id": 1,
    "email": "abc@test.com",
    "password": "$2a$10$6vU0JXRW9QJBHZfOMKX70eFHVqntgF5X0OxTyz2IAhBjUnfFCvqRe"
  },
  {
    "id": 8,
    "email": "abc01@test.com",
    "password": "$2a$10$.vECiSyRusiQjjBmG4zHG.6tAQvTBD5M9Z7bhPai0X/lC1x8xS/Ia"
  }
]
%
```