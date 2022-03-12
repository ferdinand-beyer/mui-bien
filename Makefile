SHELL := bash
.SHELLFLAGS := -eu -o pipefail -c

MAKEFLAGS += --no-builtin-rules --warn-undefined-variables

.ONESHELL:
.DELETE_ON_ERROR:

SRC := $(shell find src -type f -name "*.clj[sc]")
TEST_SRC := $(shell find test -type f -name "*.clj[sc]")
BUILD_SRC := build.clj $(shell find build -type f -name "*.clj")

JAR := mui-bien.jar
TARGET_DIR := target
GEN_TARGET := $(TARGET_DIR)/src-gen/mui_bien/material/all.cljs
TEST_TARGET := $(TARGET_DIR)/test/node/run-tests.js

.PHONY: all
all: jar

.PHONY: build
build: jar

.PHONY: gen
gen: $(GEN_TARGET)

.PHONY: test
test: $(TEST_TARGET)
	node $(TEST_TARGET)

.PHONY: jar
jar: $(JAR)

.PHONY: clean
clean:
	-rm -rf $(TARGET_DIR)

.PHONY: install
install: jar
	clojure -T:build install

.PHONY: dev
dev: gen
	clojure -M:demo:test:shadow-cljs watch demo test

# TODO: This requires 'npm install' after shadow-cljs has created 'package.json'
$(GEN_TARGET): $(GEN_SRC)
	clojure -T:build generate

$(TEST_TARGET): $(SRC) $(TEST_SRC) $(GEN_TARGET)
	clojure -M:test:shadow-cljs compile node-test

$(JAR): $(SRC) $(GEN_TARGET)
	clojure -T:build jar
