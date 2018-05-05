Generating Keys for encrypting/decrypting Kafka messages
================================

There are 4 environments:

- Dev - Latest and greatest dev changes.
- Integration- Used for integration level testing between Rally/Optum.  The code is expected to be fairly stable with the understanding that issues can still be found during integration testing.
- Bluesteel - Client level testing.  Code is expected to be the same version as in production but operating on non-prod datasets.
- Prod - Production use only

Replace the keyname with one appropriate for the environment and topic

Generate RSA public/private keypair in PKCS1 format. Has to be 4096:

```
    openssl genrsa -out rsa_keypair.pem 4096
```

Convert private key to PKCS8 format (for dataship-reader to use to decrypt messages):

```
    openssl pkcs8 -topk8 -in rsa_keypair.pem -inform pem -out key-decryption-key.pem -outform pem -nocrypt
```

Generate RSA public Key (to be sent to Optum so they can encrypt the messages)

```    
    openssl rsa -in rsa_keypair.pem -outform PEM -pubout -out key-encryption-key.pem
```

To send secure emails to Optum: https://wiki.audaxhealth.com/display/MNTK/Send+an+Encrypted+Email
