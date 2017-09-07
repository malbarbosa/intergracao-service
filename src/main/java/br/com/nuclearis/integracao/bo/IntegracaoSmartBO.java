package br.com.nuclearis.integracao.bo;
//package br.com.nuclearis.integracao.bo;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import br.com.nuclearis.core.dataaccess.dao.interfaces.IVagaPacienteDAO;
//import br.com.nuclearis.core.dataaccess.exception.DAOException;
//import br.com.nuclearis.core.dataaccess.exception.QueryParametroInvalidoException;
//import br.com.nuclearis.core.entity.worklist.VagaPaciente;
//import br.com.nuclearis.core.util.exception.ConversaoDataException;
//import br.com.nuclearis.integracao.bo.interfaces.AbstractIntegracaoSmartBO;
//import br.com.nuclearis.integracao.bo.interfaces.IIntegracaoBO;
//import br.com.nuclearis.integracao.dto.ConvenioDTO;
//import br.com.nuclearis.integracao.dto.VagaPacienteDTO;
//
//@Component("boIntegracaoSmart")
//@Scope("prototype")
//public class IntegracaoSmartBO extends AbstractIntegracaoSmartBO implements IGerenciadorIntegracaoBO {
//
//	private static Logger log = Logger.getLogger(IntegracaoSmartBO.class);
//
//	@Resource
//	private IVagaPacienteDAO vagaPacienteDAO;
//
//	@Override
//	protected void setDAO() {
//		this.setDAO(dao);
//	}
//
//	@Override
//	public List<VagaPacienteDTO> getListaAtendimentosDoDia(Collection<VagaPaciente> listaVagaPaciente, Long cdAtendimento) throws DAOException, QueryParametroInvalidoException {
//
//		Set<Long> listaCodigoIntegracao = new HashSet<Long>();
//		if (listaVagaPaciente != null) {
//			for (VagaPaciente vagaPaciente : listaVagaPaciente) {
//				listaCodigoIntegracao.add(vagaPaciente.getCdIntegracao());
//			}
//		}
//
//		List<VagaPacienteDTO> listaAtendimentosDoDia = dao.getListaAtendimentosDoDia(listaCodigoIntegracao);
//
//		log.info(" Quantidade de atendimentos do dia no MV: " + (listaAtendimentosDoDia == null || listaAtendimentosDoDia.isEmpty() ? 0 : listaAtendimentosDoDia.size()));
//
//		return listaAtendimentosDoDia;
//
//	}
//
//	@Override
//	public Map<Long, VagaPacienteDTO> getListaAgendamentos() throws DAOException, QueryParametroInvalidoException, ConversaoDataException {
//
//		return dao.getListaAgendamentos();
//
//	}
//
//	@Override
//	public VagaPacienteDTO getPacientePorId(Long idPacienteIntegracao) throws DAOException {
//		return dao.getPacientePorId(idPacienteIntegracao);
//	}
//
//	@Override
//	public ConvenioDTO getConvenioEPlano(String nomeConvenioIntegracao, String nomePlanoIntegracao) throws DAOException {
//		return dao.getConvenioEPlano(nomeConvenioIntegracao, nomePlanoIntegracao);
//	}
//
//	@Override
//	public ConvenioDTO getConvenioEPlanoPorId(Long idConvenioIntegracao, Long idPlanoIntegracao) throws DAOException {
//		return dao.getConvenioEPlanoPorId(idConvenioIntegracao, idPlanoIntegracao);
//	}
//
//}
