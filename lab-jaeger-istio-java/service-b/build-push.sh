#!/bin/bash

#docker build . -t default-route-openshift-image-registry.apps-crc.testing/default/service-b-java-istio
#docker push default-route-openshift-image-registry.apps-crc.testing/default/service-b-java-istio

docker build . -t csantanapr/service-b-java-istio
docker push csantanapr/service-b-java-istio