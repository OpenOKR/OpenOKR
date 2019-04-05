FROM openjdk:7u121-jre

ENV PROJECT_NAME okr-m-web
ENV DEPLOY_DIR /data/webroot/okr
#ENV VM_OPTION "-XX:MaxPermSize=256m -Xmx512m"
RUN mkdir -p ${DEPLOY_DIR}
COPY ./target/${PROJECT_NAME}.jar ${DEPLOY_DIR}/${PROJECT_NAME}.jar
ENTRYPOINT java -jar -XX:MaxPermSize=256m -Xmx512m -XX:OnOutOfMemoryError="kill -9 %p" ${DEPLOY_DIR}/${PROJECT_NAME}.jar