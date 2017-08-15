#! /bin/bash

curl -X POST \
-H "Content-Type: application/json" \
-d '{"query": "{ pets { name } }"}' \
http://localhost:9977/graphql
