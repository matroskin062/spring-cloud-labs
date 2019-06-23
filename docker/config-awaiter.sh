#!/bin/sh

while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://config-server:8888/ping)" != "200" ]]; do sleep 5 && echo "attempting to connect to config-server"; done