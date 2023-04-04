# bin/bash
# referenced from https://quarkus.io/guides/security-jwt#adding-a-public-key
openssl genrsa -out ./privateKey.pem 2048
openssl pkcs8 -topk8 -nocrypt -inform pem -in ./privateKey.pem -outform pem -out ./private.pem
openssl rsa -pubout -in ./private.pem -out ./public.pem
