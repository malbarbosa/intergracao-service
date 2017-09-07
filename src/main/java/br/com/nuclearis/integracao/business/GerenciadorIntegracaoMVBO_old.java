//package br.com.nuclearis.integracao.business;
//
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import javax.annotation.Resource;
//import javax.validation.ValidationException;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.transaction.annotation.Isolation;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import br.com.nuclearis.core.business.exception.CampoInvalidoException;
//import br.com.nuclearis.core.business.exception.DataNascimentoInvalidaException;
//import br.com.nuclearis.core.business.exception.DataObitoInvalidaException;
//import br.com.nuclearis.core.business.exception.ExameJaCanceladoException;
//import br.com.nuclearis.core.business.exception.ExclusaoVagaPacienteInvalidaException;
//import br.com.nuclearis.core.business.exception.FalhaNoSistemaException;
//import br.com.nuclearis.core.business.exception.HorarioVagaInvalidoException;
//import br.com.nuclearis.core.business.exception.IntegracaoException;
//import br.com.nuclearis.core.business.exception.MarcacaoComMenos30DiasException;
//import br.com.nuclearis.core.business.exception.MaxVagaDataExcedidoException;
//import br.com.nuclearis.core.business.exception.SalvarEventoException;
//import br.com.nuclearis.core.business.exception.SubEtapaExameJaTerminadaException;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.ITipoLogAlteracaoBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.agenda.IControleVagaPacienteBO;
//import br.com.nuclearis.core.dataaccess.exception.AutorizacaoExameDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.AutorizacaoStatusInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.ChaveConfiguracaoInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.DAOException;
//import br.com.nuclearis.core.dataaccess.exception.ExameInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.ExecucaoExameInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.MaquinaInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.MotivoInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.NomeTipoPlanoDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.NotaInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.NotificacaoInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.PacienteCodigoDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.PacienteCpfDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.PacienteInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.PacientePlanoDuplicadaException;
//import br.com.nuclearis.core.dataaccess.exception.PlanoInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.PrestadorSolicitanteInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.QueryParametroInvalidoException;
//import br.com.nuclearis.core.dataaccess.exception.RemarcacaoVagaPacienteDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarAutorizacaoExameException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarMedicoException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarNotaException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarNotificacaoException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarPacienteException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarPacientePlanoException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarPlanoException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarRemarcacaoVagaPacienteException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarVagaExameException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarVagaException;
//import br.com.nuclearis.core.dataaccess.exception.SubEtapaInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.TipoAutorizacaoInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.TipoLogAlteracaoInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.TipoPlanoInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.UsuarioInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.VagaInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.VagaPacienteDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.VagaPacienteInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.agenda.ControleVagaPacienteInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.agenda.EtapaInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.agenda.ProtocoloEtapaInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.agenda.ProtocoloInexistenteException;
//import br.com.nuclearis.core.emailservice.exception.EnvioEmailException;
//import br.com.nuclearis.core.entity.Operadora;
//import br.com.nuclearis.core.entity.Paciente;
//import br.com.nuclearis.core.entity.Plano;
//import br.com.nuclearis.core.entity.PrestadorSolicitante;
//import br.com.nuclearis.core.entity.TipoPlano;
//import br.com.nuclearis.core.entity.agenda.ControleVagaPaciente;
//import br.com.nuclearis.core.entity.agenda.ProtocoloEtapa;
//import br.com.nuclearis.core.entity.endereco.Estado;
//import br.com.nuclearis.core.entity.usuario.Usuario;
//import br.com.nuclearis.core.entity.worklist.ExameExecucao;
//import br.com.nuclearis.core.entity.worklist.Motivo;
//import br.com.nuclearis.core.entity.worklist.Motivo.TipoMotivoEnum;
//import br.com.nuclearis.core.entity.worklist.VagaPaciente;
//import br.com.nuclearis.core.util.DataUtil;
//import br.com.nuclearis.core.util.exception.ConversaoDataException;
//import br.com.nuclearis.core.util.exception.FormatoDataInvalidoException;
//import br.com.nuclearis.integracao.business.interfaces.IGerenciadorIntegracaoBO_old;
//import br.com.nuclearis.integracao.bo.interfaces.IIntegracaoBO;
//import br.com.nuclearis.integracao.dto.ConvenioDTO;
//import br.com.nuclearis.integracao.dto.VagaPacienteDTO;
//
////@Component("boGerenciadorIntegracaoMV")
//public class GerenciadorIntegracaoMVBO_old extends AbstractAtendimentoIntegracaoBO implements IGerenciadorIntegracaoBO_old {
//
//	private static Logger log = Logger.getLogger(GerenciadorIntegracaoMVBO_old.class);
//
//	@Resource(name = "boIntegracaoMV")
//	private IGerenciadorIntegracaoBO mvBO;
//
//	@Resource
//	private IControleVagaPacienteBO controleVagaPacienteBO;
//
//	@Resource
//	private ITipoLogAlteracaoBO boTipoLogAlteracao;
//
//	private static final Long CD_PACIENTE_INTERNADO = 905574l;
//
//	private boolean pacienteInternado = false;
//
//	private static Usuario usuarioResponsavel;
//
//	@Override
//	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 600, value = "txManagerNuclearis")
//	public synchronized Set<Long> iniciarAtendimentos() throws QueryParametroInvalidoException, DAOException, UsuarioInexistenteException, SalvarOuAtualizarPacienteException, PacienteCodigoDuplicadoException, PacienteCpfDuplicadoException,
//			SalvarOuAtualizarPlanoException, NomeTipoPlanoDuplicadoException, TipoPlanoInexistenteException, ConversaoDataException, VagaPacienteInexistenteException, CampoInvalidoException, ExclusaoVagaPacienteInvalidaException,
//			VagaPacienteDuplicadoException, TipoLogAlteracaoInexistenteException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException, SalvarOuAtualizarVagaExameException, SalvarOuAtualizarVagaException,
//			MaxVagaDataExcedidoException, ControleVagaPacienteInexistenteException, NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException, MotivoInexistenteException, VagaInexistenteException, PacienteInexistenteException,
//			RemarcacaoVagaPacienteDuplicadoException, SalvarOuAtualizarRemarcacaoVagaPacienteException, DataNascimentoInvalidaException, EnvioEmailException, DataObitoInvalidaException, FormatoDataInvalidoException,
//			ProtocoloInexistenteException, EtapaInexistenteException, SalvarOuAtualizarMedicoException, ExecucaoExameInexistenteException, SubEtapaExameJaTerminadaException, SalvarEventoException, FalhaNoSistemaException,
//			NoSuchAlgorithmException, AutorizacaoStatusInexistenteException, IOException, NotaInexistenteException, SalvarOuAtualizarNotaException, TipoAutorizacaoInexistenteException, AutorizacaoExameDuplicadoException,
//			SalvarOuAtualizarAutorizacaoExameException, ProtocoloEtapaInexistenteException, IntegracaoException, SubEtapaInexistenteException, PrestadorSolicitanteInexistenteException, SalvarOuAtualizarPacientePlanoException,
//			PacientePlanoDuplicadaException, PlanoInexistenteException, ChaveConfiguracaoInexistenteException, ValidationException, MarcacaoComMenos30DiasException {
//
//		log.info("Consultar a lista de atendimentos do dia na tabela do MV");
//
//		// TODO Usuário nuclearis
//		if (usuarioResponsavel == null) {
//			usuarioResponsavel = usuarioBO.getPorId(1L);
//		}
//		Calendar dataInicial = DataUtil.getCalendarUTC();
//		dataInicial = DataUtil.setHoraInicioDia(dataInicial);
//		Calendar dataFinal = DataUtil.getCalendarUTC();
//		dataFinal = DataUtil.setHoraFimDia(dataFinal);
//
//		// Consulta a lista de atendimentos do dia na tabela do MV
//		// caso algum atendimento seja feita sem ter passado pelo agendamento,
//		// essa consulta irá capturar e processar esse atendimento.
//
//		// Consulta as vagas paciente do dia corrente
//		Map<Long, VagaPaciente> mapVagaPaciente = getListaCdIntegracaoVagaPaciente(dataInicial, dataFinal);
//
//		List<VagaPacienteDTO> listaDadosMV = mvBO.getListaAtendimentosDoDia(null, null);
//
//		mapVagaPaciente = this.atualizarMapVagaPaciente(mapVagaPaciente, listaDadosMV);
//
//		log.info("CONSULTA DE ATENDIMENTOS");
//
//		for (VagaPacienteDTO dados : listaDadosMV) {
//
//			VagaPacienteDTO dadosMV = dados;
//			log.info("Paciente:" + dadosMV.getPaciente().getNome());
//			// Verifica se a vagaPaciente ja está gerada
//			VagaPaciente vagaPaciente = mapVagaPaciente.get(dadosMV.getCodSolicitacaoExame());
//
//			if (vagaPaciente == null) {
//				// processa o novo atendimento e gera uma vagaPaciente para ele
//				vagaPaciente = processarAtendimento(dadosMV, usuarioResponsavel);
//				if (vagaPaciente != null) {
//					mapVagaPaciente.put(dadosMV.getCodSolicitacaoExame(), vagaPaciente);
//				}
//			}
//		}
//
//		log.info("Encontrados " + listaDadosMV.size() + " novos atendimentos");
//		return mapVagaPaciente.keySet();
//	}
//
//	//	private Map<Long, VagaPaciente> atualizarMapVagaPaciente(Map<Long, VagaPaciente> mapVagaPaciente, List<VagaPacienteDTO> listaDadosMV) {
//	//		Map<Long, VagaPaciente> novoMap = new LinkedHashMap<Long, VagaPaciente>();
//	//		for (Long cdIntegracao : mapVagaPaciente.keySet()) {
//	//			for (VagaPacienteDTO dados : listaDadosMV) {
//	//				if (((VagaPacienteDTO) dados).getCodigoItemPedido().equals(cdIntegracao)) {
//	//					novoMap.put(cdIntegracao, mapVagaPaciente.get(cdIntegracao));
//	//					break;
//	//				}
//	//			}
//	//		}
//	//		return novoMap;
//	//	}
//
//	@Override
//	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 600, value = "txManagerNuclearis")
//	public synchronized Set<Long> realizarAgendamentos() throws QueryParametroInvalidoException, DAOException, UsuarioInexistenteException, ConversaoDataException, SalvarOuAtualizarPacienteException, PacienteCodigoDuplicadoException,
//			PacienteCpfDuplicadoException, SalvarOuAtualizarPlanoException, NomeTipoPlanoDuplicadoException, TipoPlanoInexistenteException, VagaPacienteInexistenteException, CampoInvalidoException, ExclusaoVagaPacienteInvalidaException,
//			VagaPacienteDuplicadoException, TipoLogAlteracaoInexistenteException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException, SalvarOuAtualizarVagaExameException, SalvarOuAtualizarVagaException,
//			MaxVagaDataExcedidoException, ControleVagaPacienteInexistenteException, NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException, MotivoInexistenteException, VagaInexistenteException, PacienteInexistenteException,
//			RemarcacaoVagaPacienteDuplicadoException, SalvarOuAtualizarRemarcacaoVagaPacienteException, DataNascimentoInvalidaException, EnvioEmailException, DataObitoInvalidaException, FormatoDataInvalidoException,
//			ProtocoloInexistenteException, EtapaInexistenteException, SalvarOuAtualizarMedicoException, ExecucaoExameInexistenteException, SubEtapaExameJaTerminadaException, SalvarEventoException, FalhaNoSistemaException,
//			ProtocoloEtapaInexistenteException, TipoAutorizacaoInexistenteException, AutorizacaoExameDuplicadoException, SalvarOuAtualizarAutorizacaoExameException, ExameJaCanceladoException, IntegracaoException,
//			SubEtapaInexistenteException, NoSuchAlgorithmException, AutorizacaoStatusInexistenteException, IOException, NotaInexistenteException, SalvarOuAtualizarNotaException, PrestadorSolicitanteInexistenteException,
//			SalvarOuAtualizarPacientePlanoException, PacientePlanoDuplicadaException, PlanoInexistenteException, ChaveConfiguracaoInexistenteException, ValidationException, MarcacaoComMenos30DiasException {
//
//		log.info("Consultar a lista de agendamentos do dia na tabela do MV");
//
//		// TODO Usuário nuclearis
//		if (usuarioResponsavel == null) {
//			usuarioResponsavel = usuarioBO.getPorId(1L);
//		}
//
//		// TODO Usuário nuclearis
//
//		Map<Long, VagaPaciente> mapVagaPaciente = getListaCdIntegracaoVagaPaciente(DataUtil.getCalendarUTC(), null);
//
//		// retorna todas os agendamentos do MV
//		Map<Long, VagaPacienteDTO> agendamentos = mvBO.getListaAgendamentos();
//
//		log.info(" Quantidade de agendamentos dos próximos 60 dias no MV: " + agendamentos.size());
//
//		for (VagaPacienteDTO dadosMarcacaoPOJO : agendamentos.values()) {
//
//			VagaPacienteDTO VagaPacienteDTO = dadosMarcacaoPOJO;
//
//			//			Paciente paciente = pacienteBO.getPorCdIntegracao(dadosMarcacaoPOJO.getPaciente().getId());
//
//			//verifica se o paciente já está cadastrado na tabela de paciente do Nuclearis. Caso não esteja ele é cadastrado, caso contrátrio ele é atualizado.
//			Paciente paciente = popularPaciente(dadosMarcacaoPOJO.getPaciente());
//
//			if (dadosMarcacaoPOJO.getCodigoAtendimento() == null) {
//				paciente.setPreCadastro(Boolean.TRUE);
//			}
//
//			//verifica se o plano e a operadora já estão cadastrados no Nuclearis. Caso não esteja eles são cadastrado, caso contrátrio são atualizados.
//			Plano plano = salvarOuAtulizarConvenioEPlano(dadosMarcacaoPOJO.getConvenio());
//
//			VagaPaciente vagaPaciente = mapVagaPaciente.get(VagaPacienteDTO.getCodSolicitacaoExame());
//
//			if (vagaPaciente != null) {
//				Calendar dtHoraInicio = vagaPaciente.getListaExameExecucao().get(0).getDataInicioPrevista();
//				Calendar dataRecepcaoUTC = DataUtil.convertDateWithTimezoneToCalendarUTC(VagaPacienteDTO.getDtHoraExame().getTime(), usuarioResponsavel.getTimezone().getNome());
//				if (dtHoraInicio.compareTo(dataRecepcaoUTC) != 0) {
//					processarAgendamento(VagaPacienteDTO, usuarioResponsavel, paciente, plano);
//				}
//				if (vagaPaciente.getPaciente() != null && CD_PACIENTE_INTERNADO.equals(vagaPaciente.getPaciente().getCdIntegracao())) {
//					mapVagaPaciente.remove(vagaPaciente);
//					break;
//				}
//			} else {
//				log.warn(" Paciente: " + VagaPacienteDTO.getPaciente().getNome());
//				vagaPaciente = processarAgendamento(VagaPacienteDTO, usuarioResponsavel);
//			}
//			if (pacienteInternado) {
//				Calendar dtHorarioRecepcaoUTC = DataUtil.convertDateWithTimezoneToCalendarUTC(VagaPacienteDTO.getDtHoraExame().getTime(), usuarioResponsavel.getTimezone().getNome());
//				atenderPaciente(VagaPacienteDTO, usuarioResponsavel, vagaPaciente, dtHorarioRecepcaoUTC, vagaPaciente.getMedicoSolicitante(), true);
//				pacienteInternado = false;
//			}
//
//		}
//		return agendamentos.keySet();
//
//	}
//
//	//	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 600, value = "txManagerNuclearis")
//	private VagaPacienteDTO tratarRegistroPacienteInternado(VagaPacienteDTO VagaPacienteDTO) throws DAOException, QueryParametroInvalidoException {
//		Long cdAtendimentoPacienteInternado = getCdAtendimentoDoPacienteInternado(VagaPacienteDTO);
//		if (cdAtendimentoPacienteInternado != null) {
//			List<VagaPacienteDTO> dadosPacienteInternado = mvBO.getListaAtendimentosDoDia(null, cdAtendimentoPacienteInternado);
//			if (dadosPacienteInternado != null && dadosPacienteInternado.size() > 0) {
//				VagaPacienteDTO atendimentoPacienteInternado = dadosPacienteInternado.get(0);
//				VagaPacienteDTO.getPaciente().setNome(atendimentoPacienteInternado.getPaciente().getNome());
//				VagaPacienteDTO.getPaciente().setId(atendimentoPacienteInternado.getPaciente().getId());
//				VagaPacienteDTO.setCodigoAtendimento(cdAtendimentoPacienteInternado);
//			}
//		}
//		return VagaPacienteDTO;
//	}
//
//	private Long getCdAtendimentoDoPacienteInternado(VagaPacienteDTO VagaPacienteDTO) {
//		if (StringUtils.isNotEmpty(VagaPacienteDTO.getDescricaoExame())) {
//			String[] split = VagaPacienteDTO.getDescricaoExame().split("\\*");
//
//			for (String atendimento : split) {
//				atendimento = StringUtils.trimToEmpty(atendimento);
//				if (atendimento.startsWith("AT")) {
//					String[] splitAtendimento = atendimento.split(" ");
//					return splitAtendimento.length > 0 ? Long.valueOf(splitAtendimento[1]) : null;
//				}
//			}
//		}
//		return null;
//	}
//
//	// As vagas pacientes que NÃO deverão ser canceladas, estarão contidas na
//	// listaCdIntegracao
//	@Override
//	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 600, value = "txManagerNuclearis")
//	public synchronized void verificarCancelamentos(Set<Long> listaCdIntegracao) throws QueryParametroInvalidoException, DAOException, MotivoInexistenteException, VagaPacienteInexistenteException, CampoInvalidoException,
//			ExclusaoVagaPacienteInvalidaException, UsuarioInexistenteException, VagaPacienteDuplicadoException, TipoLogAlteracaoInexistenteException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException,
//			SalvarOuAtualizarVagaExameException, SalvarOuAtualizarVagaException, MaxVagaDataExcedidoException, ConversaoDataException, ControleVagaPacienteInexistenteException, NotificacaoInexistenteException,
//			SalvarOuAtualizarNotificacaoException, FormatoDataInvalidoException, SubEtapaInexistenteException, ChaveConfiguracaoInexistenteException {
//		Usuario usuarioResponsavel = usuarioBO.getPorId(1L);
//		Set<VagaPaciente> listaVagaPaciente = vagaPacienteBO.getListaVagaPacienteParaCancelamentoIntegracao(listaCdIntegracao);
//		Motivo motivo = motivoBO.getPorId(TipoMotivoEnum.CANCELAMENTO.getId());
//		List<Motivo> motivos = new ArrayList<Motivo>();
//		motivos.add(motivo);
//		for (VagaPaciente vagaPaciente : listaVagaPaciente) {
//			log.warn("Paciente desmarcado");
//			log.warn("ID Vaga Paciente: " + vagaPaciente.getId());
//			log.warn("CD integracao Vaga Paciente: " + vagaPaciente.getCdIntegracao());
//			log.warn("iniciando a desmarcacao ");
//			vagaPaciente = this.gerenciadorAgendaBO.desmarcarProcedimento(vagaPaciente, motivos, motivo.getNome(), true, usuarioResponsavel, usuarioResponsavel.getListaInstituicao().get(0));
//			vagaPaciente.setCdAtendimento(null);
//			vagaPaciente.setCdIntegracao(null);
//			vagaPacienteBO.salvarOuAtualizar(vagaPaciente);
//
//		}
//
//	}
//
//	private synchronized VagaPaciente processarAgendamento(VagaPacienteDTO dadosMV, Usuario usuarioResponsavel) throws QueryParametroInvalidoException, DAOException, SalvarOuAtualizarPacienteException, PacienteCodigoDuplicadoException,
//			PacienteCpfDuplicadoException, SalvarOuAtualizarPlanoException, NomeTipoPlanoDuplicadoException, TipoPlanoInexistenteException, ConversaoDataException, VagaPacienteInexistenteException, CampoInvalidoException,
//			ExclusaoVagaPacienteInvalidaException, UsuarioInexistenteException, VagaPacienteDuplicadoException, TipoLogAlteracaoInexistenteException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException,
//			SalvarOuAtualizarVagaExameException, SalvarOuAtualizarVagaException, MaxVagaDataExcedidoException, ControleVagaPacienteInexistenteException, NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException,
//			MotivoInexistenteException, VagaInexistenteException, PacienteInexistenteException, RemarcacaoVagaPacienteDuplicadoException, SalvarOuAtualizarRemarcacaoVagaPacienteException, DataNascimentoInvalidaException,
//			EnvioEmailException, DataObitoInvalidaException, FormatoDataInvalidoException, ProtocoloInexistenteException, EtapaInexistenteException, SalvarOuAtualizarMedicoException, ExecucaoExameInexistenteException,
//			SubEtapaExameJaTerminadaException, SalvarEventoException, FalhaNoSistemaException, ProtocoloEtapaInexistenteException, TipoAutorizacaoInexistenteException, AutorizacaoExameDuplicadoException,
//			SalvarOuAtualizarAutorizacaoExameException, ExameJaCanceladoException, IntegracaoException, SubEtapaInexistenteException, PrestadorSolicitanteInexistenteException, SalvarOuAtualizarPacientePlanoException,
//			PacientePlanoDuplicadaException, PlanoInexistenteException, ChaveConfiguracaoInexistenteException, ValidationException, MarcacaoComMenos30DiasException {
//
//		log.info("Iniciando processamento dos agendamentos");
//		pacienteInternado = false;
//		if (GerenciadorIntegracaoMVBO_old.CD_PACIENTE_INTERNADO.equals(dadosMV.getPaciente().getId())) {
//			dadosMV = tratarRegistroPacienteInternado(dadosMV);
//			pacienteInternado = true;
//		}
//
//		//		Paciente paciente = pacienteBO.getPorCdIntegracao(dadosMV.getPaciente().getId());
//
//		// Paciente paciente =
//		// pacienteBO.getPorCpf(atendimento.getCpfPaciente());
//
//		// verifica se o paciente já está cadastrado na tabela de paciente do
//		// Nuclearis. Caso não esteja ele é cadastrado, caso contrátrio ele é
//		// atualizado.
//		Paciente paciente = popularPaciente(dadosMV);
//
//		// verifica se o plano e a operadora já estão cadastrados no Nuclearis.
//		// Caso não esteja eles são cadastrado, caso contrátrio são atualizados.
//		Plano plano = salvarOuAtulizarConvenioEPlano(dadosMV.getConvenio());
//
//		ControleVagaPaciente ultimaVagaPaciente = controleVagaPacienteBO.getPorIdPaciente(paciente.getId());
//		VagaPaciente vagaPaciente = null;
//		if (pacienteInternado) {
//			ultimaVagaPaciente = null;
//		}
//		if (ultimaVagaPaciente != null) {
//			ExameExecucao ee = ultimaVagaPaciente.getVagaPaciente().getListaExameExecucao().get(0);
//			if (ee != null) {
//				vagaPaciente = verificarRemarcacao(ee.getDataInicioPrevista(), dadosMV, ultimaVagaPaciente.getVagaPaciente(), ultimaVagaPaciente, usuarioResponsavel, plano);
//			}
//			if (vagaPaciente == null) {
//				vagaPaciente = ultimaVagaPaciente.getVagaPaciente();
//			}
//
//		} else {
//			vagaPaciente = encaixarPaciente(dadosMV, usuarioResponsavel, ultimaVagaPaciente, paciente, plano);
//			if (vagaPaciente != null) {
//				associarAutorizacaoExame(vagaPaciente.getExame(), plano, vagaPaciente.getVaga().getMaquina().getInstituicao());
//			}
//
//		}
//
//		log.info("Finalizando processamento dos agendamentos");
//		return vagaPaciente;
//	}
//
//	private synchronized VagaPaciente processarAtendimento(VagaPacienteDTO dadosMV, Usuario usuarioResponsavel)
//			throws QueryParametroInvalidoException, DAOException, SalvarOuAtualizarPacienteException, PacienteCodigoDuplicadoException, PacienteCpfDuplicadoException, SalvarOuAtualizarPlanoException, NomeTipoPlanoDuplicadoException,
//			TipoPlanoInexistenteException, ConversaoDataException, VagaPacienteInexistenteException, CampoInvalidoException, ExclusaoVagaPacienteInvalidaException, UsuarioInexistenteException, VagaPacienteDuplicadoException,
//			TipoLogAlteracaoInexistenteException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException, SalvarOuAtualizarVagaExameException, SalvarOuAtualizarVagaException, MaxVagaDataExcedidoException,
//			ControleVagaPacienteInexistenteException, NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException, MotivoInexistenteException, VagaInexistenteException, PacienteInexistenteException,
//			RemarcacaoVagaPacienteDuplicadoException, SalvarOuAtualizarRemarcacaoVagaPacienteException, DataNascimentoInvalidaException, EnvioEmailException, DataObitoInvalidaException, FormatoDataInvalidoException,
//			ProtocoloInexistenteException, EtapaInexistenteException, SalvarOuAtualizarMedicoException, ExecucaoExameInexistenteException, SubEtapaExameJaTerminadaException, SalvarEventoException, FalhaNoSistemaException,
//			NoSuchAlgorithmException, AutorizacaoStatusInexistenteException, IOException, NotaInexistenteException, SalvarOuAtualizarNotaException, TipoAutorizacaoInexistenteException, AutorizacaoExameDuplicadoException,
//			SalvarOuAtualizarAutorizacaoExameException, ProtocoloEtapaInexistenteException, IntegracaoException, SubEtapaInexistenteException, PrestadorSolicitanteInexistenteException, SalvarOuAtualizarPacientePlanoException,
//			PacientePlanoDuplicadaException, PlanoInexistenteException, ChaveConfiguracaoInexistenteException, ValidationException, MarcacaoComMenos30DiasException {
//
//		log.info("Iniciando processamento dos atendimentos");
//		VagaPaciente vagaPaciente = null;
//		if (dadosMV.getCodigoAtendimento() != null) {
//			// Consulta a lista de atendimentos do dia na tabela do MV
//			Paciente paciente = pacienteBO.getPorCdIntegracao(dadosMV.getPaciente().getId());
//
//			// Paciente paciente =
//			// pacienteBO.getPorCpf(atendimento.getCpfPaciente());
//
//			// verifica se o paciente já está cadastrado na tabela de paciente
//			// do Nuclearis. Caso não esteja ele é cadastrado, caso contrátrio
//			// ele é atualizado.
//			paciente = popularPaciente(dadosMV.getPaciente());
//
//			if (dadosMV.getCodigoAtendimento() == null) {
//				paciente.setPreCadastro(Boolean.TRUE);
//			}
//
//			// verifica se o plano e a operadora já estão cadastrados no
//			// Nuclearis. Caso não esteja eles são cadastrado, caso contrátrio
//			// são atualizados.
//			Plano plano = salvarOuAtulizarConvenioEPlano(dadosMV.getConvenio());
//
//			ControleVagaPaciente ultimaVagaPaciente = controleVagaPacienteBO.getPorIdPaciente(paciente.getId());
//			Calendar dtHorarioRecepcaoUTC = DataUtil.convertDateWithTimezoneToCalendarUTC(dadosMV.getDtHoraExame().getTime(), usuarioResponsavel.getTimezone().getNome());
//
//			if (ultimaVagaPaciente != null) {
//				vagaPaciente = ultimaVagaPaciente.getVagaPaciente();
//			} else {
//				vagaPaciente = encaixarPaciente(dadosMV, usuarioResponsavel, ultimaVagaPaciente, paciente, plano);
//				if (vagaPaciente != null) {
//					associarAutorizacaoExame(vagaPaciente.getExame(), plano, vagaPaciente.getVaga().getMaquina().getInstituicao());
//				}
//
//			}
//			if (vagaPaciente != null) {
//				ExameExecucao exameExecucao = getExameExecucaoSubEtapaRecepcao(vagaPaciente);
//				if (exameExecucao != null) {
//					String dataRecepcao = DataUtil.calendarToStringDdMmAaaa(dtHorarioRecepcaoUTC);
//					String dataInicioPrevista = DataUtil.calendarToStringDdMmAaaa(exameExecucao.getDataInicioPrevista());
//					PrestadorSolicitante prestadorSolicitante = this.salvarOuAtualizarPrestadorSolicitante(dadosMV.getPrestadorSolicitantePOJO());
//					if (dataRecepcao.equals(dataInicioPrevista)) {
//						atenderPaciente(dadosMV, usuarioResponsavel, vagaPaciente, dtHorarioRecepcaoUTC, prestadorSolicitante, false);
//					} else {
//						atenderPaciente(dadosMV, usuarioResponsavel, vagaPaciente, exameExecucao.getDataInicioPrevista(), prestadorSolicitante, false);
//					}
//				}
//
//			}
//		}
//
//		log.info("Finalizando processamento dos atendimentos");
//		return vagaPaciente;
//	}
//
//	//	@Override
//	//	public Paciente popularPaciente(PacienteDTO pacienteMV) throws QueryParametroInvalidoException, DAOException, SalvarOuAtualizarPacienteException, PacienteCodigoDuplicadoException, PacienteCpfDuplicadoException {
//	//		Paciente paciente = super.popularPaciente(pacienteMV);
//	//
//	//		if (pacienteMV.getEmissorRg() != null && !pacienteMV.getEmissorRg().equals(paciente.getRgOrgao())) {
//	//			if (pacienteMV.getEmissorRg().length() < 4) {
//	//				paciente.setRgOrgao(pacienteMV.getEmissorRg());
//	//			} else {
//	//				String estadoRg = pacienteMV.getEmissorRg().substring(pacienteMV.getEmissorRg().length() - 2);
//	//				int indexOf = pacienteMV.getEmissorRg().indexOf(estadoRg);
//	//				//foi necessário deixar fixo o país para consultar o estado. 
//	//				Estado estado = estadoBO.getPorUfSiglaPais3(estadoRg, "BRA");
//	//				paciente.setRgOrgao(pacienteMV.getEmissorRg().substring(0, indexOf));
//	//				paciente.setRgEstado(estado);
//	//			}
//	//		}
//	//
//	//		return pacienteBO.salvarOuAtualizar(paciente);
//	//	}
//
//	@Override
//	protected Plano salvarOuAtulizarConvenioEPlano(final ConvenioDTO convenio) throws QueryParametroInvalidoException, DAOException, TipoPlanoInexistenteException, SalvarOuAtualizarPlanoException, NomeTipoPlanoDuplicadoException {
//		Operadora operadora = operadoraBO.getPorCdIntegracao(convenio.getIdConvenio());
//		Plano plano = null;
//		if (operadora == null) {
//			operadora = new Operadora();
//		} else {
//			plano = planoBO.getPorCdIntegracao(convenio.getPlano().getIdPlano(), operadora.getId());
//			if (plano != null && plano.getNome().equals(convenio.getPlano().getNomePlano()) && operadora.getNome().equals(convenio.getNomeConvenio())) {
//				return plano;
//			}
//		}
//		operadora.setCdIntegracao(convenio.getIdConvenio());
//		operadora.setNome(convenio.getNomeConvenio());
//
//		if (plano == null) {
//			plano = new Plano();
//		}
//
//		plano.setCdIntegracao(convenio.getPlano().getIdPlano());
//		plano.setNome(convenio.getPlano().getNomePlano());
//		plano.setOperadora(operadora);
//		//Para a integração com o MV, não existe tipo plano, pois o MV não possui esse conceito. Como para o nuclearis é necessário o tipo de plano, foi setado um plano individual como padrão. 
//		TipoPlano tpPlano = tipoPlanoBO.getPorId(1L);
//		plano.setTipoPlano(tpPlano);
//		plano = planoBO.salvarOuAtualizar(plano);
//		return plano;
//	}
//
//	//	private VagaPaciente verificarRemarcacao(Calendar horarioInicioPrevisto, VagaPacienteDTO dadosMV, VagaPaciente ultimaVagaPaciente, ControleVagaPaciente ctrlVagaPaciente, Usuario usuarioLogado, Plano plano)
//	//			throws ConversaoDataException, QueryParametroInvalidoException, DAOException, VagaPacienteInexistenteException, CampoInvalidoException, ExclusaoVagaPacienteInvalidaException, UsuarioInexistenteException,
//	//			VagaPacienteDuplicadoException, TipoLogAlteracaoInexistenteException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException, SalvarOuAtualizarVagaExameException, SalvarOuAtualizarVagaException,
//	//			MaxVagaDataExcedidoException, ControleVagaPacienteInexistenteException, NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException, MotivoInexistenteException, ProtocoloEtapaInexistenteException,
//	//			FormatoDataInvalidoException, VagaInexistenteException, PacienteInexistenteException, RemarcacaoVagaPacienteDuplicadoException, SalvarOuAtualizarRemarcacaoVagaPacienteException, SalvarOuAtualizarPacienteException,
//	//			PacienteCodigoDuplicadoException, DataNascimentoInvalidaException, EnvioEmailException, PacienteCpfDuplicadoException, DataObitoInvalidaException, ProtocoloInexistenteException, EtapaInexistenteException,
//	//			ExameJaCanceladoException, SalvarOuAtualizarMedicoException, IntegracaoException, SubEtapaInexistenteException, PrestadorSolicitanteInexistenteException {
//	//
//	//		Motivo motivo = motivoBO.getPorId(TipoMotivoEnum.REMARCACAO.getId());
//	//		if (isReconvocao(dadosMV)) {
//	//			motivo = motivoBO.getPorId(TipoMotivoEnum.RECONVOCACAO.getId());
//	//		}
//	//		List<Motivo> motivos = new ArrayList<Motivo>();
//	//		motivos.add(motivo);
//	//
//	//		ProtocoloEtapa protocoloEtapa = protocoloEtapaBO.getPorIdProtocoloIdEtapa(ctrlVagaPaciente.getProtocolo().getId(), ctrlVagaPaciente.getEtapaAgendada().getId());
//	//
//	//		if (protocoloEtapa != null && protocoloEtapa.getIntervaloMinimo() != null && protocoloEtapa.getIntervaloMaximo() != null) {
//	//
//	//			ControleVagaPaciente controleVagaPacienteAtual = ctrlVagaPaciente;
//	//
//	//			Calendar proximaDataMinimaMarcacao = Calendar.getInstance();
//	//			proximaDataMinimaMarcacao.setTimeInMillis(controleVagaPacienteAtual.getDataFinalMarcacao().getTimeInMillis() + protocoloEtapa.getIntervaloMinimo().getTimeInMillis());
//	//
//	//			Calendar proximaDataMaximaMarcacao = Calendar.getInstance();
//	//			proximaDataMaximaMarcacao.setTimeInMillis(controleVagaPacienteAtual.getDataFinalMarcacao().getTimeInMillis() + protocoloEtapa.getIntervaloMaximo().getTimeInMillis());
//	//
//	//			// 4. Verificar se o ultimoControleVagaPaciente não se encontra no
//	//			// prazo de 30 dias
//	//			if (!horarioInicioPrevisto.after(proximaDataMinimaMarcacao) || !horarioInicioPrevisto.before(proximaDataMaximaMarcacao)) {
//	//
//	//				// -> Converter as datas para o timezone do usuario
//	//				Date dataMinima = DataUtil.convertDateToDateInTimeZone(proximaDataMinimaMarcacao.getTime(), usuarioLogado.getTimezone().getNome());
//	//				Date dataMaxima = DataUtil.convertDateToDateInTimeZone(proximaDataMaximaMarcacao.getTime(), usuarioLogado.getTimezone().getNome());
//	//				proximaDataMinimaMarcacao = DataUtil.convertDateToCalendar(dataMinima, "UTC");
//	//				proximaDataMaximaMarcacao = DataUtil.convertDateToCalendar(dataMaxima, "UTC");
//	//
//	//				// -> Se as datas não se batem, lancar uma exceção informando
//	//				// que já vagas agendadas para o paciente selecionado
//	//				this.gerenciadorAgendaBO.desmarcarProcedimento(ctrlVagaPaciente.getVagaPaciente(), motivos, motivo.getNome(), true, usuarioLogado, usuarioLogado.getListaInstituicao().get(0));
//	//			}
//	//		} else if (ctrlVagaPaciente != null && ctrlVagaPaciente.getId() > 0) {
//	//			Calendar dtHoraMVUTC = DataUtil.convertDateWithTimezoneToCalendarUTC(dadosMV.getDtHoraExame().getTime(), usuarioLogado.getTimezone().getNome());
//	//			if ((horarioInicioPrevisto.compareTo(dtHoraMVUTC) != 0) && !controleVagaPacienteBO.validarDataUltimaMarcacao(ctrlVagaPaciente, protocoloEtapa.getProtocolo(), horarioInicioPrevisto)) {
//	//				VagaPaciente vagaPaciente = vagaPacienteBO.getPorCdIntegracao(dadosMV.getCodSolicitacaoExame());
//	//				if (vagaPaciente == null) {
//	//
//	//					// TODO Paciente marcou um exame em menos de 30 dias. Como
//	//					// deve proceder com a integração
//	//					log.warn("Paciente remarcou");
//	//					if (ctrlVagaPaciente.getVagaPaciente() != null && ctrlVagaPaciente.getVagaPaciente().getPaciente() != null) {
//	//
//	//						log.warn("Id Ultima Vaga Paciente: " + ctrlVagaPaciente.getVagaPaciente().getId());
//	//						log.warn("Id Paciente: " + ctrlVagaPaciente.getVagaPaciente().getPaciente().getId());
//	//						log.warn("Paciente: " + ctrlVagaPaciente.getVagaPaciente().getPaciente().getNome());
//	//						log.warn("DataHora do Agendamento: " + horarioInicioPrevisto.getTime());
//	//						log.warn("DataHora do novo agendamento: " + dadosMV.getDtHoraExame().getTime());
//	//
//	//						log.warn("Paciente marcou um exame em menos de 30 dias. Como deve proceder com a integracao");
//	//						return remarcarProcedimento(dadosMV, ctrlVagaPaciente, plano, motivos, usuarioLogado);
//	//					}
//	//				}
//	//			}
//	//		}
//	//		return null;
//	//	}
//
//	//	private boolean isReconvocao(VagaPacienteDTO dadosMV) {
//	//		if (StringUtils.isNotEmpty(dadosMV.getObservacao())) {
//	//			return dadosMV.getObservacao().toUpperCase().contains("RECONVOCADA") || dadosMV.getObservacao().toUpperCase().contains("RECONVOCADO");
//	//		}
//	//		return false;
//	//	}
//	//
//	//	private VagaPaciente remarcarProcedimento(VagaPacienteDTO dadosMV, ControleVagaPaciente controleVagaPaciente, Plano plano, List<Motivo> motivos, Usuario usuarioLogado)
//	//			throws QueryParametroInvalidoException, DAOException, VagaPacienteInexistenteException, CampoInvalidoException, ExclusaoVagaPacienteInvalidaException, UsuarioInexistenteException, VagaPacienteDuplicadoException,
//	//			TipoLogAlteracaoInexistenteException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException, SalvarOuAtualizarVagaExameException, SalvarOuAtualizarVagaException, MaxVagaDataExcedidoException,
//	//			ConversaoDataException, ControleVagaPacienteInexistenteException, NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException, FormatoDataInvalidoException, VagaInexistenteException, PacienteInexistenteException,
//	//			RemarcacaoVagaPacienteDuplicadoException, SalvarOuAtualizarRemarcacaoVagaPacienteException, SalvarOuAtualizarPacienteException, PacienteCodigoDuplicadoException, DataNascimentoInvalidaException, EnvioEmailException,
//	//			PacienteCpfDuplicadoException, DataObitoInvalidaException, ProtocoloInexistenteException, EtapaInexistenteException, MotivoInexistenteException, SalvarOuAtualizarMedicoException, IntegracaoException,
//	//			SubEtapaInexistenteException, PrestadorSolicitanteInexistenteException, ProtocoloEtapaInexistenteException {
//	//
//	//		VagaPaciente vagaPaciente = this.gerenciadorAgendaBO.desmarcarProcedimento(controleVagaPaciente.getVagaPaciente(), motivos, motivos.get(0).getNome(), true, usuarioLogado, usuarioLogado.getListaInstituicao().get(0));
//	//		vagaPaciente.setCdAtendimento(null);
//	//		vagaPaciente.setCdIntegracao(null);
//	//		vagaPacienteBO.salvarOuAtualizar(vagaPaciente);
//	//
//	//		VagaPaciente novaVagaPaciente = this.encaixarPaciente(dadosMV, usuarioLogado, controleVagaPaciente, controleVagaPaciente.getVagaPaciente().getPaciente(), plano);
//	//		novaVagaPaciente.setCdAtendimento(controleVagaPaciente.getVagaPaciente().getCdAtendimento());
//	//		vagaPacienteBO.salvarOuAtualizar(novaVagaPaciente);
//	//
//	//		RemarcacaoVagaPaciente remarcacaoVP = new RemarcacaoVagaPaciente(novaVagaPaciente, controleVagaPaciente.getVagaPaciente());
//	//
//	//		boRemarcacaoVagaPaciente.salvarOuAtualizar(remarcacaoVP);
//	//		return novaVagaPaciente;
//	//
//	//	}
//
//	//	private PrestadorSolicitante preencherPrestadorSolicitante(VagaPacienteDTO atendimento) throws DAOException, QueryParametroInvalidoException, SalvarOuAtualizarMedicoException {
//	//
//	//		PrestadorSolicitante prestadorSolicitante = null;
//	//
//	//		if (atendimento.getCodigoPrestadorSolicitante() == null && atendimento.getNumeroConselhoPrestadorSolicitante() != null) {
//	//			prestadorSolicitante = prestadorSolicitanteBO.getPorNumeroConselho(atendimento.getNumeroConselhoPrestadorSolicitante());
//	//		} else if (atendimento.getCodigoPrestadorSolicitante() == null && atendimento.getNumeroConselhoPrestadorSolicitante() == null) {
//	//			return prestadorSolicitante;
//	//		} else {
//	//			prestadorSolicitante = prestadorSolicitanteBO.getPorCdIntegracao(atendimento.getCodigoPrestadorSolicitante());
//	//		}
//	//
//	//		if (prestadorSolicitante == null) {
//	//			prestadorSolicitante = new PrestadorSolicitante();
//	//		}
//	//		prestadorSolicitante.setCdIntegracao(atendimento.getCodigoPrestadorSolicitante());
//	//		prestadorSolicitante.setNumeroConselho(String.valueOf(atendimento.getNumeroConselhoPrestadorSolicitante()));
//	//		prestadorSolicitante.setNome(atendimento.getNomePrestadorSolicitante());
//	//		prestadorSolicitante = prestadorSolicitanteBO.salvarOuAtualizar(prestadorSolicitante);
//	//		return prestadorSolicitante;
//	//	}
//
//	/**
//	 * método que calcula o horário de máquina
//	 * 
//	 * @param etapa
//	 *            etapa
//	 * @param dataRecepcao
//	 *            data da recepção sem time zone
//	 * @param usuario
//	 *            usuario responsavel pelo procedimento
//	 * @return Calendar horário de máquina calculado
//	 * @throws ConversaoDataException
//	 * @throws QueryParametroInvalidoException
//	 * @throws DAOException
//	 * @throws PacienteInexistenteException
//	 * @throws PlanoInexistenteException
//	 * @throws PacientePlanoDuplicadaException
//	 * @throws SalvarOuAtualizarPacientePlanoException
//	 * @throws DataObitoInvalidaException
//	 * @throws DataNascimentoInvalidaException
//	 * @throws CampoInvalidoException
//	 */
//	//	private Calendar getHorarioMaquina(Calendar dataRecepcao, Usuario usuario, List<SubEtapa> listaSubEtapa) throws ConversaoDataException, DAOException, QueryParametroInvalidoException {
//	//		Long tempoDecorrido = 0L;
//	//		boolean subEtapaRefAgendaEcontrada = false;
//	//		Calendar dataRecepcaoUTC = DataUtil.convertDateWithTimezoneToCalendarUTC(dataRecepcao.getTime(), usuario.getTimezone().getNome());
//	//		// -> Itera as etapas ate encontrar a primeira etapa que exigeAgenda
//	//		// (etapa ref agenda) acumulando o tempo decorrido.
//	//		Iterator<SubEtapa> iter = listaSubEtapa.iterator();
//	//		while (!subEtapaRefAgendaEcontrada && iter.hasNext()) {
//	//
//	//			SubEtapa subEtapa = iter.next();
//	//
//	//			// -> Caso a etapa exija agenda seja como true para parar o while e
//	//			// acrescenta apenas o intervalo.
//	//			if (subEtapa.getTipoSubEtapa().getId().equals(TipoSubEtapaEnum.IMAGEM.getId())) {
//	//
//	//				subEtapaRefAgendaEcontrada = true;
//	//				tempoDecorrido += subEtapa.getIntervalo().getTimeInMillis();
//	//
//	//			} else {
//	//				// -> Caso contrario acrescenta o tempo total da etapa
//	//				// (intervalo + duracao)
//	//				tempoDecorrido += subEtapa.getIntervalo().getTimeInMillis() + subEtapa.getDuracao().getTimeInMillis();
//	//
//	//			}
//	//
//	//		}
//
//	// -> Define a Data da primeira etapa do exame, antes mesmo do
//	// intervalo.
//	//		Calendar dataImagem = DataUtil.getCalendarUTC();
//	//		dataImagem.setTimeInMillis(dataRecepcaoUTC.getTimeInMillis() + tempoDecorrido);
//	//		return dataImagem;
//	//	}
//
//	//	private Plano populaOpeadoraEPlano(VagaPacienteDTO atendimento) throws QueryParametroInvalidoException, DAOException, SalvarOuAtualizarPlanoException, NomeTipoPlanoDuplicadoException, TipoPlanoInexistenteException {
//	//		Operadora operadora = operadoraBO.getPorCdIntegracao(atendimento.getConvenio().getIdConvenio());
//	//		Plano plano = null;
//	//		if (operadora == null) {
//	//			operadora = new Operadora();
//	//		} else {
//	//			plano = planoBO.getPorCdIntegracao(atendimento.getConvenio().getPlano().getIdPlano(), operadora.getId());
//	//		}
//	//		operadora.setCdIntegracao(atendimento.getConvenio().getIdConvenio());
//	//		operadora.setNome(atendimento.getConvenio().getNomeConvenio());
//	//
//	//		if (plano == null) {
//	//			plano = new Plano();
//	//		}
//	//		plano.setCdIntegracao(atendimento.getConvenio().getPlano().getIdPlano());
//	//		plano.setNome(atendimento.getConvenio().getPlano().getNomePlano());
//	//		plano.setOperadora(operadora);
//	//		// Para a integração com o MV, não existe tipo plano, pois o MV não
//	//		// possui esse conceito. Como para o nuclearis é necessário o tipo de
//	//		// plano, foi setado um plano individual como padrão.
//	//		TipoPlano tpPlano = tipoPlanoBO.getPorId(1L);
//	//		plano.setTipoPlano(tpPlano);
//	//		plano = planoBO.salvarOuAtualizar(plano);
//	//		return plano;
//	//	}
//
//	//	@Override
//	//	private void associarAutorizacaoExame(Exame exame, Plano plano, Instituicao instituicao)
//	//			throws QueryParametroInvalidoException, DAOException, TipoAutorizacaoInexistenteException, AutorizacaoExameDuplicadoException, SalvarOuAtualizarAutorizacaoExameException {
//	//		AutorizacaoExame autorizacaoExame = new AutorizacaoExame();
//	//		autorizacaoExame.setExame(exame);
//	//		autorizacaoExame.setPlano(plano);
//	//		autorizacaoExame.setInstituicao(instituicao);
//	//		TipoAutorizacao tpAutorizacao = tipoAutorizacaoBO.getPorId(TipoAutorizacaoEnum.NAO_PRECISA_DE_AUTORIZACAO.getId());
//	//		if (tpAutorizacao != null) {
//	//			autorizacaoExame.setTipoAutorizacao(tpAutorizacao);
//	//		}
//	//		List<Long> listaInstituicoes = new ArrayList<Long>();
//	//		listaInstituicoes.add(instituicao.getId());
//	//		log.warn("inicio autorizacao exame");
//	//		if (!autorizacaoExameBO.existeAutorizacaoExamePorIdExameEIdPlanoEListaIdInstituicao(exame.getId(), plano.getId(), listaInstituicoes)) {
//	//			log.warn("exame autorizado");
//	//			autorizacaoExameBO.salvarOuAtualizar(autorizacaoExame);
//	//		} else {
//	//			log.warn("não foi setado a autorizado exame ");
//	//		}
//	//	}
//
//	private Paciente popularPaciente(VagaPacienteDTO atendimento)
//			throws QueryParametroInvalidoException, DAOException, SalvarOuAtualizarPacienteException, PacienteCodigoDuplicadoException, PacienteCpfDuplicadoException, CampoInvalidoException, ConversaoDataException,
//			DataNascimentoInvalidaException, DataObitoInvalidaException, SalvarOuAtualizarPacientePlanoException, PacientePlanoDuplicadaException, PlanoInexistenteException, PacienteInexistenteException {
//
//		Paciente paciente = super.popularPaciente(atendimento.getPaciente());
//
//		if (atendimento.getPaciente().getEmissorRg() != null && !atendimento.getPaciente().getEmissorRg().equals(paciente.getRgOrgao())) {
//			if (atendimento.getPaciente().getEmissorRg().length() < 4) {
//				paciente.setRgOrgao(atendimento.getPaciente().getEmissorRg());
//			} else {
//				String estadoRg = atendimento.getPaciente().getEmissorRg().substring(atendimento.getPaciente().getEmissorRg().length() - 2);
//				int indexOf = atendimento.getPaciente().getEmissorRg().indexOf(estadoRg);
//				//foi necessário deixar fixo o país para consultar o estado. 
//				Estado estado = estadoBO.getPorUfSiglaPais3(estadoRg, "BRA");
//				paciente.setRgOrgao(atendimento.getPaciente().getEmissorRg().substring(0, indexOf));
//				paciente.setRgEstado(estado);
//			}
//		}
//
//		return pacienteBO.salvarOuAtualizar(paciente);
//	}
//
//	//	private Paciente preencherTelefones(VagaPacienteDTO atendimento, Paciente paciente) {
//	//		if (atendimento.getTelefonePaciente() != null) {
//	//			String telefone = atendimento.getTelefonePaciente().replaceAll("[^\\d]", "");
//	//			if (telefone.length() > 11) {
//	//				paciente.setTelefoneCelular(!telefone.substring(2).equals(paciente.getTelefoneCelular()) ? telefone.substring(2) : paciente.getTelefoneCelular());
//	//			} else {
//	//				paciente.setTelefoneCelular(!telefone.equals(paciente.getTelefoneCelular()) ? telefone : paciente.getTelefoneCelular());
//	//			}
//	//		}
//	//
//	//		return paciente;
//	//	}
//
//	/*
//		 * Seta a data e horário inicio e fim para a sub etapa de recepção
//		 */
//	//	private void atenderPaciente(VagaPacienteDTO dadosIntegracao, Usuario usuarioResponsavel, VagaPaciente vagaPaciente, Calendar horarioRecepcao, PrestadorSolicitante prestadorSolicitante, Boolean adicionarNota)
//	//			throws DAOException, QueryParametroInvalidoException, TipoLogAlteracaoInexistenteException, CampoInvalidoException, ExecucaoExameInexistenteException, SubEtapaExameJaTerminadaException, NoSuchAlgorithmException,
//	//			AutorizacaoStatusInexistenteException, VagaPacienteInexistenteException, VagaPacienteDuplicadoException, UsuarioInexistenteException, ConversaoDataException, IOException, NotaInexistenteException, SalvarOuAtualizarNotaException,
//	//			SalvarOuAtualizarMedicoException {
//	//
//	//		ExameExecucao ee = this.getExameExecucaoSubEtapaRecepcao(vagaPaciente);
//	//		//setado o horário de maquina como sendo o horário de início da sub etapa de recepção
//	//		if (dadosIntegracao.getCodigoAtendimento() != null && ee != null) {
//	//			atenderRecepcao(usuarioResponsavel, vagaPaciente, horarioRecepcao, prestadorSolicitante, ee, adicionarNota);
//	//		}
//	//
//	//	}
//
//	@Override
//	protected VagaPaciente verificarRemarcacao(Calendar horarioInicioPrevisto, VagaPacienteDTO dadosMV, VagaPaciente ultimaVagaPaciente, ControleVagaPaciente ctrlVagaPaciente, Usuario usuarioLogado, Plano plano)
//			throws ConversaoDataException, QueryParametroInvalidoException, DAOException, VagaPacienteInexistenteException, CampoInvalidoException, ExclusaoVagaPacienteInvalidaException, UsuarioInexistenteException,
//			VagaPacienteDuplicadoException, TipoLogAlteracaoInexistenteException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException, SalvarOuAtualizarVagaExameException, SalvarOuAtualizarVagaException,
//			MaxVagaDataExcedidoException, ControleVagaPacienteInexistenteException, NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException, MotivoInexistenteException, ProtocoloEtapaInexistenteException,
//			FormatoDataInvalidoException, VagaInexistenteException, PacienteInexistenteException, RemarcacaoVagaPacienteDuplicadoException, SalvarOuAtualizarRemarcacaoVagaPacienteException, SalvarOuAtualizarPacienteException,
//			PacienteCodigoDuplicadoException, DataNascimentoInvalidaException, EnvioEmailException, PacienteCpfDuplicadoException, DataObitoInvalidaException, ProtocoloInexistenteException, EtapaInexistenteException,
//			ExameJaCanceladoException, SalvarOuAtualizarMedicoException, IntegracaoException, SubEtapaInexistenteException, PrestadorSolicitanteInexistenteException, ChaveConfiguracaoInexistenteException, ValidationException,
//			MarcacaoComMenos30DiasException {
//
//		Motivo motivo = motivoBO.getPorId(TipoMotivoEnum.REMARCACAO.getId());
//		if (isReconvocao(dadosMV)) {
//			motivo = motivoBO.getPorId(TipoMotivoEnum.RECONVOCACAO.getId());
//		}
//		List<Motivo> motivos = new ArrayList<Motivo>();
//		motivos.add(motivo);
//
//		ProtocoloEtapa protocoloEtapa = protocoloEtapaBO.getPorIdProtocoloIdEtapa(ctrlVagaPaciente.getProtocolo().getId(), ctrlVagaPaciente.getEtapaAgendada().getId());
//
//		if (protocoloEtapa != null && protocoloEtapa.getIntervaloMinimo() != null && protocoloEtapa.getIntervaloMaximo() != null) {
//
//			ControleVagaPaciente controleVagaPacienteAtual = ctrlVagaPaciente;
//
//			Calendar proximaDataMinimaMarcacao = Calendar.getInstance();
//			proximaDataMinimaMarcacao.setTimeInMillis(controleVagaPacienteAtual.getDataFinalMarcacao().getTimeInMillis() + protocoloEtapa.getIntervaloMinimo().getTimeInMillis());
//
//			Calendar proximaDataMaximaMarcacao = Calendar.getInstance();
//			proximaDataMaximaMarcacao.setTimeInMillis(controleVagaPacienteAtual.getDataFinalMarcacao().getTimeInMillis() + protocoloEtapa.getIntervaloMaximo().getTimeInMillis());
//
//			// 4. Verificar se o ultimoControleVagaPaciente não se encontra no
//			// prazo de 30 dias
//			if (!horarioInicioPrevisto.after(proximaDataMinimaMarcacao) || !horarioInicioPrevisto.before(proximaDataMaximaMarcacao)) {
//
//				// -> Converter as datas para o timezone do usuario
//				Date dataMinima = DataUtil.convertDateToDateInTimeZone(proximaDataMinimaMarcacao.getTime(), usuarioLogado.getTimezone().getNome());
//				Date dataMaxima = DataUtil.convertDateToDateInTimeZone(proximaDataMaximaMarcacao.getTime(), usuarioLogado.getTimezone().getNome());
//				proximaDataMinimaMarcacao = DataUtil.convertDateToCalendar(dataMinima, "UTC");
//				proximaDataMaximaMarcacao = DataUtil.convertDateToCalendar(dataMaxima, "UTC");
//
//				// -> Se as datas não se batem, lancar uma exceção informando
//				// que já vagas agendadas para o paciente selecionado
//				this.gerenciadorAgendaBO.desmarcarProcedimento(ctrlVagaPaciente.getVagaPaciente(), motivos, motivo.getNome(), true, usuarioLogado, usuarioLogado.getListaInstituicao().get(0));
//			}
//		} else if (ctrlVagaPaciente != null && ctrlVagaPaciente.getId() > 0) {
//			Calendar dtHoraMVUTC = DataUtil.convertDateWithTimezoneToCalendarUTC(dadosMV.getDtHoraExame().getTime(), usuarioLogado.getTimezone().getNome());
//			if ((horarioInicioPrevisto.compareTo(dtHoraMVUTC) != 0) && !controleVagaPacienteBO.validarDataUltimaMarcacao(ctrlVagaPaciente, protocoloEtapa.getProtocolo(), horarioInicioPrevisto)) {
//				VagaPaciente vagaPaciente = vagaPacienteBO.getPorCdIntegracao(dadosMV.getCodSolicitacaoExame());
//				if (vagaPaciente == null) {
//
//					// TODO Paciente marcou um exame em menos de 30 dias. Como
//					// deve proceder com a integração
//					log.warn("Paciente remarcou");
//					if (ctrlVagaPaciente.getVagaPaciente() != null && ctrlVagaPaciente.getVagaPaciente().getPaciente() != null) {
//
//						log.warn("Id Ultima Vaga Paciente: " + ctrlVagaPaciente.getVagaPaciente().getId());
//						log.warn("Id Paciente: " + ctrlVagaPaciente.getVagaPaciente().getPaciente().getId());
//						log.warn("Paciente: " + ctrlVagaPaciente.getVagaPaciente().getPaciente().getNome());
//						log.warn("DataHora do Agendamento: " + horarioInicioPrevisto.getTime());
//						log.warn("DataHora do novo agendamento: " + dadosMV.getDtHoraExame().getTime());
//
//						log.warn("Paciente marcou um exame em menos de 30 dias. Como deve proceder com a integracao");
//						return remarcarProcedimento(dadosMV, ctrlVagaPaciente, plano, motivos, usuarioLogado);
//					}
//				}
//			}
//		}
//		return null;
//	}
//
//}
