build: clean
	@ clj2js prelude > bin/prelude.js
	@ clj2js ../src/vendor/utils.clj > bin/utils.js
	@ clj2js ../src/main.clj > bin/main.js
	@ clj2js ../src/main.static.clj > bin/main.static.js
	@ node bin/main.static.js > bin/index.html

clean:
	@ rm bin/*.js || true

deploy:
	cd .. && docker build --progress plain -f .github/Dockerfile .

.PHONY: build clean deploy
