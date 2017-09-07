package br.com.nuclearis.integracao.dto;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AutorizacaoExameDTO {

	private Long codigoExame;
	private String nomeExame;
	private String tipoAtendimento;
	private ConvenioDTO convenio;
	private String siglaTipoProibicao;
	private String descTipoProibicao;
	private Calendar vigencia;

	public Long getCodigoExame() {
		return codigoExame;
	}

	public void setCodigoExame(Long codigoExame) {
		this.codigoExame = codigoExame;
	}

	public String getNomeExame() {
		return nomeExame;
	}

	public void setNomeExame(String nomeExame) {
		this.nomeExame = nomeExame;
	}

	public String getTipoAtendimento() {
		return tipoAtendimento;
	}

	public void setTipoAtendimento(String tipoAtendimento) {
		this.tipoAtendimento = tipoAtendimento;
	}

	public ConvenioDTO getConvenio() {
		return convenio;
	}

	public void setConvenio(ConvenioDTO convenio) {
		this.convenio = convenio;
	}

	//	public PlanoDTO getPlano() {
	//		return plano;
	//	}
	//
	//	public void setPlano(PlanoDTO plano) {
	//		this.plano = plano;
	//	}

	public String getSiglaTipoProibicao() {
		return siglaTipoProibicao;
	}

	public void setSiglaTipoProibicao(String siglaTipoProibicao) {
		this.siglaTipoProibicao = siglaTipoProibicao;
	}

	public String getDescTipoProibicao() {
		return descTipoProibicao;
	}

	public void setDescTipoProibicao(String descTipoProibicao) {
		this.descTipoProibicao = descTipoProibicao;
	}

	public Calendar getVigencia() {
		return vigencia;
	}

	public void setVigencia(Calendar vigencia) {
		this.vigencia = vigencia;
	}

}
