To generate a key for topic ACL
====

There are 4 environments:

- Dev - Latest and greatest dev changes.
- Integration- Used for integration level testing between Rally/Optum.  The code is expected to be fairly stable with the understanding that issues can still be found during integration testing.
- Bluesteel - Client level testing.  Code is expected to be the same version as in production but operating on non-prod datasets.
- Prod - Production use only

Only specify the CN, OU, O, ST, C
DO NOT SPECIFY EMAIL, the default kafka principal builder does not handle it natively.

Replace the appropriate values (topic-csr for example, to indicate the right environment)
```
openssl req -new -newkey rsa:2048 -nodes -keyout topic-csr.key -out topic-csr.pem
Generating a 2048 bit RSA private key

.........+++

......................................................................................................................................................+++

writing new private key to 'topic-csr.key'

-----

You are about to be asked to enter information that will be incorporated

into your certificate request.

What you are about to enter is what is called a Distinguished Name or a DN.

There are quite a few fields but you can leave some blank

For some fields there will be a default value,

If you enter '.', the field will be left blank.

-----

Country Name (2 letter code) [AU]:US

State or Province Name (full name) [Some-State]:Minnesota

Locality Name (eg, city) []:Minneapolis

Organization Name (eg, company) [Internet Widgits Pty Ltd]:Rallly

Organizational Unit Name (eg, section) []:Dataship

Common Name (e.g. server FQDN or YOUR name) []:topic-csr

Email Address []:LEAVE_BLANK



Please enter the following 'extra' attributes

to be sent with your certificate request

A challenge password []:
```

To send secure emails to Optum: https://wiki.audaxhealth.com/display/MNTK/Send+an+Encrypted+Email

Optum will sign and send back a CER (pkcs8) signed cert.

You will now have the following two files.
topic-csr.key
topic-csr.cer

```
openssl pkcs12 -export -in topic-csr.cer -inkey topic-csr.key -certfile topic-csr.cer -out topic-csr.p12
```
Set the key password (to be stored in illuminati as dataship.kafka.ssl.key.password)
```
keytool -importkeystore -srckeystore topic-csr.p12 -srcstoretype pkcs12 -destkeystore keystore.jks -deststoretype JKS
```
Set the keystore password. (to be stored in illuminati as dataship.kafka.ssl.keystore.password)
