FROM ubuntu:24.04 AS build

WORKDIR /app

RUN apt-get update && apt-get install -y git make

COPY --from=y2khub/clj2js /app/ly2k /app/ly2k

COPY Makefile .
COPY build.clj .
COPY build/ build/
COPY src/ src/
COPY test/ test/

RUN git clone https://github.com/y2k/packages.git
ENV LY2K_PACKAGES_DIR=/app/packages

RUN PATH=$PATH:/app && make build

FROM node:22-bullseye AS test

WORKDIR /app

COPY --from=build /app .

RUN make test && rm -rf /app/.github/bin/test

FROM scratch
COPY --from=build /app/.github/bin /build_result
