#!/bin/bash


#compile the java programs
javac -cp java-json.jar *.java;

java -cp java-json.jar:. Detector "batch_log_medium.json" "stream_log_medium.json" "flagged_purchases_medium.json" 3714

echo "All purchases scanned. Anomalous purchases flagged.";
