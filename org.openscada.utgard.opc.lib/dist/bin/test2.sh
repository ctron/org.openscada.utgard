#!/bin/bash

source ./preferences.sh

$JAVA -cp "$TEST_CLASSPATH" org.openscada.opc.lib.test.OPCTest2 "$HOST" "$DOMAIN" "$USER" "$PASSWORD" "$UUID" $1
