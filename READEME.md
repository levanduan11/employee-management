# private key
openssl genrsa -out keypair.pem 2048
# public key
openssl rsa -in keypair.pem -pubout -out  public.pem
# format key
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
