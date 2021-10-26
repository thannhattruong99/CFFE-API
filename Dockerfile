FROM maven:latest
RUN mkdir /cffe
WORKDIR /cffe
COPY . .
EXPOSE 8080
CMD ["mvn", "spring-boot:run"]