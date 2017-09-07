package br.com.nuclearis.integracao.business.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.nuclearis.integracao.bo.BOGenericoInt;
import br.com.nuclearis.integracao.dao.interfaces.IIntegracaoDAO;
import br.com.nuclearis.integracao.dto.IntegracaoDTO;
import br.com.nuclearis.integracao.dto.VagaPacienteDTO;
import br.com.nuclearis.integracao.exceptions.DAOException;

@Component("boIntegracaoSMART")
@Scope("prototype")
public class IntegracaoSMARTBO extends BOGenericoInt<IntegracaoDTO> implements IIntegracaoBO {

	@Resource(name = "bdIntegracaoMVDAO")
	private IIntegracaoDAO dao;

	@Override
	public void inserirLaudo(VagaPacienteDTO vagaPaciente) {
		//TODO inserir o laudo 
		//dao.inserirLaudo(cdAtendimento, laudo);

	}

	@Override
	public void realizarAgendamento(List<VagaPacienteDTO> vagasPaciente) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<VagaPacienteDTO> consultarAtendimentos(List<VagaPacienteDTO> atendimentos, List<VagaPacienteDTO> agendamentosDoDia) throws DAOException {
		//Consulta os atendimentos do MV
		List<VagaPacienteDTO> pacientesRecepcao = new ArrayList<VagaPacienteDTO>();
		Map<Long, VagaPacienteDTO> mapVagaPaciente = new HashMap<Long, VagaPacienteDTO>();
		for (VagaPacienteDTO vagaPacienteDTO : agendamentosDoDia) {
			mapVagaPaciente.put(vagaPacienteDTO.getPaciente().getCdIntegracao(), vagaPacienteDTO);
		}

		for (VagaPacienteDTO atendimentoMV : atendimentos) {
			VagaPacienteDTO agendamento = mapVagaPaciente.get(atendimentoMV.getPaciente().getCdIntegracao());
			if (agendamento != null && agendamento.getCodigoExame().equals(atendimentoMV.getCodigoExame()) && atendimentoMV.getCodigoAtendimento() != null && agendamento.getCodigoAtendimento() == null) {
				pacientesRecepcao.add(atendimentoMV);
			}
		}
		return pacientesRecepcao;
	}

	@Override
	@PostConstruct
	protected void setDAO() {
		this.setDAO(dao);
	}
}
