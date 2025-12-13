FROM node:20-bullseye AS build

WORKDIR /app

COPY --from=y2khub/clj2js /app/clj2js .

RUN git clone https://github.com/y2k/packages.git

COPY src/ src
COPY test/ test
COPY .github/ .github

RUN export PATH=$PATH:/app && export LY2K_PACKAGES_DIR=/app/packages && make test && rm -r .github/bin/test

FROM scratch

COPY --from=build /app/.github/bin /build_result

CMD [ "" ]
