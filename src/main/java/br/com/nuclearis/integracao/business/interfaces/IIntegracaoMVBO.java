package br.com.nuclearis.integracao.business.interfaces;

import java.util.Calendar;
import java.util.List;

import br.com.nuclearis.integracao.dto.AutorizacaoExameDTO;
import br.com.nuclearis.integracao.dto.ConvenioDTO;
import br.com.nuclearis.integracao.dto.PacienteDTO;
import br.com.nuclearis.integracao.dto.PlanoDTO;
import br.com.nuclearis.integracao.dto.VagaPacienteDTO;
import br.com.nuclearis.integracao.exceptions.DAOException;

public interface IIntegracaoMVBO extends IIntegracaoBO {

	//	List<PacienteDTO> consultarPacientes(String nome) throws DAOException, QueryParametroInvalidoException, ChaveConfiguracaoInexistenteException;

	List<PlanoDTO> consultarPlanosPorConvenio(Long codigoConvenio, List<Long> codigosEtapa) throws DAOException;

	List<ConvenioDTO> consultarConveniosPorExame(List<Long> codigosEtapa) throws DAOException;

	void cancelarAgendamento(List<VagaPacienteDTO> vagasPaciente) throws DAOException;

	void remarcarAgendamento(List<VagaPacienteDTO> vagasPaciente) throws DAOException;

	List<AutorizacaoExameDTO> consultarAutorizacaoPlano(Long idPlano, Long idConvenio, List<Long> codigosEtapa) throws DAOException;

	Long consultarVaga(Calendar dataHorarioVaga) throws DAOException;

	//	Long quantidadeTotalPacientes() throws DAOException, QueryParametroInvalidoException;

	//	List<PacienteDTO> consultarPacientes(String nome, int posicaoInicial, int posicaoFinal) throws DAOException, QueryParametroInvalidoException, ChaveConfiguracaoInexistenteException;
	List<PacienteDTO> consultarPacientes(String nome) throws DAOException;

}
