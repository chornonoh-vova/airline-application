#!/bin/bash
#
# Script to set api ip before building android app
# During build, ip address will be taken from property
# In runtime, android application can access it
# from buildConfigField or string resources

set -e

# Property name
API_ADDRESS='airline_application_api_address'
# File, where properties located
PROPERTIES_FILE=$HOME/.gradle/gradle.properties

set_address() {
    local IP=$1
    if ! grep -q "$API_ADDRESS" $PROPERTIES_FILE; then
        echo "Don't have entry yet, adding..."
        echo -e "$API_ADDRESS=\"$IP\"" >> $PROPERTIES_FILE
    else
        echo "Already have entry, replacing..."
        sed -i -e "s|$API_ADDRESS=\".*\"|$API_ADDRESS=\"$IP\"|g" $PROPERTIES_FILE
    fi
    echo "Properties updated"
}

if [ -z $1 ]; then
    # Interactive mode
    echo "Select IP address to add to properties file:"
    select IP in $(hostname -I); do
        echo "You selected: $IP"
        set_address $IP
        exit 0;
    done
else
    # Auto mode(ip is taken from command-line arguments)
    echo "Setting $1 in auto mode"
    set_address $1
    exit 0
fi
