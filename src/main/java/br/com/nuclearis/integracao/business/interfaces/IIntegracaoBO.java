package br.com.nuclearis.integracao.business.interfaces;

import java.util.List;

import br.com.nuclearis.integracao.dto.VagaPacienteDTO;
import br.com.nuclearis.integracao.exceptions.DAOException;

public interface IIntegracaoBO {

	void inserirLaudo(VagaPacienteDTO vagaPaciente);

	void realizarAgendamento(List<VagaPacienteDTO> vagasPaciente) throws DAOException;

	List<VagaPacienteDTO> consultarAtendimentos(List<VagaPacienteDTO> atendimentos, List<VagaPacienteDTO> agendamentosDoDia) throws DAOException;
}
