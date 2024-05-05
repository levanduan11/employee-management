#!/bin/bash

DB_NAME="${1:-boot}"
DB_URL="jdbc:postgresql://localhost:5432/${DB_NAME}"
DB_USER="${2:-postgres}"
DB_PASSWORD="${3:-123}"
DB_SCHEMA="${4:-employee}"  
LOCATION="${5:-filesystem:../sql}"
echo $LOCATION
flyway  -url="${DB_URL}" -user="${DB_USER}" -password="${DB_PASSWORD}" -schemas="${DB_SCHEMA}" -locations="${LOCATION}" info
flyway -url="${DB_URL}" -user="${DB_USER}" -password="${DB_PASSWORD}" -schemas="${DB_SCHEMA}" -locations="${LOCATION}" migrate
