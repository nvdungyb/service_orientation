# Dockerfile
FROM openjdk:21-jdk-slim
  
  # Tạo thư mục làm việc trong container
WORKDIR /app
  
  # Copy file JAR đã build vào container (giả sử tên jar là app.jar)
COPY target/*.jar app.jar
  
  # Mở cổng (tuỳ từng service mà bạn expose khác nhau)
EXPOSE 8081
  
  # Lệnh để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
