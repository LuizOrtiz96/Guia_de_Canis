package br.com.ortizluiz.guidecanis.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class HashUtil {

	public static String hash(String palavra) {
		// "Tempero" do hash
		String salt = "B@By";
		// Adicionar o "Tempero" à palavra
		palavra = salt + palavra;
		// Gerando o hash
		String hash = Hashing.sha256().hashString(palavra, StandardCharsets.UTF_8).toString();
		// retorna o hash
		return hash;
	}

}
