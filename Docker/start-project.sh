#!/bin/sh

cd $PWD
cd ..

PRJ_ROOT= $PWD

NETWORK_NAME=bloomberg-network
if [ -z $(docker network ls --filter name=^${NETWORK_NAME}$ --format="{{ .Name }}") ] ; then
     docker network create ${NETWORK_NAME} ;
fi


./mvnw clean package

cd Docker
docker-compose -f docker-compose.yml up