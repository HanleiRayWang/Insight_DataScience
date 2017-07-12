#!/bin/bash

javac -cp java-json.jar *.java;

java -cp src:java-json.jar Detector ./log_input/batch_log_medium.json ./log_input/stream_log_medium.json ./log_output/flagged_purchases_medium.json" 3714

