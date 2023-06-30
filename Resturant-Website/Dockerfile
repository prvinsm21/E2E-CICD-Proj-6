FROM adoptopenjdk/openjdk11:alpine-jre

ARG artifact=Resturant-Website/target/resturant-website.jar

WORKDIR /opt/app 


COPY ${artifact} app.jar 

ENTRYPOINT ["java", "-jar", "app.jar"]

