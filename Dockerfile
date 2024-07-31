# Используем образ Maven для сборки приложения
FROM maven:3.8.5-openjdk-17 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл pom.xml и скачиваем зависимости
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Переходим к использованию легковесного образа OpenJDK для запуска приложения
FROM openjdk:17-jdk-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR-файл из стадии сборки
COPY --from=build /app/target/AccountManagement-0.0.1-SNAPSHOT.jar /app/AccountManagement-0.0.1-SNAPSHOT.jar

# Открываем порт, на котором будет работать приложение
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "/app/AccountManagement-0.0.1-SNAPSHOT.jar"]
