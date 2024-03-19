package com.example.tpcEncryptPkcs7;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

@SpringBootApplication
public class TpcEncryptPkcs7Application  implements ApplicationRunner {

	private static PrivateKey privKey;
	private static PublicKey pubKey;

	public static void main(String[] args) {
		SpringApplication.run(TpcEncryptPkcs7Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// Coloca aquí el código que deseas ejecutar al iniciar la aplicación
		System.out.println("La aplicación se ha iniciado. Ejecutando código inicial...");

		// código
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		KeyPair kp = kpg.generateKeyPair();
		privKey = kp.getPrivate();
		pubKey = kp.getPublic();

		System.out.println("private key (hexadecimal): "+ Hex.toHexString(privKey.getEncoded()));
		System.out.println("public key (hexadecimal): "+Hex.toHexString(pubKey.getEncoded()));

		// Configuración de las llaves
		TpcEncryptPkcs7 encryptor = new TpcEncryptPkcs7();
		encryptor.setPrivateKey(privKey);
		encryptor.setPublicKey(pubKey);

		String json = "{\"fpan\":\"987654321123456789\",\"issuerCardRefId\":\"abc1245784219\",\"exp\":\"1223\",\"cardholderName\":\"John\","
				+ "\"postalAddress\":{\"line1\":\"address1\",\"line2\":\"address2\",\"city\":\"City1\",\"postalCode\":\"12345\",\"state\":\"state1\",\"country\":\"Country1\"}}";

		// Encriptar datos
		byte[] encryptedData = encryptor.encrypt(json);

		// Desencriptar datos
		byte[] decryptedData = encryptor.decrypt(encryptedData);

		System.out.println("encriptado: " + Hex.toHexString(encryptedData));
		System.out.println("desencriptado: " + new String(decryptedData, "UTF-8"));

		ObjectMapper objectMapper = new ObjectMapper();
		CardInfoDTO cardInfoDTO = objectMapper.readValue(json, CardInfoDTO.class);

		String cardInfoJson = objectMapper.writeValueAsString(cardInfoDTO);
		System.out.println("CardInfoDTO en formato JSON:");
		System.out.println(cardInfoJson);

		// Código que se ejecuta antes de que la aplicación termine
		System.out.println("Código ejecutado. La aplicación está lista para ser usada.");

		// Finaliza la aplicación
		System.exit(0);
	}
}
