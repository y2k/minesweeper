PRELUDE_PATH := $(shell realpath ../vendor/prelude/js/src/prelude.clj)

build: clean
	@ mkdir -p bin/src/common
	@ echo '{"type":"module"}' > bin/package.json
	@ clj2js js ../src/common/utils.clj $(PRELUDE_PATH) > bin/src/common/utils.js
	@ clj2js js ../src/main.clj $(PRELUDE_PATH) > bin/src/main.js
	@ clj2js js ../src/main.static.clj $(PRELUDE_PATH) > bin/src/main.static.js
	@ node bin/src/main.static.js > bin/index.html

clean:
	@ rm -rf bin

deploy:
	cd .. && docker build -f .github/Dockerfile -t y2khub/minesweeper .

web:
	@ cd bin && python3 -m http.server 8000

.PHONY: web build clean deploy
