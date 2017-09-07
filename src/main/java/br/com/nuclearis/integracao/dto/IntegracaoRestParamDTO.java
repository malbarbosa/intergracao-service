package br.com.nuclearis.integracao.dto;

import java.io.Serializable;
import java.util.List;

public class IntegracaoRestParamDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7067351635365586545L;

	private List<Long> idsEtapas;

	private Long idConvenio;

	private Long idPlano;

	private String dataHoraVaga;

	private String timezoneUsuario;

	private String nomePaciente;

	public String getNomePaciente() {
		return nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public List<Long> getIdsEtapas() {
		return idsEtapas;
	}

	public void setIdsEtapas(List<Long> idsEtapas) {
		this.idsEtapas = idsEtapas;
	}

	public Long getIdConvenio() {
		return idConvenio;
	}

	public void setIdConvenio(Long idConvenio) {
		this.idConvenio = idConvenio;
	}

	public Long getIdPlano() {
		return idPlano;
	}

	public void setIdPlano(Long idPlano) {
		this.idPlano = idPlano;
	}

	public String getTimezoneUsuario() {
		return timezoneUsuario;
	}

	public void setTimezoneUsuario(String timezoneUsuario) {
		this.timezoneUsuario = timezoneUsuario;
	}

	public String getDataHoraVaga() {
		return dataHoraVaga;
	}

	public void setDataHoraVaga(String dataHoraVaga) {
		this.dataHoraVaga = dataHoraVaga;
	}

}
