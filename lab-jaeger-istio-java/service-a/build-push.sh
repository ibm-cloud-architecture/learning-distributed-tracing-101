#!/bin/bash

#docker build . -t default-route-openshift-image-registry.apps-crc.testing/default/service-a-java-istio
#docker push default-route-openshift-image-registry.apps-crc.testing/default/service-a-java-istio

docker build . -t csantanapr/service-a-java-istio
docker push csantanapr/service-a-java-istio
