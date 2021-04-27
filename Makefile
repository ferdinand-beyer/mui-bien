SHELL := bash
.SHELLFLAGS := -eu -o pipefail -c

MAKEFLAGS += --no-builtin-rules --warn-undefined-variables

.ONESHELL:
.DELETE_ON_ERROR:

BUILD_DIR := build
JAR := mui-bien.jar
GENERATED := $(BUILD_DIR)/src/mui_bien/core/all.cljs

.PHONY: all
all:

.PHONY: build
build: gen jar

.PHONY: gen
gen: $(GENERATED)

.PHONY: jar
jar: $(JAR)

.PHONY: clean
clean:
	-rm -rf $(BUILD_DIR) $(JAR)

.PHONY: install
install: jar
	clojure -X:install

$(GENERATED):
	clojure -X:gen

$(JAR): $(GENERATED)
	clojure -X:jar
