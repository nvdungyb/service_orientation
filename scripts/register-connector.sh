#!/bin/bash
echo "Waiting for Kafka Connect to be ready..."
until curl -s http://localhost:8083 >/dev/null; do
  echo "Kafka Connect not ready, waiting..."
  sleep 2
done
echo "Kafka Connect is ready!"

echo "Registering mysql-connector..."
response=$(curl -s -X POST -H "Content-Type: application/json" \
  --data @/scripts/mysql-connector.json \
  http://localhost:8083/connectors)
echo "Response: $response"

if echo "$response" | grep -q "error_code"; then
  echo "Failed to register connector, but continuing..."
else
  echo "Connector registered successfully!"
fi