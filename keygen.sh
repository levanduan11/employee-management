#!/bin/bash
# shellcheck disable=SC2164

cd api/src/main/resources

rm -R keys
mkdir keys
cd keys

openssl genrsa -out keypair.pem 2048
openssl rsa -in keypair.pem -pubout -out  public.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem

rm keypair.pem
echo "public.pem, private.pem created in api/src/main/resources/keys"