package br.com.ortizluiz.guidecanis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
public class Canil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nome;
	@Column(columnDefinition = "TEXT")
	private String descricao;
	private String cep;
	private String rua;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String atracoes;
	private String formasPagamento;
	private String horario;
	private String site;
	private String telefone;
	private boolean delivery;
	private boolean acessibilidade;
	private boolean estacionamento;
	@ManyToOne
	private TipoCanil tipo;
	@Column(columnDefinition = "TEXT")
	private String fotos;
	private int preco;
}
