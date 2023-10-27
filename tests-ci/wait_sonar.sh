#!/usr/bin/env bash

log() {
  echo "*** $(date +'%Y-%m-%d %H:%M:%S')" "$@"
}

log "Waiting SonarQube to start"

retryCount=1
maxRetry=30

docker logs sonar 2>&1 | grep "SonarQube is operational"
status=$?
while [ "$status" -ne 0 ] && [ "$retryCount" -le "$maxRetry" ]; do
  log "SonarQube not operational yet - sleep and retry (""$retryCount""/""$maxRetry"")"
  retryCount=$((retryCount+1))
  sleep 5
  docker logs sonar 2>&1 | grep "SonarQube is operational"
  status=$?
done

if [ "$status" -ne 0 ]; then
  log "SonarQube not operational after" "$retryCount" " retries."
  return 1
fi

log "SonarQube is operational"
