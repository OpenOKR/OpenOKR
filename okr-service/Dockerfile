FROM openjdk:8u171-jre

ENV USERNAME deploy
RUN useradd -ms /bin/bash ${USERNAME}
USER ${USERNAME}

ENV PROJECT_NAME okr-service
ENV DEPLOY_DIR /home/${USERNAME}/service
ENV VM_OPTION "-XX:MaxPermSize=256m -Xms128m -Xmx2g"

RUN mkdir -p ${DEPLOY_DIR}/${PROJECT_NAME}
COPY ./target/${PROJECT_NAME}/ ${DEPLOY_DIR}/${PROJECT_NAME}
ENTRYPOINT java  ${VM_OPTION}  -XX:OnOutOfMemoryError="kill -9 %p" -cp ${DEPLOY_DIR}/${PROJECT_NAME} org.springframework.boot.loader.JarLauncher