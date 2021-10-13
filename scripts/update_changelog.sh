#!/bin/bash

# Update "Unreleased" CHANGELOG notes on release

VERSION=`cat ./lib/build/resources/main/version.properties | sed -n 2p | cut -d '=' -f 2`
sed -i '' 's/## Unreleased/## Unreleased\
\
## '"${VERSION}/" ./CHANGELOG.md
