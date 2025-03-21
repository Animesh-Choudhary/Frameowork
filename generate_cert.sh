#!/bin/bash

# Define certs directory inside your framework
CERTS_DIR="certs"
DATA_FILE="$CERTS_DIR/cert_data.csv"

# Ensure the directory exists
mkdir -p "$CERTS_DIR"

# Generate a valid certificate (365 days)
VALID_CERT="$CERTS_DIR/cert.pem"
openssl req -x509 -newkey rsa:2048 -keyout "$CERTS_DIR/key.pem" -out "$VALID_CERT" -days 365 -nodes \
  -subj "/C=US/ST=California/L=San Francisco/O=MyCompany/OU=QA/CN=mydomain.com"

# Generate an expired certificate (1 day)
EXPIRED_CERT="$CERTS_DIR/expired_cert.pem"
openssl req -x509 -newkey rsa:2048 -keyout "$CERTS_DIR/expired_key.pem" -out "$EXPIRED_CERT" -days 1 -nodes \
  -subj "/C=US/ST=California/L=San Francisco/O=MyCompany/OU=QA/CN=expired.mydomain.com"

# Extract details and store them in cert_data.csv inside certs/
echo "cert_file,cn,issuer,validity" > "$DATA_FILE"

for CERT in "$VALID_CERT" "$EXPIRED_CERT"; do
  CN=$(openssl x509 -in "$CERT" -noout -subject | awk -F'CN=' '{print $2}')
  ISSUER=$(openssl x509 -in "$CERT" -noout -issuer | awk -F'O=' '{print $2}')
  VALIDITY=$(openssl x509 -in "$CERT" -noout -enddate | cut -d= -f2)

  echo "$CERT,$CN,$ISSUER,$VALIDITY" >> "$DATA_FILE"
done

echo "✅ Certificates and metadata saved in $DATA_FILE"
