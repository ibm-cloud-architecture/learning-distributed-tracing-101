#!/bin/bash

#docker build . -t default-route-openshift-image-registry.apps-crc.testing/default/service-b-nodejs
#docker push default-route-openshift-image-registry.apps-crc.testing/default/service-b-nodejs

docker build . -t csantanapr/service-b-nodejs-istio
docker push csantanapr/service-b-nodejs-istio