#!/bin/bash

cd "$(dirname "$0")/.."
typeset -r BASE="$PWD"
typeset -r SCRIPT_DIR="$BASE/src/main/resources/com/potetm"
typeset -r SCRIPT_FILE="fireworks.js"

cd "$SCRIPT_DIR"
jjs -fx -scripting "$SCRIPT_FILE"
