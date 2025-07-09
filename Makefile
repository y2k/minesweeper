.PHONY: test
test: build
	@ node .github/bin/test/test.js

.PHONY: build
build:
	@ export OCAMLRUNPARAM=b && \
		~/Projects/language/_build/default/bin/main.exe compile -target eval -src build.clj > .github/Makefile
	@ $(MAKE) -f .github/Makefile

.PHONY: clean
clean:
	@ rm -rf .github/bin

.PHONY: deploy
deploy:
	@ docker build -f .github/Dockerfile -t y2khub/minesweeper .

.PHONY: web
web: build
	@ cd .github/bin && python3 -m http.server 8000
