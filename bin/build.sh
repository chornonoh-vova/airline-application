#!/bin/bash
#
# Script to build all images

set -e

SCRIPT_PATH=`dirname $0`
pushd $SCRIPT_PATH > /dev/null

# build api image
( cd ../api; docker build -t airline-api:0.0.1 -f ../etc/docker/images/api/Dockerfile . )

# build web image
( cd ../web; docker build -t airline-web:0.0.1 -f ../etc/docker/images/web/Dockerfile . )

popd > /dev/null
