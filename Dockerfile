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
FROM openjdk:11.0.16-jdk

ARG HEAP_SIZE
ENV HEAP_SIZE=${HEAP_SIZE:-512M}
ARG NEW_SIZE
ENV NEW_SIZE=${NEW_SIZE:-256M}

COPY --from=builder /app /app

RUN echo "Asia/Seoul" > /etc/timezone

CMD java \
    -jar \
    -Dspring.profiles.active=prod \
#    -Xms${HEAP_SIZE} \
#    -Xmx${HEAP_SIZE} \
#    -XX:NewSize=${NEW_SIZE} \
#    -XX:MaxNewSize=${NEW_SIZE} \
    /app/$(ls /app | grep -E '.*\.jar')



