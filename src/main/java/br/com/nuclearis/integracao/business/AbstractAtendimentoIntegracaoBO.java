//package br.com.nuclearis.integracao.business;
//
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
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
//import br.com.nuclearis.core.business.interfaces.IGerenciadorAgendaBO;
//import br.com.nuclearis.core.business.interfaces.IGerenciadorExameExecucaoBO;
//import br.com.nuclearis.core.dataaccess.bo.SubEtapaBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.IAutorizacaoExameBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.ICidadeBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.IEnderecoBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.IEstadoBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.IExameExecucaoBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.IMaquinaBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.IMotivoBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.IOperadoraBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.IPacienteBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.IPlanoBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.IPrestadorSolicitanteBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.IRemarcacaoVagaPacienteBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.ITipoAutorizacaoBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.ITipoPlanoBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.ITipoSexoBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.IUsuarioBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.IVagaPacienteBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.agenda.IControleVagaPacienteBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.agenda.IEtapaBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.agenda.IProtocoloEtapaBO;
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
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarVagaExameException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarVagaException;
//import br.com.nuclearis.core.dataaccess.exception.SubEtapaInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.TipoAutorizacaoInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.TipoLogAlteracaoInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.TipoPlanoInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.UsuarioInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.VagaHorarioInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.VagaInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.VagaPacienteDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.VagaPacienteInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.agenda.ControleVagaPacienteInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.agenda.EtapaInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.agenda.ProtocoloEtapaInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.agenda.ProtocoloInexistenteException;
//import br.com.nuclearis.core.emailservice.exception.EnvioEmailException;
//import br.com.nuclearis.core.entity.AutorizacaoExame;
//import br.com.nuclearis.core.entity.Instituicao;
//import br.com.nuclearis.core.entity.Maquina;
//import br.com.nuclearis.core.entity.Nota;
//import br.com.nuclearis.core.entity.Paciente;
//import br.com.nuclearis.core.entity.Plano;
//import br.com.nuclearis.core.entity.PrestadorSolicitante;
//import br.com.nuclearis.core.entity.TipoAutorizacao;
//import br.com.nuclearis.core.entity.TipoAutorizacao.TipoAutorizacaoEnum;
//import br.com.nuclearis.core.entity.agenda.ControleVagaPaciente;
//import br.com.nuclearis.core.entity.agenda.Etapa;
//import br.com.nuclearis.core.entity.agenda.Protocolo;
//import br.com.nuclearis.core.entity.agenda.ProtocoloEtapa;
//import br.com.nuclearis.core.entity.agenda.SubEtapa;
//import br.com.nuclearis.core.entity.agenda.TipoSubEtapa.TipoSubEtapaEnum;
//import br.com.nuclearis.core.entity.usuario.TipoSexo;
//import br.com.nuclearis.core.entity.usuario.TipoSexo.TipoSexoEnum;
//import br.com.nuclearis.core.entity.usuario.Usuario;
//import br.com.nuclearis.core.entity.worklist.Exame;
//import br.com.nuclearis.core.entity.worklist.ExameExecucao;
//import br.com.nuclearis.core.entity.worklist.Motivo;
//import br.com.nuclearis.core.entity.worklist.RemarcacaoVagaPaciente;
//import br.com.nuclearis.core.entity.worklist.VagaPaciente;
//import br.com.nuclearis.core.util.DataUtil;
//import br.com.nuclearis.core.util.exception.ConversaoDataException;
//import br.com.nuclearis.core.util.exception.FormatoDataInvalidoException;
//import br.com.nuclearis.integracao.dto.ConvenioDTO;
//import br.com.nuclearis.integracao.dto.PacienteDTO;
//import br.com.nuclearis.integracao.dto.PrestadorSolicitanteDTO;
//import br.com.nuclearis.integracao.dto.VagaPacienteDTO;
//
//public abstract class AbstractAtendimentoIntegracaoBO {
//
//	private static final String RECONVOCADO = "RECONVOCADO";
//
//	private static final String RECONVOCADA = "RECONVOCADA";
//
//	private static Logger log = Logger.getLogger(AbstractAtendimentoIntegracaoBO.class);
//
//	@Resource
//	protected ITipoSexoBO tipoSexoBO;
//	@Resource
//	protected ICidadeBO cidadeBO;
//	@Resource
//	protected IOperadoraBO operadoraBO;
//	@Resource
//	protected IPlanoBO planoBO;
//	@Resource
//	protected ITipoPlanoBO tipoPlanoBO;
//	@Resource
//	protected IPacienteBO pacienteBO;
//	@Resource
//	protected IEnderecoBO enderecoBO;
//	@Resource
//	private IExameExecucaoBO exameExecucaoBO;
//	@Resource
//	private ITipoAutorizacaoBO tipoAutorizacaoBO;
//	@Resource
//	private IAutorizacaoExameBO autorizacaoExameBO;
//	@Resource
//	protected IEstadoBO estadoBO;
//	@Resource
//	protected IUsuarioBO usuarioBO;
//	@Resource
//	protected IEtapaBO etapaBO;
//	@Resource
//	protected IProtocoloEtapaBO protocoloEtapaBO;
//	@Resource
//	protected IMaquinaBO maquinaBO;
//	@Resource
//	protected IVagaPacienteBO vagaPacienteBO;
//	@Resource
//	protected IGerenciadorAgendaBO gerenciadorAgendaBO;
//	@Resource
//	protected SubEtapaBO subEtapaBO;
//	@Resource
//	private IControleVagaPacienteBO controleVagaPacienteBO;
//	@Resource
//	private IPrestadorSolicitanteBO prestadorSolicitanteBO;
//	@Resource
//	private IRemarcacaoVagaPacienteBO boRemarcacaoVagaPaciente;
//	@Resource
//	protected IMotivoBO motivoBO;
//	@Resource
//	protected IGerenciadorExameExecucaoBO gerenciadorExameExecucaoBO;
//
//	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 600, value = "txManagerNuclearis")
//	protected VagaPaciente encaixar(Usuario usuarioResponsavel, ControleVagaPaciente ultimaVagaPaciente, Paciente paciente, Plano plano, final Long codigoExame, final Calendar dtHoraPedido, final Long codigoIntegracao,
//			final Long codigoAtendimento, final float peso, final float altura, final PrestadorSolicitante prestadorSolicitante)
//			throws QueryParametroInvalidoException, DAOException, ConversaoDataException, CampoInvalidoException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException, SalvarOuAtualizarVagaExameException,
//			SalvarOuAtualizarVagaException, UsuarioInexistenteException, MaxVagaDataExcedidoException, NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException, VagaInexistenteException, PacienteInexistenteException,
//			VagaPacienteInexistenteException, VagaPacienteDuplicadoException, RemarcacaoVagaPacienteDuplicadoException, SalvarOuAtualizarRemarcacaoVagaPacienteException, SalvarOuAtualizarPacienteException, PacienteCodigoDuplicadoException,
//			DataNascimentoInvalidaException, EnvioEmailException, PacienteCpfDuplicadoException, DataObitoInvalidaException, FormatoDataInvalidoException, ProtocoloInexistenteException, EtapaInexistenteException, IntegracaoException,
//			SubEtapaInexistenteException, PrestadorSolicitanteInexistenteException, ChaveConfiguracaoInexistenteException, ValidationException, ControleVagaPacienteInexistenteException, ProtocoloEtapaInexistenteException,
//			MarcacaoComMenos30DiasException, VagaHorarioInexistenteException {
//		List<Etapa> etapas = etapaBO.getPorCdIntegracao(codigoExame);
//		Iterator<Etapa> it = etapas.iterator();
//		if (it.hasNext()) {
//			Etapa etapa = it.next();
//			List<ProtocoloEtapa> listaProtocoloEtapa = protocoloEtapaBO.getListaPorIdEtapa(etapa.getId());
//			if (listaProtocoloEtapa != null && listaProtocoloEtapa.size() > 0) {
//				//recupera a etapa de ordem 1 do protocolo
//				ProtocoloEtapa protocoloEtapa = listaProtocoloEtapa.get(0);
//				etapa = protocoloEtapa.getEtapa();
//
//				List<Maquina> maquinas = maquinaBO.getListaPorIdEtapa(etapa.getId());
//				if (maquinas != null && maquinas.size() > 0) {
//
//					Calendar horarioMaquina = this.calcularHorarioMaquina(etapa, dtHoraPedido, usuarioResponsavel);
//
//					//Cria a vaga, vaga Paciente e exameExecucacao para os atendimentos do MV
//					Protocolo protocolo = listaProtocoloEtapa.get(0).getProtocolo();
//
//					VagaPaciente vagaPaciente = this.vagaPacienteBO.getPorCdIntegracao(codigoIntegracao);
//
//					if (vagaPaciente == null) {
//						vagaPaciente = this.gerenciadorAgendaBO.encaixarPaciente(maquinas.get(0), etapa, horarioMaquina, ultimaVagaPaciente, protocolo, paciente, peso, altura, plano, usuarioResponsavel, false, prestadorSolicitante);
//
//					}
//
//					vagaPaciente.setCdIntegracao(codigoIntegracao);
//					vagaPaciente.setCdAtendimento(codigoAtendimento);
//
//					//cria um prestador solicitante
//					//					vagaPaciente.setMedicoSolicitante(prestadorSolicitante);
//
//					//atualiza a vagaPaciente com a informação do codigo do item do pedido do MV
//					vagaPaciente = vagaPacienteBO.salvarOuAtualizar(vagaPaciente);
//
//					return vagaPaciente;
//
//				}
//			}
//
//		}
//		return null;
//	}
//
//	protected abstract Plano salvarOuAtulizarConvenioEPlano(final ConvenioDTO convenio) throws QueryParametroInvalidoException, DAOException, TipoPlanoInexistenteException, SalvarOuAtualizarPlanoException, NomeTipoPlanoDuplicadoException;
//
//	//Obtém o registro de exame execucao que possui tipo subetapa recepcao s"ao inicializar 
//	protected ExameExecucao getExameExecucaoSubEtapaRecepcao(VagaPaciente vagaPaciente) throws DAOException, QueryParametroInvalidoException {
//
//		SubEtapa subEtapaAtual = exameExecucaoBO.getEtapaAtualPorIdVagaPaciente(vagaPaciente.getId());
//
//		if (TipoSubEtapaEnum.RECEPCAO.getId().equals(subEtapaAtual.getTipoSubEtapa().getId())) {
//
//			List<ExameExecucao> listaExameExecucao = vagaPaciente.getListaExameExecucao();
//			for (ExameExecucao ee : listaExameExecucao) {
//				if (TipoSubEtapaEnum.RECEPCAO.getId().equals(ee.getSubEtapa().getTipoSubEtapa().getId())) {
//					return ee;
//				}
//			}
//		}
//		return null;
//	}
//
//	protected void associarAutorizacaoExame(Exame exame, Plano plano, Instituicao instituicao)
//			throws QueryParametroInvalidoException, DAOException, TipoAutorizacaoInexistenteException, AutorizacaoExameDuplicadoException, SalvarOuAtualizarAutorizacaoExameException {
//		AutorizacaoExame autorizacaoExame = new AutorizacaoExame();
//		autorizacaoExame.setExame(exame);
//		autorizacaoExame.setPlano(plano);
//		autorizacaoExame.setInstituicao(instituicao);
//		TipoAutorizacao tpAutorizacao = tipoAutorizacaoBO.getPorId(TipoAutorizacaoEnum.NAO_PRECISA_DE_AUTORIZACAO.getId());
//		if (tpAutorizacao != null) {
//			autorizacaoExame.setTipoAutorizacao(tpAutorizacao);
//		}
//		List<Long> listaInstituicoes = new ArrayList<Long>();
//		listaInstituicoes.add(instituicao.getId());
//		if (!autorizacaoExameBO.existeAutorizacaoExamePorIdExameEIdPlanoEListaIdInstituicao(exame.getId(), plano.getId(), listaInstituicoes)) {
//			autorizacaoExameBO.salvarOuAtualizar(autorizacaoExame);
//		}
//	}
//
//	protected Paciente popularPaciente(PacienteDTO pacienteIntegracao) throws QueryParametroInvalidoException, DAOException, SalvarOuAtualizarPacienteException, PacienteCodigoDuplicadoException, PacienteCpfDuplicadoException {
//
//		Paciente paciente = pacienteBO.getPorCdIntegracao(pacienteIntegracao.getId());
//		if (paciente == null) {
//			paciente = new Paciente();
//		}
//		paciente.setCdIntegracao(pacienteIntegracao.getId());
//		if (paciente.getDataNascimento() == null || (pacienteIntegracao.getDataNascimento() != null && pacienteIntegracao.getDataNascimento().compareTo(paciente.getDataNascimento()) != 0)) {
//			paciente.setDataNascimento(pacienteIntegracao.getDataNascimento());
//		}
//
//		paciente.setNome(pacienteIntegracao.getNome());
//
//		if (paciente.getDataCadastro() == null) {
//			paciente.setDataCadastro(DataUtil.getCalendarUTC());
//		}
//
//		if (pacienteIntegracao.getEmail() != null && !pacienteIntegracao.getEmail().equals(paciente.getEmail())) {
//			paciente.setEmail(pacienteIntegracao.getEmail() == null ? "" : pacienteIntegracao.getEmail());
//		}
//
//		if (pacienteIntegracao.getNomeMae() != null && !pacienteIntegracao.getNomeMae().equals(paciente.getNomeMae())) {
//			paciente.setNomeMae(pacienteIntegracao.getNomeMae());
//		}
//
//		if (pacienteIntegracao.getNumeroRg() != null && !pacienteIntegracao.getNumeroRg().equals(paciente.getRg())) {
//			paciente.setRg(pacienteIntegracao.getNumeroRg());
//		}
//
//		if (pacienteIntegracao.getNumeroCpf() != null && !pacienteIntegracao.getNumeroCpf().equals(paciente.getCpf())) {
//			paciente.setCpf(pacienteIntegracao.getNumeroCpf());
//		}
//
//		paciente.setPreCadastro(Boolean.FALSE);
//
//		//Preencher dados do sexo
//		if (pacienteIntegracao.getSexo() != null) {
//			TipoSexoEnum tipoSexoEnum = TipoSexoEnum.getPorSigla(pacienteIntegracao.getSexo());
//			TipoSexo tpSexo = tipoSexoBO.getPorId(tipoSexoEnum.getId());
//			paciente.setTipoSexo(tpSexo);
//		}
//
//		paciente = this.preencherTelefones(pacienteIntegracao, paciente);
//
//		return paciente;
//	}
//
//	private Paciente preencherTelefones(PacienteDTO pacienteIntegracao, Paciente paciente) {
//		if (pacienteIntegracao.getTelefone() != null) {
//			String telefone = pacienteIntegracao.getTelefone().replaceAll("[^\\d]", "");
//			if (telefone.length() > 11) {
//				paciente.setTelefoneCelular(!telefone.substring(2).equals(paciente.getTelefoneCelular()) ? telefone.substring(2) : paciente.getTelefoneCelular());
//			} else {
//				paciente.setTelefoneCelular(!telefone.equals(paciente.getTelefoneCelular()) ? telefone : paciente.getTelefoneCelular());
//			}
//		}
//
//		return paciente;
//	}
//
//	protected Map<Long, VagaPaciente> getListaCdIntegracaoVagaPaciente(Calendar dataInicial, Calendar dataFinal) throws DAOException {
//
//		List<VagaPaciente> listaVagaPaciente = vagaPacienteBO.getListaVagaPacienteIntegracaoDoDia(dataInicial, dataFinal);
//
//		Map<Long, VagaPaciente> mapVagaPaciente = new HashMap<Long, VagaPaciente>();
//		for (VagaPaciente vagaPaciente : listaVagaPaciente) {
//			mapVagaPaciente.put(vagaPaciente.getCdIntegracao(), vagaPaciente);
//		}
//
//		return mapVagaPaciente;
//	}
//
//	public synchronized VagaPaciente processarAgendamento(VagaPacienteDTO dadosIntegracao, Usuario usuarioResponsavel, Paciente paciente, Plano plano)
//			throws QueryParametroInvalidoException, DAOException, SalvarOuAtualizarPacienteException, PacienteCodigoDuplicadoException, PacienteCpfDuplicadoException, SalvarOuAtualizarPlanoException, NomeTipoPlanoDuplicadoException,
//			TipoPlanoInexistenteException, ConversaoDataException, VagaPacienteInexistenteException, CampoInvalidoException, ExclusaoVagaPacienteInvalidaException, UsuarioInexistenteException, VagaPacienteDuplicadoException,
//			TipoLogAlteracaoInexistenteException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException, SalvarOuAtualizarVagaExameException, SalvarOuAtualizarVagaException, MaxVagaDataExcedidoException,
//			ControleVagaPacienteInexistenteException, NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException, MotivoInexistenteException, VagaInexistenteException, PacienteInexistenteException,
//			RemarcacaoVagaPacienteDuplicadoException, SalvarOuAtualizarRemarcacaoVagaPacienteException, DataNascimentoInvalidaException, EnvioEmailException, DataObitoInvalidaException, FormatoDataInvalidoException,
//			ProtocoloInexistenteException, EtapaInexistenteException, SalvarOuAtualizarMedicoException, ExecucaoExameInexistenteException, SubEtapaExameJaTerminadaException, SalvarEventoException, FalhaNoSistemaException,
//			ProtocoloEtapaInexistenteException, TipoAutorizacaoInexistenteException, AutorizacaoExameDuplicadoException, SalvarOuAtualizarAutorizacaoExameException, ExameJaCanceladoException, IntegracaoException,
//			SubEtapaInexistenteException, PrestadorSolicitanteInexistenteException, ChaveConfiguracaoInexistenteException, ValidationException, MarcacaoComMenos30DiasException, VagaHorarioInexistenteException {
//
//		log.warn("Iniciando processamento dos agendamentos");
//
//		ControleVagaPaciente ultimaVagaPaciente = controleVagaPacienteBO.getPorIdPaciente(paciente.getId());
//
//		VagaPaciente vagaPaciente = null;
//		if (ultimaVagaPaciente != null && ultimaVagaPaciente.getEtapaAgendada().getCdIntegracao().equals(dadosIntegracao.getCodigoExame())) {
//
//			ExameExecucao ee = ultimaVagaPaciente.getVagaPaciente().getListaExameExecucao().get(0);
//			if (ee != null) {
//				vagaPaciente = verificarRemarcacao(ee.getDataInicioPrevista(), dadosIntegracao, ultimaVagaPaciente.getVagaPaciente(), ultimaVagaPaciente, usuarioResponsavel, plano);
//			}
//			if (vagaPaciente == null) {
//				vagaPaciente = ultimaVagaPaciente.getVagaPaciente();
//			}
//
//		} else {
//
//			vagaPaciente = this.encaixarPaciente(dadosIntegracao, usuarioResponsavel, ultimaVagaPaciente, paciente, plano);
//			if (vagaPaciente != null) {
//				associarAutorizacaoExame(vagaPaciente.getExame(), plano, vagaPaciente.getVaga().getMaquina().getInstituicao());
//			}
//		}
//
//		log.warn("Finalizando processamento dos agendamentos");
//		return vagaPaciente;
//	}
//
//	protected boolean isReconvocao(VagaPacienteDTO dadosMV) {
//		if (StringUtils.isNotEmpty(dadosMV.getDescricaoExame())) {
//			return dadosMV.getDescricaoExame().toUpperCase().contains(RECONVOCADA) || dadosMV.getDescricaoExame().toUpperCase().contains(RECONVOCADO);
//		}
//		return false;
//	}
//
//	protected abstract VagaPaciente verificarRemarcacao(Calendar horarioInicioPrevisto, VagaPacienteDTO dadosMV, VagaPaciente ultimaVagaPaciente, ControleVagaPaciente ctrlVagaPaciente, Usuario usuarioLogado, Plano plano)
//			throws ConversaoDataException, QueryParametroInvalidoException, DAOException, VagaPacienteInexistenteException, CampoInvalidoException, ExclusaoVagaPacienteInvalidaException, UsuarioInexistenteException,
//			VagaPacienteDuplicadoException, TipoLogAlteracaoInexistenteException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException, SalvarOuAtualizarVagaExameException, SalvarOuAtualizarVagaException,
//			MaxVagaDataExcedidoException, ControleVagaPacienteInexistenteException, NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException, MotivoInexistenteException, ProtocoloEtapaInexistenteException,
//			FormatoDataInvalidoException, VagaInexistenteException, PacienteInexistenteException, RemarcacaoVagaPacienteDuplicadoException, SalvarOuAtualizarRemarcacaoVagaPacienteException, SalvarOuAtualizarPacienteException,
//			PacienteCodigoDuplicadoException, DataNascimentoInvalidaException, EnvioEmailException, PacienteCpfDuplicadoException, DataObitoInvalidaException, ProtocoloInexistenteException, EtapaInexistenteException,
//			ExameJaCanceladoException, SalvarOuAtualizarMedicoException, IntegracaoException, SubEtapaInexistenteException, PrestadorSolicitanteInexistenteException, ChaveConfiguracaoInexistenteException, ValidationException,
//			MarcacaoComMenos30DiasException;
//
//	protected PrestadorSolicitante salvarOuAtualizarPrestadorSolicitante(final PrestadorSolicitanteDTO prestador) throws DAOException, QueryParametroInvalidoException, SalvarOuAtualizarMedicoException {
//
//		PrestadorSolicitante prestadorSolicitante = null;
//
//		if (prestador == null) {
//			return prestadorSolicitante;
//		}
//
//		if (prestador.getId() == null && prestador.getNumeroConselho() != null) {
//			prestadorSolicitante = prestadorSolicitanteBO.getPorNumeroConselho(prestador.getNumeroConselho());
//		} else if (prestador.getId() == null && prestador.getNumeroConselho() == null) {
//			return prestadorSolicitante;
//		} else {
//			prestadorSolicitante = prestadorSolicitanteBO.getPorCdIntegracao(prestador.getId());
//		}
//
//		if (prestadorSolicitante == null) {
//			prestadorSolicitante = new PrestadorSolicitante();
//		}
//		prestadorSolicitante.setCdIntegracao(prestador.getId());
//		prestadorSolicitante.setNumeroConselho(prestador.getNumeroConselho());
//		prestadorSolicitante.setNome(prestador.getNome());
//		prestadorSolicitante = prestadorSolicitanteBO.salvarOuAtualizar(prestadorSolicitante);
//		return prestadorSolicitante;
//	}
//
//	public VagaPaciente remarcarPaciente(VagaPacienteDTO dadosMV, ControleVagaPaciente controleVagaPaciente, Plano plano, List<Motivo> motivos, Usuario usuarioLogado, Paciente paciente, PrestadorSolicitante prestadorSolicitante)
//			throws QueryParametroInvalidoException, DAOException, VagaPacienteInexistenteException, CampoInvalidoException, ExclusaoVagaPacienteInvalidaException, UsuarioInexistenteException, VagaPacienteDuplicadoException,
//			TipoLogAlteracaoInexistenteException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException, SalvarOuAtualizarVagaExameException, SalvarOuAtualizarVagaException, MaxVagaDataExcedidoException,
//			ConversaoDataException, ControleVagaPacienteInexistenteException, NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException, FormatoDataInvalidoException, VagaInexistenteException, PacienteInexistenteException,
//			RemarcacaoVagaPacienteDuplicadoException, SalvarOuAtualizarRemarcacaoVagaPacienteException, SalvarOuAtualizarPacienteException, PacienteCodigoDuplicadoException, DataNascimentoInvalidaException, EnvioEmailException,
//			PacienteCpfDuplicadoException, DataObitoInvalidaException, ProtocoloInexistenteException, EtapaInexistenteException, MotivoInexistenteException, SalvarOuAtualizarMedicoException, IntegracaoException,
//			ProtocoloEtapaInexistenteException, SubEtapaInexistenteException, PrestadorSolicitanteInexistenteException, ChaveConfiguracaoInexistenteException, ValidationException, MarcacaoComMenos30DiasException,
//			VagaHorarioInexistenteException {
//
//		VagaPaciente vagaPaciente = this.gerenciadorAgendaBO.desmarcarProcedimento(controleVagaPaciente.getVagaPaciente(), motivos, motivos.get(0).getDescricao(), true, usuarioLogado, usuarioLogado.getListaInstituicao().get(0));
//		vagaPaciente.setCdAtendimento(null);
//		vagaPaciente.setCdIntegracao(null);
//		vagaPacienteBO.salvarOuAtualizar(vagaPaciente);
//
//		VagaPaciente novaVagaPaciente = this.encaixarPaciente(dadosMV, usuarioLogado, controleVagaPaciente, controleVagaPaciente.getVagaPaciente().getPaciente(), plano);
//		novaVagaPaciente.setCdAtendimento(controleVagaPaciente.getVagaPaciente().getCdAtendimento());
//		vagaPacienteBO.salvarOuAtualizar(novaVagaPaciente);
//
//		RemarcacaoVagaPaciente remarcacaoVP = new RemarcacaoVagaPaciente(novaVagaPaciente, controleVagaPaciente.getVagaPaciente());
//
//		boRemarcacaoVagaPaciente.salvarOuAtualizar(remarcacaoVP);
//		return novaVagaPaciente;
//
//	}
//
//	private ProtocoloEtapa getProtocoloEtapa(final Etapa etapa, final ControleVagaPaciente ultimaVagaPaciente) throws QueryParametroInvalidoException, DAOException, ProtocoloEtapaInexistenteException {
//
//		if (ultimaVagaPaciente != null) {
//			//consulta o protocolo etapa com a próxima etapa do último protocolo
//			ProtocoloEtapa ultimoProtocoloEtapaMarcado = protocoloEtapaBO.getPorIdProtocoloIdEtapa(ultimaVagaPaciente.getProtocolo().getId(), ultimaVagaPaciente.getEtapaAgendada().getId());
//			List<ProtocoloEtapa> listaProtocoloEtapa = protocoloEtapaBO.getListaPorIdProtocoloEOrdem(ultimaVagaPaciente.getProtocolo().getId(), (ultimoProtocoloEtapaMarcado.getOrdem()).intValue() + 1);
//
//			for (ProtocoloEtapa protocoloEtapa : listaProtocoloEtapa) {
//				//obtem o próximo protocolo etapa
//				//verifica se a próxima etapa do protocolo é igual a que o esta sendo solicitada para marcar
//				if (protocoloEtapa.getEtapa().equals(etapa)) {
//					return protocoloEtapa;
//				}
//			}
//
//		} else {
//			List<ProtocoloEtapa> listaProtocoloEtapa = protocoloEtapaBO.getListaPorIdEtapa(etapa.getId());
//			if (listaProtocoloEtapa != null && !listaProtocoloEtapa.isEmpty()) {
//				return listaProtocoloEtapa.get(0);
//			}
//		}
//		return null;
//
//	}
//
//	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 600, value = "txManagerNuclearis")
//	public VagaPaciente encaixarPaciente(VagaPacienteDTO dadosIntegracao, Usuario usuarioResponsavel, ControleVagaPaciente ultimoControleVagaPaciente, Paciente paciente, Plano plano)
//			throws QueryParametroInvalidoException, DAOException, ConversaoDataException, CampoInvalidoException, HorarioVagaInvalidoException, MaquinaInexistenteException, ExameInexistenteException, SalvarOuAtualizarVagaExameException,
//			SalvarOuAtualizarVagaException, UsuarioInexistenteException, MaxVagaDataExcedidoException, NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException, VagaInexistenteException, PacienteInexistenteException,
//			VagaPacienteInexistenteException, VagaPacienteDuplicadoException, RemarcacaoVagaPacienteDuplicadoException, SalvarOuAtualizarRemarcacaoVagaPacienteException, SalvarOuAtualizarPacienteException, PacienteCodigoDuplicadoException,
//			DataNascimentoInvalidaException, EnvioEmailException, PacienteCpfDuplicadoException, DataObitoInvalidaException, FormatoDataInvalidoException, ProtocoloInexistenteException, EtapaInexistenteException,
//			SalvarOuAtualizarMedicoException, IntegracaoException, ProtocoloEtapaInexistenteException, SubEtapaInexistenteException, PrestadorSolicitanteInexistenteException, ChaveConfiguracaoInexistenteException, ValidationException,
//			ControleVagaPacienteInexistenteException, MarcacaoComMenos30DiasException, VagaHorarioInexistenteException {
//
//		final Long codigoExame = dadosIntegracao.getCodigoExame();
//		final Calendar dtHoraPedido = dadosIntegracao.getDtHoraExame();
//		final Long codigoIntegracao = dadosIntegracao.getCodSolicitacaoExame();
//		final Long codigoAtendimento = dadosIntegracao.getCodigoAtendimento();
//		final float peso = dadosIntegracao.getPesoPaciente();
//		final float altura = dadosIntegracao.getAlturaPaciente();
//		PrestadorSolicitante prestadorSolicitante = this.salvarOuAtualizarPrestadorSolicitante(dadosIntegracao.getPrestadorSolicitantePOJO());
//		List<Etapa> etapas = etapaBO.getPorCdIntegracao(codigoExame);
//		Iterator<Etapa> it = etapas.iterator();
//		if (it.hasNext()) {
//			Etapa etapa = it.next();
//			List<ProtocoloEtapa> listaProtocoloEtapa = protocoloEtapaBO.getListaPorIdEtapa(etapa.getId());
//			if (listaProtocoloEtapa != null && listaProtocoloEtapa.size() > 0) {
//				//recupera a etapa de ordem 1 do protocolo
//
//				ProtocoloEtapa protocoloEtapa = listaProtocoloEtapa.get(0);
//				etapa = protocoloEtapa.getEtapa();
//
//				//==>Próxima etapa
//				//ProtocoloEtapa protocoloEtapa = this.getProtocoloEtapa(etapa, ultimoControleVagaPaciente);
//				//etapa = protocoloEtapa.getEtapa();
//
//				List<Maquina> maquinas = maquinaBO.getListaPorIdEtapa(etapa.getId());
//				if (maquinas != null && maquinas.size() > 0) {
//
//					Calendar horarioMaquina = this.calcularHorarioMaquina(etapa, dtHoraPedido, usuarioResponsavel);
//
//					//Cria a vaga, vaga Paciente e exameExecucacao para os atendimentos do MV
//					Protocolo protocolo = listaProtocoloEtapa.get(0).getProtocolo();
//
//					VagaPaciente vagaPaciente = this.vagaPacienteBO.getPorCdIntegracao(codigoIntegracao);
//
//					if (vagaPaciente == null) {
//						vagaPaciente = this.gerenciadorAgendaBO.encaixarPaciente(maquinas.get(0), etapa, horarioMaquina, ultimoControleVagaPaciente, protocolo, paciente, peso, altura, plano, usuarioResponsavel, false, prestadorSolicitante);
//					}
//
//					vagaPaciente.setCdIntegracao(codigoIntegracao);
//					vagaPaciente.setCdAtendimento(codigoAtendimento);
//
//					//cria um prestador solicitante
//					vagaPaciente.setMedicoSolicitante(prestadorSolicitante);
//
//					//atualiza a vagaPaciente com a informação do codigo do item do pedido do MV
//					vagaPaciente = vagaPacienteBO.salvarOuAtualizar(vagaPaciente);
//
//					return vagaPaciente;
//
//				}
//			}
//
//		}
//		log.warn("O código da etapa não foi cadastrado. Associe o código do exame à etapa para que os exames possam ser integrados.");
//		return null;
//	}
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
//	 */
//	private Calendar calcularHorarioMaquina(Etapa etapa, Calendar dataRecepcao, Usuario usuario) throws ConversaoDataException, DAOException, QueryParametroInvalidoException {
//		Long tempoDecorrido = 0L;
//		boolean subEtapaRefAgendaEcontrada = false;
//		Calendar dataRecepcaoUTC = DataUtil.convertDateWithTimezoneToCalendarUTC(dataRecepcao.getTime(), usuario.getTimezone().getNome());
//		List<SubEtapa> listaSubEtapa = subEtapaBO.getListaPorIdEtapa(etapa.getId());
//		// -> Itera as etapas ate encontrar a primeira etapa que exigeAgenda (etapa ref agenda) acumulando o tempo decorrido.
//		Iterator<SubEtapa> iter = listaSubEtapa.iterator();
//		while (!subEtapaRefAgendaEcontrada && iter.hasNext()) {
//
//			SubEtapa subEtapa = iter.next();
//
//			// -> Caso a etapa exija agenda seja como true para parar o while e acrescenta apenas o intervalo.
//			if (subEtapa.getTipoSubEtapa().getId().equals(TipoSubEtapaEnum.IMAGEM.getId())) {
//
//				subEtapaRefAgendaEcontrada = true;
//				tempoDecorrido += subEtapa.getIntervalo().getTimeInMillis();
//
//			} else {
//				// -> Caso contrario acrescenta o tempo total da etapa (intervalo + duracao)
//				tempoDecorrido += subEtapa.getIntervalo().getTimeInMillis() + subEtapa.getDuracao().getTimeInMillis();
//
//			}
//
//		}
//
//		// -> Define a Data da primeira etapa do exame, antes mesmo do intervalo.
//		Calendar dataImagem = DataUtil.getCalendarUTC();
//		dataImagem.setTimeInMillis(dataRecepcaoUTC.getTimeInMillis() + tempoDecorrido);
//		return dataImagem;
//	}
//
//	protected VagaPaciente remarcarProcedimento(VagaPacienteDTO dadosMV, ControleVagaPaciente controleVagaPaciente, Plano plano, List<Motivo> motivos, Usuario usuarioLogado) throws QueryParametroInvalidoException, DAOException,
//			VagaPacienteInexistenteException, CampoInvalidoException, ExclusaoVagaPacienteInvalidaException, UsuarioInexistenteException, VagaPacienteDuplicadoException, TipoLogAlteracaoInexistenteException, HorarioVagaInvalidoException,
//			MaquinaInexistenteException, ExameInexistenteException, SalvarOuAtualizarVagaExameException, SalvarOuAtualizarVagaException, MaxVagaDataExcedidoException, ConversaoDataException, ControleVagaPacienteInexistenteException,
//			NotificacaoInexistenteException, SalvarOuAtualizarNotificacaoException, FormatoDataInvalidoException, VagaInexistenteException, PacienteInexistenteException, RemarcacaoVagaPacienteDuplicadoException,
//			SalvarOuAtualizarRemarcacaoVagaPacienteException, SalvarOuAtualizarPacienteException, PacienteCodigoDuplicadoException, DataNascimentoInvalidaException, EnvioEmailException, PacienteCpfDuplicadoException,
//			DataObitoInvalidaException, ProtocoloInexistenteException, EtapaInexistenteException, MotivoInexistenteException, SalvarOuAtualizarMedicoException, IntegracaoException, ProtocoloEtapaInexistenteException,
//			SubEtapaInexistenteException, PrestadorSolicitanteInexistenteException, ChaveConfiguracaoInexistenteException, ValidationException, MarcacaoComMenos30DiasException, VagaHorarioInexistenteException {
//
//		VagaPaciente vagaPaciente = this.gerenciadorAgendaBO.desmarcarProcedimento(controleVagaPaciente.getVagaPaciente(), motivos, motivos.get(0).getDescricao(), true, usuarioLogado, usuarioLogado.getListaInstituicao().get(0));
//		vagaPaciente.setCdAtendimento(null);
//		vagaPaciente.setCdIntegracao(null);
//		vagaPacienteBO.salvarOuAtualizar(vagaPaciente);
//
//		//Porque está retornando null
//		VagaPaciente novaVagaPaciente = this.encaixarPaciente(dadosMV, usuarioLogado, controleVagaPaciente, controleVagaPaciente.getVagaPaciente().getPaciente(), plano);
//		if (novaVagaPaciente != null && controleVagaPaciente.getVagaPaciente() != null) {
//			novaVagaPaciente.setCdAtendimento(controleVagaPaciente.getVagaPaciente().getCdAtendimento());
//			vagaPacienteBO.salvarOuAtualizar(novaVagaPaciente);
//
//			RemarcacaoVagaPaciente remarcacaoVP = new RemarcacaoVagaPaciente(novaVagaPaciente, controleVagaPaciente.getVagaPaciente());
//
//			boRemarcacaoVagaPaciente.salvarOuAtualizar(remarcacaoVP);
//			return novaVagaPaciente;
//		}
//		return null;
//
//	}
//
//	//Seta a próxima subetada caso a subetapa atual seja a de Recepção 
//	protected void atenderRecepcao(Usuario usuarioResponsavel, VagaPaciente vagaPaciente, Calendar horarioRecepcao, PrestadorSolicitante prestadorSolicitante, ExameExecucao ee, Boolean adicionarNota)
//			throws CampoInvalidoException, QueryParametroInvalidoException, DAOException, ExecucaoExameInexistenteException, SubEtapaExameJaTerminadaException, VagaPacienteInexistenteException, VagaPacienteDuplicadoException {
//		ee.setDataInicio(horarioRecepcao);
//		if (adicionarNota) {
//			if (ee.getListaNota() == null || ee.getListaNota().isEmpty()) {
//				ee.setListaNota(new ArrayList<Nota>());
//			}
//			ee.getListaNota().add(new Nota("Paciente Internado", ee, usuarioResponsavel, DataUtil.getCalendarUTC()));
//		}
//		//Adicionado 1 minuto para setar a data final da sub etapa de recepção
//		Calendar horarioRecepcaoFinal = Calendar.getInstance();
//		horarioRecepcaoFinal.setTime(horarioRecepcao.getTime());
//		horarioRecepcaoFinal.add(Calendar.MINUTE, 1);
//		ee.setUsuarioResponsavel(usuarioResponsavel);
//		gerenciadorExameExecucaoBO.terminarExecucaoEtapa(usuarioResponsavel, ee, horarioRecepcaoFinal, true, true);
//
//		//cria um prestador solicitante
//		vagaPaciente.setMedicoSolicitante(prestadorSolicitante);
//		vagaPacienteBO.salvarOuAtualizar(vagaPaciente);
//	}
//
//	//Caso um paciente tenha cancelado seu atendimento, mesmo após ter passado pela recepção, este método será responsável de atualizar o map das vagas pacientes sem que ele venha nesse metodo, para que ele seja cancelado no metodo verificarCancelamentos
//	protected Map<Long, VagaPaciente> atualizarMapVagaPaciente(Map<Long, VagaPaciente> mapVagaPaciente, List<VagaPacienteDTO> listaDadosIntegracao) {
//		Map<Long, VagaPaciente> novoMap = new LinkedHashMap<Long, VagaPaciente>();
//		for (Long cdIntegracao : mapVagaPaciente.keySet()) {
//			for (VagaPacienteDTO dados : listaDadosIntegracao) {
//				if (dados.getCodSolicitacaoExame().equals(cdIntegracao)) {
//					novoMap.put(cdIntegracao, mapVagaPaciente.get(cdIntegracao));
//				}
//			}
//		}
//		return novoMap;
//
//	}
//
//	protected void atenderPaciente(VagaPacienteDTO dadosIntegracao, Usuario usuarioResponsavel, VagaPaciente vagaPaciente, Calendar horarioRecepcao, PrestadorSolicitante prestadorSolicitante, Boolean adicionarNota)
//			throws DAOException, QueryParametroInvalidoException, TipoLogAlteracaoInexistenteException, CampoInvalidoException, ExecucaoExameInexistenteException, SubEtapaExameJaTerminadaException, NoSuchAlgorithmException,
//			AutorizacaoStatusInexistenteException, VagaPacienteInexistenteException, VagaPacienteDuplicadoException, UsuarioInexistenteException, ConversaoDataException, IOException, NotaInexistenteException, SalvarOuAtualizarNotaException,
//			SalvarOuAtualizarMedicoException {
//
//		ExameExecucao ee = this.getExameExecucaoSubEtapaRecepcao(vagaPaciente);
//		//setado o horário de maquina como sendo o horário de início da sub etapa de recepção
//		if (dadosIntegracao.getCodigoAtendimento() != null && ee != null) {
//			atenderRecepcao(usuarioResponsavel, vagaPaciente, horarioRecepcao, prestadorSolicitante, ee, adicionarNota);
//		}
//
//	}
//
//}