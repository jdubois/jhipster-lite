#!/bin/bash

application=$1
if [[ $application == '' ]]; then
  echo "using jhlite by default"
  application='jhlite'
fi

sonar=$(curl -s 'http://localhost:9001/api/measures/component?component='"$application"'&metricKeys=bugs%2Ccoverage%2Cvulnerabilities%2Cduplicated_lines_density%2Ccode_smells%2Csecurity_hotspots');

measure ()
{
  echo "$sonar"|jq -r .component|jq -r .measures|jq '[.[]|select(.metric=="'$1'")][0]'|jq -r .value
}

vul=$(measure "vulnerabilities")
cov=$(measure "coverage")
bug=$(measure "bugs")
dup=$(measure "duplicated_lines_density")
csm=$(measure "code_smells")
sec=$(measure "security_hotspots")

echo "----- Local Sonar Analysis -----"
echo "  Coverage:          $cov"
echo "  Vulnerabilities:   $vul"
echo "  Bugs:              $bug"
echo "  Duplication:       $dup"
echo "  Code smells:       $csm"
echo "  Security Hotspots: $sec"
echo "--------------------------------"

fail ()
{
  echo
  echo 'List of all errors:'
  curl -s 'http://localhost:9001/api/issues/search?componentKeys='"$application"'&resolved=false' | jq '.issues[] | {file: "\(.component)#\(.line)", error: "[\(.rule)] \(.message)"}'
  exit 1
}

if [[ $vul != "0" ]]; then
  echo "Sonar Analysis failed -> Vulnerabilities"
  fail;
fi

if [[ $bug != "0" ]]; then
  echo "Sonar Analysis failed -> Bugs"
  fail;
fi

if [[ $dup != "0.0" ]]; then
  echo "Sonar Analysis failed -> Duplication"
  fail;
fi

if [[ $csm != "0" ]]; then
  echo "Sonar Analysis failed -> Code smells"
  fail;
fi

if [[ $sec != "0" ]]; then
  echo "Sonar Analysis failed -> Security Hotspots"
  fail;
fi

echo "Sonar Analysis is passed"
