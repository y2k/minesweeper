build: clean
	@ mkdir bin
	@ echo '{"type":"module"}' > bin/package.json
	@ clj2js prelude > bin/prelude.js
	@ clj2js ../src/vendor/utils.clj > bin/utils.js
	@ clj2js ../src/main.clj > bin/main.js
	@ clj2js ../src/main.static.clj > bin/main.static.js
	@ node bin/main.static.js > bin/index.html

clean:
	@ rm -r bin || true

deploy:
	cd .. && docker build -f .github/Dockerfile -t y2khub/minesweeper .

.PHONY: build clean deploy
