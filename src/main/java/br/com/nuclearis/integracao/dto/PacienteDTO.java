package br.com.nuclearis.integracao.dto;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Embedded;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PacienteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1843203408537317796L;
	private Long id;
	private String nome;
	private String sexo;
	private Calendar dataNascimento;
	private String email;
	private String numeroCpf;
	private String nomeMae;
	private String telefone;
	private String numeroRg;
	private String emissorRg;
	private String ufRg;
	private String estadoCivil;
	private String numeroCNS;
	private Long cdIntegracao;
	private Calendar validadeCNS;
	@Embedded
	private EnderecoDTO endereco = new EnderecoDTO();

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumeroCpf() {
		return numeroCpf;
	}

	public void setNumeroCpf(String numeroCpf) {
		this.numeroCpf = numeroCpf;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNumeroRg() {
		return numeroRg;
	}

	public void setNumeroRg(String numeroRg) {
		this.numeroRg = numeroRg;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public EnderecoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmissorRg() {
		return emissorRg;
	}

	public void setEmissorRg(String emissorRg) {
		this.emissorRg = emissorRg;
	}

	public String getUfRg() {
		return ufRg;
	}

	public void setUfRg(String ufRg) {
		this.ufRg = ufRg;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNumeroCNS() {
		return numeroCNS;
	}

	public void setNumeroCNS(String numeroCNS) {
		this.numeroCNS = numeroCNS;
	}

	public void setEstadoCivil(String estadoCivil) {

		if (estadoCivil != null) {
			char sigla = estadoCivil.charAt(0);
			switch (sigla) {
				case 'C':
					estadoCivil = "Casado";
					break;
				case 'D':
					estadoCivil = "Divorciado";
					break;
				case 'S':
					estadoCivil = "Solteiro";
					break;
				case 'V':
					estadoCivil = "Viúvo";
					break;
				default:
					estadoCivil = null;
					break;
			}
		}
		this.estadoCivil = estadoCivil;
	}

	public Long getCdIntegracao() {
		return cdIntegracao;
	}

	public void setCdIntegracao(Long cdIntegracao) {
		this.cdIntegracao = cdIntegracao;
	}

	public Calendar getValidadeCNS() {
		return validadeCNS;
	}

	public void setValidadeCNS(Calendar validadeCNS) {
		this.validadeCNS = validadeCNS;
	}

}
