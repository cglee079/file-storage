###########################
##### RUN BUILD #####
###########################
FROM cglee079/file-storage:base  AS builder

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle --no-daemon --debug clean
RUN gradle --no-daemon --debug -x test build


WORKDIR /
RUN mkdir /app
RUN mkdir ls
RUN cp -r /home/gradle/src/build/libs/* /app


###########################
##### RUN APPLICATION #####
###########################
FROM arm64v8/openjdk:11-jdk

ARG HEAP_SIZE
ARG NEW_SIZE

COPY --from=builder /app /app

RUN echo "Asia/Seoul" > /etc/timezone

CMD java \
    -jar \
    -Dspring.profiles.active=prod \
    -Xms${HEAP_SIZE} \
    -Xmx${HEAP_SIZE} \
    -XX:NewSize=${NEW_SIZE} \
    -XX:MaxNewSize=${NEW_SIZE} \
    /app/$(ls /app | grep -E '.*\.jar')



