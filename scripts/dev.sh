#!/bin/bash


cd $(dirname ${0})/..

export BASE=$(pwd)

rm -rf ${BASE}/tmp
mkdir ${BASE}/tmp
cd ${BASE}/tmp
cp ${BASE}/vrtlr-app/config/app* .
cp ${BASE}/vrtlr-app/target/vrtlr-app.jar .
unzip -qq vrtlr-app.jar
rm -rf ${BASE}/tmp/BOOT-INF/lib/vrtlr*
rm -rf ${BASE}/tmp/BOOT-INF/lib/vrtlr*
rm -rf ${BASE}/vrtlr-impl/target/classes/public
rm -rf ${BASE}/vrtlr-impl/target/classes/templates


java \
  -cp ${BASE}/vrtlr-api/target/classes:${BASE}/vrtlr-impl/target/classes:${BASE}/vrtlr-app/target/classes:${BASE}/vrtlr-impl/src/main/resources:${BASE}/tmp/BOOT-INF/lib/* \
  -Ddatadir=${BASE}/data \
  -Dspring.thymeleaf.cache=false \
  de.vrtlr.app.ShrtnrApp
