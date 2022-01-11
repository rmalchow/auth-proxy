#!/bin/bash

set -e
mvn -Dmaven.repo.local=./m2 clean
mvn -Dmaven.repo.local=./m2 package


