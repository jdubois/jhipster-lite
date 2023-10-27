#!/usr/bin/env bash

echo "*** Stopping JHipster Lite in 5sec..."
sleep 5
kill $(cat .pid-jhlite)
