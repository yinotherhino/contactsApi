FROM adoptopenjdk/openjdk11:alpine-jre

ENV GRADLE_VERSION 7.4
RUN apk add --no-cache curl && \
    curl -L https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -o gradle-${GRADLE_VERSION}-bin.zip && \
    unzip gradle-${GRADLE_VERSION}-bin.zip -d /opt && \
    rm -f gradle-${GRADLE_VERSION}-bin.zip && \
    ln -s /opt/gradle-${GRADLE_VERSION}/bin/gradle /usr/bin/gradle && \
    mkdir /root/.gradle

RUN apk add --no-cache git

WORKDIR /app

COPY . .

RUN gradle clean build

EXPOSE 8080

CMD ["java", "-jar", "build/libs/demo.jar"]
