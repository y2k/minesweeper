FROM node:20-bullseye AS build

WORKDIR /app

COPY --from=y2khub/clj2js /app/clj2js .

COPY src/ src
# COPY test/ test
COPY .github/ .github

RUN export PATH=$PATH:/app && cd .github && make

RUN ls -la .github/bin

FROM scratch

COPY --from=build /app/.github/bin /build_result

CMD [ "" ]
