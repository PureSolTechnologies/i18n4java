#!/bin/sh

export DEPLOY_DIR=/opt/qsys/lib/

cp -r bin/* $DEPLOY_DIR
find $DEPLOY_DIR/* -type d -exec chmod 777 {} \;
find $DEPLOY_DIR/* -type f -exec chmod 666 {} \;
