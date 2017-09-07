package br.com.nuclearis.integracao.dto;

import javax.persistence.Embedded;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ConvenioDTO {

	private Long idConvenio;
	private String nomeConvenio;
	private String statusConvenio;
	@Embedded
	private PlanoDTO plano = new PlanoDTO();

	public String getNomeConvenio() {
		return nomeConvenio;
	}

	public void setNomeConvenio(String nomeConvenio) {
		this.nomeConvenio = nomeConvenio;
	}

	public String getStatusConvenio() {
		return statusConvenio;
	}

	public void setStatusConvenio(String statusConvenio) {
		this.statusConvenio = statusConvenio;
	}

	public PlanoDTO getPlano() {
		return plano;
	}

	public void setPlano(PlanoDTO plano) {
		this.plano = plano;
	}

	public Long getIdConvenio() {
		return idConvenio;
	}

	public void setIdConvenio(Long idConvenio) {
		this.idConvenio = idConvenio;
	}

}
