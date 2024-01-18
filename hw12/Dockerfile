# Установка JDK 17
FROM openjdk:17

# создаем директорию
RUN mkdir -p /app/

# Установка рабочей директории
WORKDIR /app/

# Копирование JAR файла в образ
COPY /target/hw12-auth-0.0.1-SNAPSHOT.jar /app/MyApp.jar

# Декларируем вывод порта наружу
EXPOSE 8080

# Запуск приложения из JAR файла
CMD ["java", "-jar", "MyApp.jar"]