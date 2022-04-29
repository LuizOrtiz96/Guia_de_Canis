package br.com.ortizluiz.guidecanis.model;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Erro {

	public HttpStatus statusCode;
	private String mensagem;
	private String exception;

	public Erro(HttpStatus status, String msg, String exc) {
		this.statusCode = status;
		this.mensagem = msg;
		this.exception = exc;
	}
}
