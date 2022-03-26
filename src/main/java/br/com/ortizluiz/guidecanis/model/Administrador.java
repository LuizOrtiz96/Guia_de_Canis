package br.com.ortizluiz.guidecanis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.com.ortizluiz.guidecanis.util.HashUtil;
import lombok.Data;

// Cria os Getters e Setters
@Data
// Mapeia a entidade para o JPA
@Entity
public class Administrador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotEmpty
	private String nome;
	// Define a coluna email com um índice único
	@Column(unique = true)
	@Email
	private String email;
	@NotEmpty
	private String senha;

// Método set que aplica o hash na senha
	public void setSenha(String senha) {

		this.senha = HashUtil.hash(senha);
	}
}
