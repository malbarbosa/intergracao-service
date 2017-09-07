package br.com.nuclearis.integracao.dto;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Embeddable
@JsonInclude(Include.NON_NULL)
public class PlanoDTO {

	private Long idPlano;
	private String nomePlano;
	private String statusPlano;
	private ConvenioDTO convenio;
	private String tipoPlano;

	public String getNomePlano() {
		return nomePlano;
	}

	public void setNomePlano(String nomePlano) {
		this.nomePlano = nomePlano;
	}

	public String getStatusPlano() {
		return statusPlano;
	}

	public void setStatusPlano(String statusPlano) {
		this.statusPlano = statusPlano;
	}

	public Long getIdPlano() {
		return idPlano;
	}

	public void setIdPlano(Long idPlano) {
		this.idPlano = idPlano;
	}

	public ConvenioDTO getConvenio() {
		return convenio;
	}

	public void setConvenio(ConvenioDTO convenio) {
		this.convenio = convenio;
	}

	public String getTipoPlano() {
		return tipoPlano;
	}

	public void setTipoPlano(String tipoPlano) {
		this.tipoPlano = tipoPlano;
	}

}
