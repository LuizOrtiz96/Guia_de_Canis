package br.com.ortizluiz.guidecanis.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class FirebaseUtil {

	// Variável para guardar as credenciais de acesso
	private Credentials credenciais;
	// Variável para acessar emanipular o storage
	private Storage storage;
	// Constante para o nome do Bucket
	private final String BUCKET_NAME = "gs://canisguide-luiz.appspot.com/";
	// Constante para o prefixo da URL
	private final String PREFIX = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET_NAME + "/o/";
	// Constante do sufixo da URL
	private final String SUFFIX = "?alt=media";
	// constante para a URL
	private final String DOWNLOAD_URL = PREFIX + "%s" + SUFFIX;

	public FirebaseUtil() {
		// Acessar o arquivo Json com a chave privada
		Resource resource = new ClassPathResource("chavefirebase.json");

		try {
			// Gera uma credencial no firebase através da chave do arquivo
			credenciais = GoogleCredentials.fromStream(resource.getInputStream());
			// Cria o Storage para manipular os dados no Firebase
			storage = StorageOptions.newBuilder().setCredentials(credenciais).build().getService();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());

		}
	}

	// Método para extrair a extensão do arquivo
	private String getExtensao(String nomeArquivo) {
		// Extrai o trecho do arquivo onde está a extensão
		return nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
	}

	// Método que faz o upload
	public String upload(MultipartFile arquivo) {
		// Gera uma String aleatoria para o arquivo
		String nomeArquivo = UUID.randomUUID().toString() + getExtensao(arquivo.getOriginalFilename());
		return "";

	}
}
