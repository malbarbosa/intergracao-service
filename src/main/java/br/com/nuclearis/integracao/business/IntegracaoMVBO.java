package br.com.nuclearis.integracao.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.nuclearis.integracao.bo.BOGenericoInt;
import br.com.nuclearis.integracao.business.interfaces.IIntegracaoMVBO;
import br.com.nuclearis.integracao.dao.interfaces.IIntegracaoMVDAO;
import br.com.nuclearis.integracao.dto.AutorizacaoExameDTO;
import br.com.nuclearis.integracao.dto.ConvenioDTO;
import br.com.nuclearis.integracao.dto.IntegracaoDTO;
import br.com.nuclearis.integracao.dto.PacienteDTO;
import br.com.nuclearis.integracao.dto.PlanoDTO;
import br.com.nuclearis.integracao.dto.VagaPacienteDTO;
import br.com.nuclearis.integracao.exceptions.DAOException;
import br.com.nuclearis.integracao.service.IntegracaoMVService;

@Component("boIntegracaoMV")
@Scope("prototype")
public class IntegracaoMVBO extends BOGenericoInt<IntegracaoDTO> implements IIntegracaoMVBO {

	private static Logger log = Logger.getLogger(IntegracaoMVService.class);

	@Resource(name = "bdIntegracaoMVDAO")
	private IIntegracaoMVDAO dao;

	//	@Resource
	//	private IConfiguracaoBO configuracaoBO;

	@Override
	@PostConstruct
	protected void setDAO() {
		this.setDAO(dao);
	}

	enum TipoAcaoEnum {
		AGENDAMENTO("A"), REMARCACAO("T"), CANCELAMENTO("C");

		private String value;

		private TipoAcaoEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	//	@Override
	//	@Transactional
	//	public List<PacienteDTO> consultarPacientes(String nome, int posicaoInicial, int posicaoFinal) throws DAOException, QueryParametroInvalidoException, ChaveConfiguracaoInexistenteException {
	//		//		Configuracao configuracao = configuracaoBO.getPorChave(Constantes.NOME_SCHEMA_E_VIEW_PACIENTES);
	//		return dao.consultarPacientes(nome, posicaoInicial, posicaoFinal);
	//	}

	@Override
	public List<PlanoDTO> consultarPlanosPorConvenio(Long codigoConvenio, List<Long> codigosEtapa) throws DAOException {
		return dao.consultarPlanosPorConvenio(codigoConvenio, codigosEtapa);
	}

	@Override
	public List<ConvenioDTO> consultarConveniosPorExame(List<Long> codigosEtapa) throws DAOException {
		return dao.consultarConveniosPorExame(codigosEtapa);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 600)
	public void realizarAgendamento(List<VagaPacienteDTO> vagasPaciente) throws DAOException {
		//insere os dados da vagaPaciente na tabela INTEGRA_ENTRADA_AGENDAMENTO
		dao.inserirAgendamento(getCdAtendimentoPacienteInternado(vagasPaciente.get(0)), TipoAcaoEnum.AGENDAMENTO.getValue());
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 600)
	public void cancelarAgendamento(List<VagaPacienteDTO> vagasPaciente) throws DAOException {
		dao.inserirAgendamento(getCdAtendimentoPacienteInternado(vagasPaciente.get(0)), TipoAcaoEnum.CANCELAMENTO.getValue());
	}

	//TODO verifica se o paciente a ser agendado é um paciente internado. Caso seja, retorna o codigo de atendimento do mesmo.
	private VagaPacienteDTO getCdAtendimentoPacienteInternado(VagaPacienteDTO vagaPaciente) throws DAOException {
		vagaPaciente.setCodigoAtendimento(dao.consultarCdAtendimentoPacienteInternado(vagaPaciente.getPaciente().getCdIntegracao()));
		return vagaPaciente;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 600)
	public void remarcarAgendamento(List<VagaPacienteDTO> vagasPaciente) throws DAOException {
		dao.inserirAgendamento(getCdAtendimentoPacienteInternado(vagasPaciente.get(0)), TipoAcaoEnum.REMARCACAO.getValue());

	}

	@Override
	public List<AutorizacaoExameDTO> consultarAutorizacaoPlano(Long idPlano, Long idConvenio, List<Long> codigosEtapa) throws DAOException {
		return dao.consultarAutorizacaoPlano(idPlano, idConvenio, codigosEtapa);
	}

	@Override
	public void inserirLaudo(VagaPacienteDTO vagaPaciente) {
		try {
			// TODO disponibilizar o laudo
			//			FileOutputStream fos = new FileOutputStream("laudo-" + vagaPaciente.getPaciente().getNome() + ".rtf");
			//			FileBufferedOutputStream fbus = new FileBufferedOutputStream(vagaPaciente.getLaudo().length());
			//			fbus.write(vagaPaciente.getLaudo().getBytes());
			//			fbus.close();
			//			ByteArrayInputStream bis = new ByteArrayInputStream(vagaPaciente.getLaudo().getBytes());
			//			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			VagaPacienteDTO agendamento = mapVagaPaciente.get(atendimentoMV.getPaciente().getId());
			if (agendamento != null && agendamento.getCodigoExame().equals(atendimentoMV.getCodigoExame()) && atendimentoMV.getCodigoAtendimento() != null && agendamento.getCodigoAtendimento() == null) {
				atendimentoMV.setId(agendamento.getId());
				pacientesRecepcao.add(atendimentoMV);
			}
		}
		return pacientesRecepcao;
	}

	@Override
	public Long consultarVaga(Calendar dataHorarioVaga) throws DAOException {
		return dao.consultarCondigoIntegracaoDaVaga(dataHorarioVaga);
	}
	//
	//	@Override
	//	public Long quantidadeTotalPacientes() throws DAOException {
	//		return dao.quantidadeTotalPacientes();
	//	}

	@Override
	public List<PacienteDTO> consultarPacientes(String nome) throws DAOException {
		return dao.consultarPacientes(nome);
	}

}
