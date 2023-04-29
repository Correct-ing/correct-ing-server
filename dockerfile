FROM openjdk:17-jre-slim
RUN apt-get update && apt-get install -y ffmpeg
RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
COPY build/libs/correcting-*.jar app.jar
COPY scripts/deploy.sh deploy.sh
RUN chmod a+x deploy.sh
EXPOSE 8080
ENTRYPOINT ["./deploy.sh"]