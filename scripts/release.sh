#!/bin/bash
set -e
set +x
export TAG_A=harbor.rand0m.me/public/${CI_PROJECT_NAME}:latest
export TAG_B=harbor.rand0m.me/public/${CI_PROJECT_NAME}:RELEASE

docker login -u ${CI_EMAIL} -p ${CI_PASSWORD} harbor.rand0m.me
docker pull ${TAG_A}
docker tag ${TAG_A} ${TAG_B}
docker push ${TAG_B}

docker login -u ${DH_USER} -p ${DH_PW}
docker pull ${TAG_A}
docker tag ${TAG_A} ${TAG_B}
docker push ${TAG_B}
