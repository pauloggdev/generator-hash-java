import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class HashGenerator {

    public static void main(String[] args) {
        String texto = "2024-09-25;2024-09-25T16:16:36;FR 2024/2;300.00;sCY6+9uMYGJTm8KL6SvMOjo+BbqkWgvAVqA7+BKMW9ZDQUEQXIAx9lOkIzsdkY5pInU/bu8zPaMt73mYqzQ+Mq985g33uvKfyIlT5QjQcGYgCw4wbL4bIhhU+doqbuTS37rPlN2/DKq3oK2zcenrGRqnC/2eaIVObwpjL1YO2aE="; // Insira o texto que você deseja hashear
        String chavePath = "primakey_pkcs8.pem"; // Use o caminho do novo arquivo PKCS#8

        try {
            // Ler a chave privada do arquivo
            PrivateKey chavePrivada = lerChavePrivadaDoArquivo(chavePath);
            
            // Gerar o hash SHA-1 do texto usando a chave privada
            String hashBase64 = gerarHashSHA1ComChavePrivada(texto, chavePrivada);
            System.out.println("Hash SHA-1 em Base64: " + hashBase64);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static PrivateKey lerChavePrivadaDoArquivo(String caminho) throws IOException, Exception {
        // Ler o arquivo da chave privada
        String key = new String(Files.readAllBytes(Paths.get(caminho)))
                          .replace("-----BEGIN PRIVATE KEY-----", "")
                          .replace("-----END PRIVATE KEY-----", "")
                          .replaceAll("\\s+", "");

        // Converter a string em um array de bytes
        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        
        // Criar uma chave privada a partir do especificador
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private static String gerarHashSHA1ComChavePrivada(String texto, PrivateKey chavePrivada) throws Exception {
        // Usar a classe Signature para gerar a assinatura
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(chavePrivada);
        signature.update(texto.getBytes());

        // Gerar a assinatura (que é o hash)
        byte[] hashBytes = signature.sign();

        // Codificar o hash em Base64
        return Base64.getEncoder().encodeToString(hashBytes);
    }
}
