# clj-isitup [![CircleCI](https://circleci.com/gh/anmonteiro/clj-isitup.svg?style=svg)](https://circleci.com/gh/anmonteiro/clj-isitup) [![Dependencies Status](http://jarkeeper.com/anmonteiro/isitup/status.svg)](http://jarkeeper.com/anmonteiro/isitup)

Latest version:

[![Clojars Project](http://clojars.org/clj-isitup/latest-version.svg)](http://clojars.org/clj-isitup)

clj-isitup is an [isitup.org](http://isitup.org) API client & command-line tool.

## Installation

```clojure
(defproject clj-isitup-example "1.2.3"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [clj-isitup "0.5.1"]])
```

## Library

### Guide

To get started, `:require` clj-isitup somewhere in your project.

```clojure
(ns app.core
  (:require [clj-isitup.core :as isup]))
```

The only public API call as of the current version is `run-status`. Here's an example of usage:

```clojure
(isup/run-status "google.com")
;; => {:domain "google.com", :port 80, :status_code 1, :response_ip "216.58.211.206", :response_code 302, :response_time 0.085}
```

The table below provides the mapping between the response's `:status_code` and its meaning.

| `status_code` |    Interpretation    |
| :-----------: | -------------------- |
|       1       | Website is alive     |
|       2       | Website appears down |
|       3       | Domain was not valid |


## CLI tool

### Usage

For now (to check [Google](http://google.com)):

    $ lein trampoline run -- google.com
    ✔ Up: google.com

For multiple sites:

    $ lein trampoline run -- google.com xyz.asd
    ✔ Up: google.com
    ✖ Down: xyz.asd

### CLI options

```shell
  Usage: isitup [-v | -h] domain(s)
  -h, --help     Show help
  -v, --version  Show isitup-cli's version
```


## License

Copyright © 2015 António Nuno Monteiro

Distributed under the Eclipse Public License version 1.0 (see [LICENSE](./LICENSE)).
