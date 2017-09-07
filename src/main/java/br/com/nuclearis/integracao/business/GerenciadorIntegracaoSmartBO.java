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
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Component;
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
//import br.com.nuclearis.core.business.exception.MaxVagaDataExcedidoException;
//import br.com.nuclearis.core.business.exception.SalvarEventoException;
//import br.com.nuclearis.core.business.exception.SubEtapaExameJaTerminadaException;
//import br.com.nuclearis.core.business.interfaces.IGerenciadorExameExecucaoBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.ILogAlteracaoBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.IPaisBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.agenda.IControleVagaPacienteBO;
//import br.com.nuclearis.core.dataaccess.exception.AutorizacaoExameDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.AutorizacaoStatusInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.DAOException;
//import br.com.nuclearis.core.dataaccess.exception.ExameInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.ExecucaoExameInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.IdPushAndroidDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.IdPushIOSDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.IdiomaInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.MaquinaInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.MotivoInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.NomeTipoPlanoDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.NotaInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.NotificacaoInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.PacienteCodigoDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.PacienteCpfDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.PacienteInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.PrestadorSolicitanteInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.QueryParametroInvalidoException;
//import br.com.nuclearis.core.dataaccess.exception.RemarcacaoVagaPacienteDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarAutorizacaoExameException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarMedicoException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarNotaException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarNotificacaoException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarPacienteException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarPlanoException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarRemarcacaoVagaPacienteException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarUsuarioException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarVagaExameException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarVagaException;
//import br.com.nuclearis.core.dataaccess.exception.SubEtapaInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.TipoAutorizacaoInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.TipoLogAlteracaoInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.TipoPlanoInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.UsuarioEmailDuplicadoException;
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
//import br.com.nuclearis.core.entity.endereco.Cidade;
//import br.com.nuclearis.core.entity.endereco.Endereco;
//import br.com.nuclearis.core.entity.usuario.Usuario;
//import br.com.nuclearis.core.entity.worklist.ExameExecucao;
//import br.com.nuclearis.core.entity.worklist.Motivo;
//import br.com.nuclearis.core.entity.worklist.Motivo.TipoMotivoEnum;
//import br.com.nuclearis.core.entity.worklist.VagaPaciente;
//import br.com.nuclearis.core.util.DataUtil;
//import br.com.nuclearis.core.util.exception.ConversaoDataException;
//import br.com.nuclearis.core.util.exception.FormatoDataInvalidoException;
//import br.com.nuclearis.integracao.business.interfaces.IGerenciadorIntegracaoBO_old;
//import br.com.nuclearis.integracao.bo.interfaces.AbstractIntegracaoSmartBO;
//import br.com.nuclearis.integracao.dto.ConvenioDTO;
//import br.com.nuclearis.integracao.dto.PacienteDTO;
//import br.com.nuclearis.integracao.dto.VagaPacienteDTO;
//
//@Component("boGerenciadorIntegracaoSmart")
//public class GerenciadorIntegracaoSmartBO extends AbstractAtendimentoIntegracaoBO implements IGerenciadorIntegracaoBO_old {
//
//	private static final String STATUS_AGENDADO = "A";
//
//	private static Logger log = Logger.getLogger(GerenciadorIntegracaoMVBO_old.class);
//
//	@Resource
//	private AbstractIntegracaoSmartBO integracaoBO;
//
//	@Resource
//	private IControleVagaPacienteBO controleVagaPacienteBO;
//
//	@Resource
//	private ILogAlteracaoBO logAlteracaoBO;
//
//	@Resource
//	private IGerenciadorExameExecucaoBO gerenciadorExameExecucaoBO;
//
//	@Resource
//	private IPaisBO paisBO;
//
//	private Usuario usuarioResponsavel;
//
//	@Override
//	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 600, value = "txManagerNuclearis")
//	public synchronized Set<Long> iniciarAtendimentos()
//			throws QueryParametroInvalidoException, DAOException, UsuarioInexistenteException, SalvarOuAtualizarPacienteException, PacienteCodigoDuplicadoException, PacienteCpfDuplicadoException, SalvarOuAtualizarPlanoException,
//			NomeTipoPlanoDuplicadoException, TipoPlanoInexistenteException, ConversaoDataException, VagaPacienteInexistenteException, CampoInvalidoException, ExclusaoVagaPacienteInvalidaException, VagaPacienteDuplicadoException,
//			TipoLogAlteracaoInexistenteException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException, SalvarOuAtualizarVagaExameException, SalvarOuAtualizarVagaException, MaxVagaDataExcedidoException,
//			ControleVagaPacienteInexistenteException, NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException, MotivoInexistenteException, VagaInexistenteException, PacienteInexistenteException,
//			RemarcacaoVagaPacienteDuplicadoException, SalvarOuAtualizarRemarcacaoVagaPacienteException, DataNascimentoInvalidaException, EnvioEmailException, DataObitoInvalidaException, FormatoDataInvalidoException,
//			ProtocoloInexistenteException, EtapaInexistenteException, SalvarOuAtualizarMedicoException, ExecucaoExameInexistenteException, SubEtapaExameJaTerminadaException, SalvarEventoException, FalhaNoSistemaException,
//			NoSuchAlgorithmException, AutorizacaoStatusInexistenteException, IOException, NotaInexistenteException, SalvarOuAtualizarNotaException, TipoAutorizacaoInexistenteException, AutorizacaoExameDuplicadoException,
//			SalvarOuAtualizarAutorizacaoExameException, ProtocoloEtapaInexistenteException, IntegracaoException, SubEtapaInexistenteException, PrestadorSolicitanteInexistenteException {
//
//		log.info("Consultar a lista de atendimentos do dia na tabela do MV");
//
//		//TODO O Smart somente disponibiliza o usuario responável pelo agendamento, então no atendimento o usuário que está sendo utilizado é o Nuclearis.
//		if (usuarioResponsavel == null) {
//			usuarioResponsavel = usuarioBO.getPorId(1L);
//		}
//
//		Calendar dataInicial = DataUtil.getCalendarUTC();
//		dataInicial = DataUtil.setHoraInicioDia(dataInicial);
//		Calendar dataFinal = DataUtil.getCalendarUTC();
//		dataFinal = DataUtil.setHoraFimDia(dataFinal);
//
//		//Consulta as vagas paciente do dia corrente
//		Map<Long, VagaPaciente> mapVagaPaciente = getListaCdIntegracaoVagaPaciente(dataInicial, dataFinal);
//
//		//Consulta a lista de atendimentos do dia na tabela do MV
//		// caso algum atendimento seja feita sem ter passado pelo agendamento, essa consulta irá capturar e processar esse atendimento.
//		//TODO Após pleno funcionamento passando null como parâmetro, será necessário remover o parametro deste método
//		List<VagaPacienteDTO> listaDadosIntegracao = integracaoBO.getListaAtendimentosDoDia(null, null);
//
//		//Atualizar map
//		mapVagaPaciente = this.atualizarMapVagaPaciente(mapVagaPaciente, listaDadosIntegracao);
//
//		log.info("CONSULTA DE ATENDIMENTOS");
//
//		for (VagaPacienteDTO dados : listaDadosIntegracao) {
//
//			VagaPacienteDTO dadosSmart = dados;
//			log.info("Paciente:" + dadosSmart.getPaciente().getNome());
//			//Verifica se a vagaPaciente ja está gerada
//			VagaPaciente vagaPaciente = mapVagaPaciente.get(dadosSmart.getCodSolicitacaoExame());
//			if (vagaPaciente == null) {
//				//processa o novo atendimento e gera uma vagaPaciente para ele
//				vagaPaciente = processarAtendimento(dadosSmart, usuarioResponsavel);
//				if (vagaPaciente != null) {
//					mapVagaPaciente.put(dadosSmart.getCodSolicitacaoExame(), vagaPaciente);
//				}
//			}
//		}
//
//		log.info("Encontrados " + listaDadosIntegracao.size() + " novos atendimentos");
//		return mapVagaPaciente.keySet();
//	}
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
//			UsuarioEmailDuplicadoException, SalvarOuAtualizarUsuarioException, IdPushAndroidDuplicadoException, IdPushIOSDuplicadoException, IdiomaInexistenteException, SubEtapaInexistenteException,
//			PrestadorSolicitanteInexistenteException {
//
//		log.warn("Consultar a lista de agendamentos do dia na tabela do SMART");
//
//		Map<Long, VagaPaciente> mapVagaPaciente = getListaCdIntegracaoVagaPaciente(DataUtil.getCalendarUTC(), null);
//
//		//TODO verificar a possibilitar de tornar o campo usuarioResponsavel STATIC
//		if (usuarioResponsavel == null) {
//			usuarioResponsavel = usuarioBO.getPorId(1L);
//		}
//
//		//retorna todas os agendamentos do MV
//		Map<Long, VagaPacienteDTO> agendamentos = integracaoBO.getListaAgendamentos();
//
//		log.warn(" Quantidade de agendamentos do SMART: " + agendamentos.size());
//
//		for (VagaPacienteDTO dadosMarcacaoPOJO : agendamentos.values()) {
//
//			VagaPacienteDTO VagaPacienteDTO = dadosMarcacaoPOJO;
//
//			//Para o SMART, os campos email e sexo do usuário não são obrigatórios, por isso foi utilizado o usuário Nuclearis como padrão para a integração. 
//			//usuarioResponsavel = integracaoBO.getUsuarioByLogin(dadosMarcacaoPOJO.getUsuarioResponsavel());
//
//			VagaPaciente vagaPaciente = mapVagaPaciente.get(VagaPacienteDTO.getCodSolicitacaoExame());
//
//			Paciente paciente = pacienteBO.getPorCdIntegracao(dadosMarcacaoPOJO.getPaciente().getId());
//
//			//verifica se o paciente já está cadastrado na tabela de paciente do Nuclearis. Caso não esteja ele é cadastrado, caso contrátrio ele é atualizado.
//			paciente = popularPaciente(dadosMarcacaoPOJO.getPaciente());
//
//			if (dadosMarcacaoPOJO.getCodigoAtendimento() == null) {
//				paciente.setPreCadastro(Boolean.TRUE);
//			}
//
//			//			ConvenioDTO convenioPOJO = integracaoBO.getConvenioEPlano(dadosMarcacaoPOJO.getConvenio().getNomeConvenio(), dadosMarcacaoPOJO.getConvenio().getPlano().getNomePlano());
//
//			//verifica se o plano e a operadora já estão cadastrados no Nuclearis. Caso não esteja eles são cadastrado, caso contrátrio são atualizados.
//			Plano plano = salvarOuAtulizarConvenioEPlano(dadosMarcacaoPOJO.getConvenio());
//
//			if (vagaPaciente != null) {
//				Calendar dtHoraInicio = vagaPaciente.getListaExameExecucao().get(0).getDataInicioPrevista();
//				Calendar dataRecepcaoUTC = DataUtil.convertDateWithTimezoneToCalendarUTC(VagaPacienteDTO.getDtHoraExame().getTime(), usuarioResponsavel.getTimezone().getNome());
//				if (dtHoraInicio.compareTo(dataRecepcaoUTC) != 0) {
//					processarAgendamento(VagaPacienteDTO, usuarioResponsavel, paciente, plano);
//				}
//			} else {
//				log.warn(" Paciente: " + VagaPacienteDTO.getPaciente().getNome());
//				processarAgendamento(VagaPacienteDTO, usuarioResponsavel, paciente, plano);
//			}
//		}
//		return agendamentos.keySet();
//
//	}
//
//	//As vagas pacientes que NÃO deverão ser canceladas, estarão contidas na listaCdIntegracao
//	@Override
//	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 600, value = "txManagerNuclearis")
//	public synchronized void verificarCancelamentos(Set<Long> listaCdIntegracao)
//			throws QueryParametroInvalidoException, DAOException, MotivoInexistenteException, VagaPacienteInexistenteException, CampoInvalidoException, ExclusaoVagaPacienteInvalidaException, UsuarioInexistenteException,
//			VagaPacienteDuplicadoException, TipoLogAlteracaoInexistenteException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException, SalvarOuAtualizarVagaExameException, SalvarOuAtualizarVagaException,
//			MaxVagaDataExcedidoException, ConversaoDataException, ControleVagaPacienteInexistenteException, NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException, FormatoDataInvalidoException, SubEtapaInexistenteException {
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
//			vagaPaciente = this.gerenciadorAgendaBO.desmarcarProcedimento(vagaPaciente, motivos, motivo.getDescricao(), true, usuarioResponsavel, usuarioResponsavel.getListaInstituicao().get(0));
//			vagaPaciente.setCdAtendimento(null);
//			vagaPaciente.setCdIntegracao(null);
//			vagaPacienteBO.salvarOuAtualizar(vagaPaciente);
//
//		}
//
//	}
//
//	private synchronized VagaPaciente processarAtendimento(VagaPacienteDTO dadosIntegracao, Usuario usuarioResponsavel)
//			throws QueryParametroInvalidoException, DAOException, SalvarOuAtualizarPacienteException, PacienteCodigoDuplicadoException, PacienteCpfDuplicadoException, SalvarOuAtualizarPlanoException, NomeTipoPlanoDuplicadoException,
//			TipoPlanoInexistenteException, ConversaoDataException, VagaPacienteInexistenteException, CampoInvalidoException, ExclusaoVagaPacienteInvalidaException, UsuarioInexistenteException, VagaPacienteDuplicadoException,
//			TipoLogAlteracaoInexistenteException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException, SalvarOuAtualizarVagaExameException, SalvarOuAtualizarVagaException, MaxVagaDataExcedidoException,
//			ControleVagaPacienteInexistenteException, NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException, MotivoInexistenteException, VagaInexistenteException, PacienteInexistenteException,
//			RemarcacaoVagaPacienteDuplicadoException, SalvarOuAtualizarRemarcacaoVagaPacienteException, DataNascimentoInvalidaException, EnvioEmailException, DataObitoInvalidaException, FormatoDataInvalidoException,
//			ProtocoloInexistenteException, EtapaInexistenteException, SalvarOuAtualizarMedicoException, ExecucaoExameInexistenteException, SubEtapaExameJaTerminadaException, SalvarEventoException, FalhaNoSistemaException,
//			NoSuchAlgorithmException, AutorizacaoStatusInexistenteException, IOException, NotaInexistenteException, SalvarOuAtualizarNotaException, TipoAutorizacaoInexistenteException, AutorizacaoExameDuplicadoException,
//			SalvarOuAtualizarAutorizacaoExameException, ProtocoloEtapaInexistenteException, IntegracaoException, SubEtapaInexistenteException, PrestadorSolicitanteInexistenteException {
//
//		log.warn("Iniciando processamento dos atendimentos");
//		VagaPaciente vagaPaciente = null;
//		if (dadosIntegracao.getDtHoraExame() != null) {
//			//Consulta a lista de atendimentos do dia na tabela do MV
//			Paciente paciente = pacienteBO.getPorCdIntegracao(dadosIntegracao.getPaciente().getId());
//
//			//Paciente paciente = pacienteBO.getPorCpf(atendimento.getCpfPaciente());
//
//			VagaPacienteDTO dadosPacientePOJO = integracaoBO.getPacientePorId(dadosIntegracao.getPaciente().getId());
//
//			//Necessário fazer esses sets aqui, pois no SMART as informações de peso e altura ficam no paciente
//			dadosIntegracao.setAlturaPaciente(dadosPacientePOJO.getAlturaPaciente());
//			dadosIntegracao.setPesoPaciente(dadosPacientePOJO.getPesoPaciente());
//			//verifica se o paciente já está cadastrado na tabela de paciente do Nuclearis. Caso não esteja ele é cadastrado, caso contrátrio ele é atualizado.
//			paciente = popularPaciente(dadosPacientePOJO.getPaciente());
//
//			if (dadosIntegracao.getCodigoAtendimento() == null) {
//				paciente.setPreCadastro(Boolean.TRUE);
//			}
//
//			ConvenioDTO convenioPOJO = integracaoBO.getConvenioEPlano(dadosIntegracao.getConvenio().getNomeConvenio(), dadosIntegracao.getConvenio().getPlano().getNomePlano());
//
//			log.warn("Paciente: " + dadosPacientePOJO.getPaciente().getNome());
//			log.warn("Codigo Solicitacao Exame: " + dadosIntegracao.getCodSolicitacaoExame());
//			log.warn("Codigo convenio: " + convenioPOJO.getIdConvenio());
//			log.warn("Nome convenio: " + convenioPOJO.getNomeConvenio());
//			log.warn("Codigo plano: " + convenioPOJO.getPlano().getIdPlano());
//			log.warn("Nome plano: " + convenioPOJO.getPlano().getNomePlano());
//			//verifica se o plano e a operadora já estão cadastrados no Nuclearis. Caso não esteja eles são cadastrado, caso contrátrio são atualizados.
//			Plano plano = salvarOuAtulizarConvenioEPlano(convenioPOJO);
//
//			ControleVagaPaciente ultimaVagaPaciente = controleVagaPacienteBO.getPorIdPaciente(paciente.getId());
//			Calendar dtHorarioRecepcaoUTC = DataUtil.convertDateWithTimezoneToCalendarUTC(dadosIntegracao.getDtHoraExame().getTime(), usuarioResponsavel.getTimezone().getNome());
//
//			if (ultimaVagaPaciente != null && ultimaVagaPaciente.getEtapaAgendada().getCdIntegracao().equals(dadosIntegracao.getCodigoExame())) {
//				vagaPaciente = ultimaVagaPaciente.getVagaPaciente();
//			} else {
//				vagaPaciente = encaixarPaciente(dadosIntegracao, usuarioResponsavel, ultimaVagaPaciente, paciente, plano);
//				if (vagaPaciente != null) {
//					associarAutorizacaoExame(vagaPaciente.getExame(), plano, vagaPaciente.getVaga().getMaquina().getInstituicao());
//				}
//
//			}
//			ExameExecucao exameExecucao = getExameExecucaoSubEtapaRecepcao(vagaPaciente);
//			if (exameExecucao != null) {
//				String dataRecepcao = DataUtil.calendarToStringDdMmAaaa(dtHorarioRecepcaoUTC);
//				String dataInicioPrevista = DataUtil.calendarToStringDdMmAaaa(exameExecucao.getDataInicioPrevista());
//				PrestadorSolicitante prestadorSolicitante = this.salvarOuAtualizarPrestadorSolicitante(dadosIntegracao.getPrestadorSolicitantePOJO());
//				if (dataRecepcao.equals(dataInicioPrevista)) {
//					atenderPaciente(dadosIntegracao, usuarioResponsavel, vagaPaciente, dtHorarioRecepcaoUTC, prestadorSolicitante, false);
//				} else {
//					atenderPaciente(dadosIntegracao, usuarioResponsavel, vagaPaciente, exameExecucao.getDataInicioPrevista(), prestadorSolicitante, false);
//				}
//			}
//
//		}
//
//		log.warn("Finalizando processamento dos atendimentos");
//		return vagaPaciente;
//	}
//
//	/*
//	 * Seta a data e horário inicio e fim para a sub etapa de recepção
//	 */
//	//	private void atenderPaciente(VagaPacienteDTO dadosIntegracao, Usuario usuarioResponsavel, VagaPaciente vagaPaciente, Calendar horarioRecepcao, PrestadorSolicitante prestadorSolicitante, Boolean adicionarNota)
//	//			throws DAOException, QueryParametroInvalidoException, TipoLogAlteracaoInexistenteException, CampoInvalidoException, ExecucaoExameInexistenteException, SubEtapaExameJaTerminadaException, NoSuchAlgorithmException,
//	//			AutorizacaoStatusInexistenteException, VagaPacienteInexistenteException, VagaPacienteDuplicadoException, UsuarioInexistenteException, ConversaoDataException, IOException, NotaInexistenteException, SalvarOuAtualizarNotaException,
//	//			SalvarOuAtualizarMedicoException {
//	//
//	//		ExameExecucao ee = this.getExameExecucaoSubEtapaRecepcao(vagaPaciente);
//	//		//setado o horário de maquina como sendo o horário de início da sub etapa de recepção
//	//		if (dadosIntegracao.getDtHoraExame() != null && ee != null) {
//	//			atenderPaciente(dadosIntegracao, usuarioResponsavel, vagaPaciente, horarioRecepcao, vagaPaciente.getMedicoSolicitante(), true);
//	//		}
//	//
//	//	}
//
//	@Override
//	public Paciente popularPaciente(PacienteDTO pacienteIntegracao) throws QueryParametroInvalidoException, DAOException, SalvarOuAtualizarPacienteException, PacienteCodigoDuplicadoException, PacienteCpfDuplicadoException {
//
//		Paciente paciente = super.popularPaciente(pacienteIntegracao);
//
//		//Preencher dados do endereço do paciente
//
//		if (StringUtils.isNotEmpty(pacienteIntegracao.getEndereco().getCidade()) && StringUtils.isNotBlank(pacienteIntegracao.getEndereco().getUf())) {
//			//Remover a associa
//			//Pais pais = paisBO.getPorSigla2("BR");
//			Cidade cidade = cidadeBO.getPorNomeUf(pacienteIntegracao.getEndereco().getCidade(), pacienteIntegracao.getEndereco().getUf());
//			//List<Cidade> cidades = cidadeBO.getCidadesPorNomeEstadoEPais(pacienteIntegracao.getEndereco().getCidade(), pacienteIntegracao.getEndereco().getUf(), pais.getSigla2());
//			if (cidade != null && paciente.getEndereco() == null
//					|| (pacienteIntegracao.getEndereco() != null && paciente.getEndereco() != null && pacienteIntegracao.getEndereco().getLogradouro().toLowerCase().trim().equals(paciente.getEndereco().getRua().toLowerCase().trim()))) {
//				Endereco endereco = new Endereco();
//				endereco.setCep(pacienteIntegracao.getEndereco().getCep());
//				endereco.setRua(pacienteIntegracao.getEndereco().getLogradouro());
//				endereco.setComplemento(pacienteIntegracao.getEndereco().getComplemento());
//
//				endereco.setCidade(cidade);
//
//				endereco = enderecoBO.salvarOuAtualizar(endereco);
//				paciente.setEndereco(endereco);
//
//				if (StringUtils.isNotEmpty(pacienteIntegracao.getEndereco().getPais())) {
//					paciente.setPais(paisBO.getPorNomePortugues(pacienteIntegracao.getEndereco().getPais()));
//				}
//
//			}
//		}
//		return pacienteBO.salvarOuAtualizar(paciente);
//	}
//
//	@Override
//	protected Plano salvarOuAtulizarConvenioEPlano(final ConvenioDTO convenio) throws QueryParametroInvalidoException, DAOException, TipoPlanoInexistenteException, SalvarOuAtualizarPlanoException, NomeTipoPlanoDuplicadoException {
//		Operadora operadora = operadoraBO.getPorCdIntegracao(convenio.getIdConvenio());
//		Plano plano = null;
//		if (operadora == null) {
//			operadora = new Operadora();
//		} else if (convenio.getPlano().getIdPlano() == null) {
//			List<Plano> listaPlanos = planoBO.getPorCdIntegracaoOperadora(convenio.getIdConvenio());
//			plano = listaPlanos.get(0);
//		}
//
//		operadora.setCdIntegracao(convenio.getIdConvenio());
//		operadora.setNome(convenio.getNomeConvenio());
//
//		if (plano == null) {
//			plano = new Plano();
//		}
//
//		//Feito dessa forma, pois alguns convênio não possuem planos
//		if (convenio.getPlano().getIdPlano() == null) {
//			plano.setNome(operadora.getNome());
//		} else {
//			plano.setCdIntegracao(convenio.getPlano().getIdPlano());
//			plano.setNome(convenio.getPlano().getNomePlano());
//		}
//		plano.setOperadora(operadora);
//		//Para a integração com o MV, não existe tipo plano, pois o MV não possui esse conceito. Como para o nuclearis é necessário o tipo de plano, foi setado um plano individual como padrão. 
//		TipoPlano tpPlano = tipoPlanoBO.getPorId(1L);
//		plano.setTipoPlano(tpPlano);
//		plano = planoBO.salvarOuAtualizar(plano);
//		return plano;
//	}
//
//	@Override
//	public VagaPaciente verificarRemarcacao(Calendar horarioInicioPrevisto, VagaPacienteDTO dadosIntegracao, VagaPaciente ultimaVagaPaciente, ControleVagaPaciente ctrlVagaPaciente, Usuario usuarioLogado, Plano plano)
//			throws ConversaoDataException, QueryParametroInvalidoException, DAOException, VagaPacienteInexistenteException, CampoInvalidoException, ExclusaoVagaPacienteInvalidaException, UsuarioInexistenteException,
//			VagaPacienteDuplicadoException, TipoLogAlteracaoInexistenteException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException, SalvarOuAtualizarVagaExameException, SalvarOuAtualizarVagaException,
//			MaxVagaDataExcedidoException, ControleVagaPacienteInexistenteException, NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException, MotivoInexistenteException, ProtocoloEtapaInexistenteException,
//			FormatoDataInvalidoException, VagaInexistenteException, PacienteInexistenteException, RemarcacaoVagaPacienteDuplicadoException, SalvarOuAtualizarRemarcacaoVagaPacienteException, SalvarOuAtualizarPacienteException,
//			PacienteCodigoDuplicadoException, DataNascimentoInvalidaException, EnvioEmailException, PacienteCpfDuplicadoException, DataObitoInvalidaException, ProtocoloInexistenteException, EtapaInexistenteException,
//			ExameJaCanceladoException, SalvarOuAtualizarMedicoException, IntegracaoException, SubEtapaInexistenteException, PrestadorSolicitanteInexistenteException {
//
//		Motivo motivo = motivoBO.getPorId(TipoMotivoEnum.REMARCACAO.getId());
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
//			// 4. Verificar se o ultimoControleVagaPaciente não se encontra no prazo de 30 dias
//			if (!horarioInicioPrevisto.after(proximaDataMinimaMarcacao) || !horarioInicioPrevisto.before(proximaDataMaximaMarcacao)) {
//
//				// -> Converter as datas para o timezone do usuario
//				Date dataMinima = DataUtil.convertDateToDateInTimeZone(proximaDataMinimaMarcacao.getTime(), usuarioLogado.getTimezone().getNome());
//				Date dataMaxima = DataUtil.convertDateToDateInTimeZone(proximaDataMaximaMarcacao.getTime(), usuarioLogado.getTimezone().getNome());
//				proximaDataMinimaMarcacao = DataUtil.convertDateToCalendar(dataMinima, "UTC");
//				proximaDataMaximaMarcacao = DataUtil.convertDateToCalendar(dataMaxima, "UTC");
//
//				// -> Se as datas não se batem, lancar uma exceção informando que já vagas agendadas para o paciente selecionado
//				this.gerenciadorAgendaBO.desmarcarProcedimento(ctrlVagaPaciente.getVagaPaciente(), motivos, motivo.getDescricao(), true, usuarioLogado, usuarioLogado.getListaInstituicao().get(0));
//			}
//		} else if (ctrlVagaPaciente != null && ctrlVagaPaciente.getId() > 0) {
//			Calendar dtHoraMVUTC = DataUtil.convertDateWithTimezoneToCalendarUTC(dadosIntegracao.getDtHoraExame().getTime(), usuarioLogado.getTimezone().getNome());
//			if ((horarioInicioPrevisto.compareTo(dtHoraMVUTC) != 0) && STATUS_AGENDADO.equals(dadosIntegracao.getSituacao().toUpperCase())) {
//				VagaPaciente vagaPaciente = vagaPacienteBO.getPorCdIntegracao(dadosIntegracao.getCodSolicitacaoExame());
//				if (vagaPaciente == null) {
//
//					//TODO Paciente marcou um exame em menos de 30 dias. Como deve proceder com a integração 
//					log.warn("Paciente remarcou");
//					if (ctrlVagaPaciente.getVagaPaciente() != null && ctrlVagaPaciente.getVagaPaciente().getPaciente() != null) {
//
//						log.warn("Id Ultima Vaga Paciente: " + ctrlVagaPaciente.getVagaPaciente().getId());
//						log.warn("Id Paciente: " + ctrlVagaPaciente.getVagaPaciente().getPaciente().getId());
//						log.warn("Paciente: " + ctrlVagaPaciente.getVagaPaciente().getPaciente().getNome());
//						log.warn("DataHora do Agendamento: " + horarioInicioPrevisto.getTime());
//						log.warn("DataHora do novo agendamento: " + dadosIntegracao.getDtHoraExame().getTime());
//					}
//
//					log.warn("Paciente marcou um exame em menos de 30 dias. Como deve proceder com a integracao");
//					return remarcarProcedimento(dadosIntegracao, ctrlVagaPaciente, plano, motivos, usuarioLogado);
//				}
//			}
//		}
//		return null;
//
//	}
//}
