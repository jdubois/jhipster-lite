#!/bin/bash

echo "*** Waiting 5sec to be sure the Jar is here"
sleep 5

echo "*** List folder"
ls -al

echo "*** Removing other jar files..."
rm *-javadoc.jar
rm *-sources.jar

echo "*** Starting JHipster Lite..."
java \
  -jar *.jar \
  --logging.level.ROOT=OFF & > /dev/null
echo $! > .pid-jhlite

retryCount=1
maxRetry=30
httpUrl="http://localhost:7471/management/health"

rep=$(curl -v "$httpUrl")
status=$?
while [ "$status" -ne 0 ] && [ "$retryCount" -le "$maxRetry" ]; do
  echo "*** [$(date)] Application not reachable yet. Sleep and retry - retryCount =" $retryCount "/" $maxRetry
  retryCount=$((retryCount+1))
  sleep 5
  rep=$(curl -v "$httpUrl")
  status=$?
done

if [ "$status" -ne 0 ]; then
  echo "*** [$(date)] Not connected after" $retryCount " retries."
  return 1
fi
