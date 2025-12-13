FROM node:22-bullseye AS build
WORKDIR /app

COPY --from=y2khub/clj2js /app/ly2k .
RUN git clone https://github.com/y2k/packages.git

ENV PATH="$PATH:/app" \
    LY2K_PACKAGES_DIR=/app/packages

COPY Makefile .
COPY src/ src/
COPY test/ test/

RUN make test && rm -rf .github/bin/test

FROM scratch
COPY --from=build /app/.github/bin /build_result
