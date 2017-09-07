package br.com.nuclearis.integracao.dto;

import java.util.Calendar;

public class VagaPacienteDTO implements IntegracaoDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Calendar dtHoraExame;

	private Calendar dataChegadaRecepcao;

	private PacienteDTO paciente = new PacienteDTO();

	private ConvenioDTO convenio = new ConvenioDTO();

	private Long codigoAtendimento;

	private Float alturaPaciente;

	private Float pesoPaciente;

	private Long codigoExame;

	private String descricaoExame;

	private String observacao;

	private String situacao;

	private int codigoSetorSolicitante;

	private PrestadorSolicitanteDTO prestadorSolicitantePOJO = new PrestadorSolicitanteDTO();

	private String usuarioResponsavel;

	private Long codigoAgendamento;

	private String laudo;

	//Representa o código único do registro de integracao
	private Long codSolicitacaoExame;

	public Long getCodSolicitacaoExame() {
		return codSolicitacaoExame;
	}

	public void setCodSolicitacaoExame(Long codigoItemPedido) {
		this.codSolicitacaoExame = codigoItemPedido;
	}

	public Long getCodigoExame() {
		return codigoExame;
	}

	public void setCodigoExame(Long codigoExame) {
		this.codigoExame = codigoExame;
	}

	public String getDescricaoExame() {
		return descricaoExame;
	}

	public void setDescricaoExame(String descricaoExame) {
		this.descricaoExame = descricaoExame;
	}

	public ConvenioDTO getConvenio() {
		return convenio;
	}

	public void setConvenio(ConvenioDTO convenio) {
		this.convenio = convenio;
	}

	public PacienteDTO getPaciente() {
		return paciente;
	}

	public void setPaciente(PacienteDTO paciente) {
		this.paciente = paciente;
	}

	public Long getCodigoAtendimento() {
		return codigoAtendimento;
	}

	public void setCodigoAtendimento(Long codigoAtendimento) {
		this.codigoAtendimento = codigoAtendimento;
	}

	public Float getAlturaPaciente() {
		return alturaPaciente;
	}

	public void setAlturaPaciente(Float alturaPaciente) {
		this.alturaPaciente = alturaPaciente;
	}

	public Float getPesoPaciente() {
		return pesoPaciente;
	}

	public void setPesoPaciente(Float pesoPaciente) {
		this.pesoPaciente = pesoPaciente;
	}

	public PrestadorSolicitanteDTO getPrestadorSolicitantePOJO() {
		return prestadorSolicitantePOJO;
	}

	public void setPrestadorSolicitantePOJO(PrestadorSolicitanteDTO prestadorSolicitantePOJO) {
		this.prestadorSolicitantePOJO = prestadorSolicitantePOJO;
	}

	public String getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	public void setUsuarioResponsavel(String usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	public Calendar getDtHoraExame() {
		return dtHoraExame;
	}

	public void setDtHoraExame(Calendar dtHoraPedido) {
		this.dtHoraExame = dtHoraPedido;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Calendar getDataChegadaRecepcao() {
		return dataChegadaRecepcao;
	}

	public void setDataChegadaRecepcao(Calendar dataChegadaRecepcao) {
		this.dataChegadaRecepcao = dataChegadaRecepcao;
	}

	public int getCodigoSetorSolicitante() {
		return codigoSetorSolicitante;
	}

	public void setCodigoSetorSolicitante(int codigoSetorSolicitante) {
		this.codigoSetorSolicitante = codigoSetorSolicitante;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Long getCodigoAgendamento() {
		return codigoAgendamento;
	}

	public void setCodigoAgendamento(Long codigoPedido) {
		this.codigoAgendamento = codigoPedido;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLaudo() {
		return laudo;
	}

	public void setLaudo(String laudo) {
		this.laudo = laudo;
	}

}
