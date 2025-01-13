.PHONY: test
test: clean build

.PHONY: build
build:
	@ export OCAMLRUNPARAM=b && clj2js compile -target repl -src build.clj > .github/Makefile
	@ $(MAKE) -f .github/Makefile

.PHONY: clean
clean:
	@ rm -rf .github/bin

.PHONY: deploy
deploy:
	@ docker build -f .github/Dockerfile -t y2khub/minesweeper .

.PHONY: web
web:
	@ cd .github/bin && python3 -m http.server 8000
