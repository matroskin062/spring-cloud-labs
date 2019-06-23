FROM alpine:3.6

MAINTAINER matroskin

RUN apk --no-cache add openjdk11 --repository=http://dl-cdn.alpinelinux.org/alpine/edge/community

RUN apk update && apk add --no-cache nss git curl
COPY ./config-awaiter.sh /cmd/awaiter.sh
RUN chmod +x /cmd/awaiter.sh