# Gerador de hash(AGT) para java

 Gerador de hash(AGT) feito na linguagem java

## Configuração 

A chave privada está no formato PKCS#1 converter para PKCS#8 usando uma ferramenta como OpenSSL. O comando para isso é:

```bash
openssl pkcs8 -topk8 -inform PEM -in private_key.pem -outform PEM -out primakey_pkcs8.pem -nocrypt
