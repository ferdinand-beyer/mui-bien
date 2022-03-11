SHELL := bash
.SHELLFLAGS := -eu -o pipefail -c

MAKEFLAGS += --no-builtin-rules --warn-undefined-variables

.ONESHELL:
.DELETE_ON_ERROR:

SRC := $(shell find src -type f -name "*.clj[sc]")
TEST_SRC := $(shell find test -type f -name "*.clj[sc]")
GEN_SRC := $(shell find gen -type f -name "*.clj")

JAR := mui-bien.jar
BUILD_DIR := build
GEN_TARGET := $(BUILD_DIR)/src/mui_bien/core/all.cljs
TEST_TARGET := $(BUILD_DIR)/test/node/run-tests.js

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
	-rm -rf $(BUILD_DIR) $(JAR)

.PHONY: install
install: jar
	clojure -X:install

.PHONY: dev
dev: #gen
	clojure -M:dev:test:shadow-cljs watch devcards test

.PHONY: release
release:
ifdef VERSION
	clojure -X:jar :version '"$(VERSION)"'
else
	$(error Required variable VERSION is not set for target `$@')
endif

$(GEN_TARGET): mui.edn $(GEN_SRC)
	clojure -X:gen

$(TEST_TARGET): $(SRC) $(TEST_SRC) $(GEN_TARGET)
	clojure -M:test:shadow-cljs compile node-test

$(JAR): $(SRC) $(GEN_TARGET)
	clojure -X:jar
