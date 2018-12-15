# airline-application
Application for airline company

## Description
Application consists of services:
 - **api** service (Spring)
 - **database** service (mariadb)
 - **web** service

Services configuration can be found at `/etc/docker/stack/docker-compose.yml`

## Requirements
 - [Docker](https://docs.docker.com/install/) (v 18.06 or higher)
 - Ports, user by application: 80, 3306, 8080

## Install
In your console, run: `./bin/install.sh` and airline application will be installed.

## Build
To build only images for services, run: `./bin/build.sh` and all images will be buildes.<br>

**Important note**<br>
Android application relies on _airline_api_address_ property, set at gradle properties, so before building android application, run `./bin/set_host.sh`<br>

Then, you can build android application itself, so navigate to android directory and run: `./gradlew build`

