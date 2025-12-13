.PHONY: test
test: build
	@ node .github/bin/test/main_test.js

.PHONY: build
build:
	@ ly2k compile -target eval -src build.clj > .github/Makefile
	@ $(MAKE) -f .github/Makefile
	@ ly2k generate -target js > .github/bin/src/prelude.js
	@ ly2k generate -target js > .github/bin/test/prelude.js

.PHONY: clean
clean:
	@ rm -rf .github/bin

.PHONY: deploy
deploy:
	@ docker build -f .github/Dockerfile -t y2khub/minesweeper .

.PHONY: web
web: build
	@ cd .github/bin && python3 -m http.server 8000
