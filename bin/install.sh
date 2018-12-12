#!/bin/bash
#
# This is application installation file

set -e

version='0.0.1'

BUILD_FLAG=${1:--d}

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

suffix=''
prefix=''

# building image for airline api
if [[ $BUILD_FLAG != '-d' ]]; then
    suffix='-alpine'
    prefix='release.'
fi

# building images
# api
( cd ../api; docker build -t airline-api:${version}${suffix} -f ../etc/docker/images/api/${prefix}Dockerfile . )
# web
( cd ../web; docker build -t airline-web:${version}${suffix} -f ../etc/docker/images/web/${prefix}Dockerfile . )

# deploying application
docker stack deploy -c ../etc/docker/stack/${prefix}docker-compose.yml airline

popd > /dev/null
