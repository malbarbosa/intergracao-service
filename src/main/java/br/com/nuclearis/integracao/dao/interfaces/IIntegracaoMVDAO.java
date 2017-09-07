package br.com.nuclearis.integracao.dao.interfaces;

import java.util.Calendar;
import java.util.List;

import br.com.nuclearis.integracao.dto.AutorizacaoExameDTO;
import br.com.nuclearis.integracao.dto.ConvenioDTO;
import br.com.nuclearis.integracao.dto.PacienteDTO;
import br.com.nuclearis.integracao.dto.PlanoDTO;
import br.com.nuclearis.integracao.dto.VagaPacienteDTO;
import br.com.nuclearis.integracao.exceptions.DAOException;

public interface IIntegracaoMVDAO extends IIntegracaoDAO {

	List<ConvenioDTO> consultarConveniosPorExame(List<Long> codigosEtapa) throws DAOException;

	List<PlanoDTO> consultarPlanosPorConvenio(Long codigoConvenio, List<Long> codigosEtapa) throws DAOException;

	List<AutorizacaoExameDTO> consultarAutorizacaoPlano(Long idPlano, Long idConvenio, List<Long> codigosEtapa) throws DAOException;

	Long consultarCondigoIntegracaoDaVaga(Calendar dataHorarioAgendamento) throws DAOException;

	void inserirAgendamento(VagaPacienteDTO vagaPaciente, String tipoMovimetacao) throws DAOException;

	List<PacienteDTO> consultarPacientes(String nome) throws DAOException;

	@Override
	void inserirLaudo(Long cdAtendimento, Byte[] laudo) throws DAOException;

	Long consultarCdAtendimentoPacienteInternado(Long codigoPaciente) throws DAOException;

}
