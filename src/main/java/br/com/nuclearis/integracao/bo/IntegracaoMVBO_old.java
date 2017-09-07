package br.com.nuclearis.integracao.bo;
//package br.com.nuclearis.integracao.bo;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//
//import br.com.nuclearis.core.dataaccess.dao.interfaces.IVagaPacienteDAO;
//import br.com.nuclearis.core.dataaccess.exception.DAOException;
//import br.com.nuclearis.core.dataaccess.exception.QueryParametroInvalidoException;
//import br.com.nuclearis.core.entity.worklist.VagaPaciente;
//import br.com.nuclearis.core.util.exception.ConversaoDataException;
//import br.com.nuclearis.integracao.dao.interfaces.IIntegracaoMVDAO_old;
//import br.com.nuclearis.integracao.bo.interfaces.IIntegracaoBO;
//import br.com.nuclearis.integracao.dto.VagaPacienteDTO;
//
////@Component("boIntegracaoMV")
////@Scope("prototype")
//public class IntegracaoMVBO_old extends BOGenericoInt<VagaPacienteDTO> implements IIntegracaoBO {
//
//	private static Logger log = Logger.getLogger(IntegracaoMVBO_old.class);
//
//	@Resource(name = "daoMV")
//	private IIntegracaoMVDAO_old dao;
//
//	@Resource
//	private IVagaPacienteDAO vagaPacienteDAO;
//
//	@Override
//	@PostConstruct
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
//		List<VagaPacienteDTO> listaAtendimentosDoDia = dao.getListaAtendimentosDoDia(listaCodigoIntegracao, cdAtendimento);
//
//		log.info(" Quantidade de atendimentos do dia no MV: " + listaAtendimentosDoDia.size());
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
//}
