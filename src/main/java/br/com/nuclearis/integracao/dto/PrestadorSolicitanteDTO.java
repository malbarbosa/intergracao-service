package br.com.nuclearis.integracao.dto;

import java.io.Serializable;

public class PrestadorSolicitanteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5086026862884000747L;

	private Long id;

	private String nome;

	private String numeroConselho;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumeroConselho() {
		return numeroConselho;
	}

	public void setNumeroConselho(String numeroConselho) {
		this.numeroConselho = numeroConselho;
	}

}
