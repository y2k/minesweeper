FROM node:20-bullseye AS build

WORKDIR /app

COPY src/ .

RUN ls  -la

# COPY project.clj .
# RUN lein do install, test

# COPY src src
# COPY test test

# RUN lein test
# RUN lein cljsbuild once min

# COPY resources resources

# FROM alpine

# COPY --from=build /app/resources/public /build_result