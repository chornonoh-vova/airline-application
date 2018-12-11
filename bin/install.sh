#!/bin/bash
#
# This is application installation file

set -e

BUILD_FLAG=${1:--d}

echo $BUILD_FLAG

SCRIPT_PATH=`dirname $0`
pushd $SCRIPT_PATH > /dev/null

init_secret() {
    local found=$(docker secret ls --format='{{.Name}}' --filter name=$1)
    if [ -z "${found}" -o "${found}" != $1 ]; then
        docker secret create $1 $2 > /dev/null
        echo "Created secret $1"
    else
        echo "Alreary have secret $1, skipping..."
    fi
}

# initializing swarm
if ! docker info 2> /dev/null | grep -qw 'Swarm: active'; then
    docker swarm init > /dev/null
fi

# creating secrets
openssl rand -base64 20 | tr -d '\n' | init_secret mysql_password -
openssl rand -base64 20 | tr -d '\n' | init_secret mysql_root_password -

# creating volume
docker volume create airline_database_data

# building image for airline api
if [[ $BUILD_FLAG = '-d' ]]; then
    ( cd ../api; docker build -t airline-api:0.0.1 -f ../etc/docker/images/api/Dockerfile . )
    # deploying application
    docker stack deploy -c ../etc/docker/stack/docker-compose.yml airline
else
    ( cd ../api; docker build -t airline-api:0.0.1-alpine -f ../etc/docker/images/api/release.Dockerfile . )
    # deploying application
    docker stack deploy -c ../etc/docker/stack/release.docker-compose.yml airline
fi

popd > /dev/null
