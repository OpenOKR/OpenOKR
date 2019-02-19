FROM nginx

ENV DEPLOY_DIR /data/front-end/html

RUN mkdir -p ${DEPLOY_DIR}

COPY ./build/ ${DEPLOY_DIR}/
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf
