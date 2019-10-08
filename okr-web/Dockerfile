FROM openjdk:7u121-jre

ENV USERNAME deploy
RUN useradd -ms /bin/bash ${USERNAME}
USER ${USERNAME}

ENV PROJECT_NAME okr-web
ENV DEPLOY_DIR /home/${USERNAME}/webroot
ENV VM_OPTION "-XX:MaxPermSize=256m -Xms128m -Xmx512m"

RUN mkdir -p ${DEPLOY_DIR}/${PROJECT_NAME}
COPY ./target/${PROJECT_NAME}/ ${DEPLOY_DIR}/${PROJECT_NAME}
ENTRYPOINT java  ${VM_OPTION}  -XX:OnOutOfMemoryError="kill -9 %p" -cp ${DEPLOY_DIR}/${PROJECT_NAME} org.springframework.boot.loader.JarLauncher