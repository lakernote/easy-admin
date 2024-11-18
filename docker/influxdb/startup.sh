#!/bin/bash

influxd &

# let influx get started
sleep 0.5
/usr/bin/influx -execute 'CREATE DATABASE metrics WITH DURATION 6h REPLICATION 1 NAME "default"'

# now wait (on influxd) so that we don't exit the container
wait