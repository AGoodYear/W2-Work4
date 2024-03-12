FROM openjdk:8
LABEL authors="Aurora"
LABEL image.name="Danmaku"
ADD W4R3-0.0.1-SNAPSHOT.jar /W4R3.jar
RUN bash -c 'touch /W4R3.jar'
EXPOSE 8080

ENV TZ=Asia/Shanghai
#设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/timezone

#设置编码
ENV LANG C.UTF-8


ENTRYPOINT ["java", "-jar","/W4R3.jar"]