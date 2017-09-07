package br.com.nuclearis.integracao.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.type.CalendarType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.nuclearis.integracao.dao.interfaces.IIntegracaoMVDAO;
import br.com.nuclearis.integracao.dto.AutorizacaoExameDTO;
import br.com.nuclearis.integracao.dto.ConvenioDTO;
import br.com.nuclearis.integracao.dto.EnderecoDTO;
import br.com.nuclearis.integracao.dto.IntegracaoDTO;
import br.com.nuclearis.integracao.dto.PacienteDTO;
import br.com.nuclearis.integracao.dto.PlanoDTO;
import br.com.nuclearis.integracao.dto.VagaPacienteDTO;
import br.com.nuclearis.integracao.exceptions.DAOException;
import br.com.nuclearis.integracao.util.DataUtil;
import br.com.nuclearis.integracao.util.PropertyUtil;

@Repository("bdIntegracaoMVDAO")
@Scope("prototype")
public class BDIntegracaoMVDAO extends DAONuclearisInt<IntegracaoDTO> implements IIntegracaoMVDAO {

	//TODO está fixo apenas porque vamos começar a integrar somente com o PET-CT. Este parametro será retirado depois. O código 533 refere-se ao SPECT-Cerebral, mas o HSR trata este exame como PET
	//	private final List<String> listaCodigosExame = Arrays.asList(new String[] { "510", "511", "512" });

	public BDIntegracaoMVDAO() {
		super(IntegracaoDTO.class);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
	public List<ConvenioDTO> consultarConveniosPorExame(List<Long> codigosEtapa) throws DAOException {
		StringBuilder strQuery = new StringBuilder(" SELECT ");
		strQuery.append(" DISTINCT ");
		strQuery.append(" COD_CONVENIO_MV CD_CONVENIO, ");
		strQuery.append(" NM_CONVENIO NM_CONVENIO");
		strQuery.append(" FROM ");
		//TODO Alterar para que o nome da tabela seja capturado pelo banco
		//		strQuery.append("  		DBAHSR.VDIC_PROIBICOES_CONVENIO");
		strQuery.append(PropertyUtil.getViewConvenios());
		strQuery.append(" WHERE ");
		strQuery.append(" 	 COD_EXA_MV in (:codigoExame)");

		Query query = criarConsultaSQL(strQuery.toString()).addScalar("CD_CONVENIO", LongType.INSTANCE).addScalar("NM_CONVENIO", StringType.INSTANCE);

		query.setParameterList("codigoExame", codigosEtapa);

		List<Object> result = query.list();

		List<ConvenioDTO> convenios = new ArrayList<ConvenioDTO>();
		for (Object object : result) {
			Object[] obj = (Object[]) object;
			int i = 0;
			ConvenioDTO convenio = new ConvenioDTO();
			convenio.setIdConvenio((Long) obj[i++]);
			Object valor = obj[i++];
			convenio.setNomeConvenio(valor != null ? String.valueOf(valor) : null);
			convenios.add(convenio);
		}

		return convenios;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
	public List<PlanoDTO> consultarPlanosPorConvenio(Long codigoConvenio, List<Long> codigosEtapa) throws DAOException {
		StringBuilder strQuery = new StringBuilder(" SELECT ");
		strQuery.append(" DISTINCT ");
		strQuery.append(" COD_CONVENIO_MV COD_CONVENIO, ");
		strQuery.append(" COD_PLANO_MV COD_PLANO ");
		strQuery.append(" ,DS_CON_PLA DESC_PLANO");
		strQuery.append(" FROM ");
		//TODO Alterar para que o nome da tabela seja capturado pelo banco
		//		strQuery.append("  		DBAHSR.VDIC_PROIBICOES_CONVENIO");
		strQuery.append(PropertyUtil.getViewPlanos());
		strQuery.append(" WHERE ");
		strQuery.append(" 	 COD_CONVENIO_MV = :codigoConvenio ");
		strQuery.append(" 	 AND COD_EXA_MV IN (:codigoExame) ");

		Query query = criarConsultaSQL(strQuery.toString()).addScalar("COD_CONVENIO", LongType.INSTANCE).addScalar("COD_PLANO", LongType.INSTANCE).addScalar("DESC_PLANO", StringType.INSTANCE);

		query.setParameter("codigoConvenio", codigoConvenio);
		query.setParameterList("codigoExame", codigosEtapa);

		List<Object> result = query.list();

		List<PlanoDTO> planos = new ArrayList<PlanoDTO>();
		for (Object object : result) {
			Object[] obj = (Object[]) object;
			int i = 0;
			Object valor = obj[i++];
			ConvenioDTO convenio = new ConvenioDTO();
			convenio.setIdConvenio((Long) valor);
			PlanoDTO plano = new PlanoDTO();
			valor = obj[i++];
			plano.setIdPlano((Long) valor);
			valor = obj[i++];
			plano.setNomePlano(valor != null ? String.valueOf(valor) : null);
			plano.setConvenio(convenio);
			planos.add(plano);
		}

		return planos;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
	public List<AutorizacaoExameDTO> consultarAutorizacaoPlano(Long idPlano, Long idConvenio, List<Long> codigosEtapa) throws DAOException {
		StringBuilder strQuery = new StringBuilder(" SELECT ");
		strQuery.append(" 	COD_EXA_MV CD_EXAME,");
		strQuery.append(" 	DS_EXAME NOME_EXAME, ");
		strQuery.append(" 	TIPO_ATENDIMENTO TP_ATENDIMENTO, ");
		strQuery.append(" 	COD_CONVENIO_MV CD_CONVENIO, ");
		strQuery.append(" 	NM_CONVENIO NOME_CONVENIO, ");
		strQuery.append(" 	COD_PLANO_MV CD_PLANO, ");
		strQuery.append(" 	DS_CON_PLA NOME_PLANO, ");
		strQuery.append(" 	CASE ABR_TIPO_PROIBICAO ");
		strQuery.append(" 			WHEN  'AG' THEN 'SETOR' ");
		strQuery.append(" 			WHEN  'FC' THEN 'SN' ");
		strQuery.append(" 			WHEN  'NA' THEN 'NA' END PROIBICAO, ");
		strQuery.append(" 	TIPO_PROIBICAO TP_PROIBICAO, ");
		strQuery.append(" 	MAX(INICIO_VIGENCIA) VIGENCIA ");
		strQuery.append(" FROM ");
		//TODO Alterar para que o nome da tabela seja capturado pelo banco
		//		strQuery.append("  		DBAHSR.VDIC_PROIBICOES_CONVENIO");
		strQuery.append(PropertyUtil.getViewProibicoes());
		strQuery.append(" WHERE ");
		strQuery.append(" 	 COD_CONVENIO_MV = :codigoConvenio ");
		strQuery.append(" 	 AND COD_PLANO_MV = :codigoPlano ");
		strQuery.append(" 	 AND COD_EXA_MV IN (:codigoExame) ");
		strQuery.append(" GROUP BY");
		strQuery.append(" 	  COD_EXA_MV,");
		strQuery.append(" 	  DS_EXAME,");
		strQuery.append(" 	  TIPO_ATENDIMENTO,");
		strQuery.append(" 	  COD_CONVENIO_MV,");
		strQuery.append(" 	  NM_CONVENIO,");
		strQuery.append(" 	  COD_PLANO_MV,");
		strQuery.append(" 	  DS_CON_PLA,");
		strQuery.append(" 	  ABR_TIPO_PROIBICAO,");
		strQuery.append(" 	  TIPO_PROIBICAO,");
		strQuery.append(" 	  INICIO_VIGENCIA");

		Query query = criarConsultaSQL(strQuery.toString()).addScalar("CD_EXAME", LongType.INSTANCE).addScalar("NOME_EXAME", StringType.INSTANCE).addScalar("TP_ATENDIMENTO", StringType.INSTANCE).addScalar("CD_CONVENIO", LongType.INSTANCE)
				.addScalar("NOME_CONVENIO", StringType.INSTANCE).addScalar("CD_PLANO", LongType.INSTANCE).addScalar("NOME_PLANO", StringType.INSTANCE).addScalar("PROIBICAO", StringType.INSTANCE)
				.addScalar("TP_PROIBICAO", StringType.INSTANCE).addScalar("VIGENCIA", CalendarType.INSTANCE);

		query.setParameter("codigoConvenio", idConvenio);
		query.setParameter("codigoPlano", idPlano);
		query.setParameterList("codigoExame", codigosEtapa);

		List<Object> result = query.list();

		List<AutorizacaoExameDTO> autorizacaoes = new ArrayList<AutorizacaoExameDTO>();
		for (Object object : result) {
			Object[] obj = (Object[]) object;
			int i = 0;
			Object valor = obj[i++];
			AutorizacaoExameDTO autorizacaoExame = new AutorizacaoExameDTO();
			autorizacaoExame.setCodigoExame((Long) valor);
			valor = obj[i++];
			autorizacaoExame.setNomeExame((String) valor);
			valor = obj[i++];
			autorizacaoExame.setTipoAtendimento((String) valor);
			valor = obj[i++];
			ConvenioDTO convenio = new ConvenioDTO();
			convenio.setIdConvenio((Long) valor);
			valor = obj[i++];
			convenio.setNomeConvenio((String) valor);
			PlanoDTO plano = new PlanoDTO();
			valor = obj[i++];
			plano.setIdPlano((Long) valor);
			valor = obj[i++];
			plano.setNomePlano(valor != null ? String.valueOf(valor) : null);
			convenio.setPlano(plano);
			autorizacaoExame.setConvenio(convenio);
			valor = obj[i++];
			autorizacaoExame.setSiglaTipoProibicao(String.valueOf(valor));
			valor = obj[i++];
			autorizacaoExame.setDescTipoProibicao(String.valueOf(valor));
			valor = obj[i++];
			if (valor != null) {
				autorizacaoExame.setVigencia((Calendar) valor);
			}

			autorizacaoes.add(autorizacaoExame);
		}

		return autorizacaoes;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
	public Long consultarCondigoIntegracaoDaVaga(Calendar dataHorarioAgendamento) throws DAOException {
		StringBuilder strQuery = new StringBuilder(" SELECT ");
		strQuery.append(" 	CD_IT_AGENDA_CENTRAL CD_VAGA ");
		strQuery.append(" FROM ");
		//TODO Alterar para que o nome da tabela seja capturado pelo banco
		//		strQuery.append("  		DBAHSR.VDIC_IT_AGENDA_CENTRAL_MN");
		strQuery.append(PropertyUtil.getViewVagas());
		strQuery.append(" WHERE ");
		strQuery.append(" 	 HR_AGENDA between :dataHorarioAgendamento and :dataHorarioAgendamentoFinal");
		strQuery.append(" ORDER BY HR_AGENDA ");

		Query query = criarConsultaSQL(strQuery.toString()).addScalar("CD_VAGA", LongType.INSTANCE);

		query.setParameter("dataHorarioAgendamento", dataHorarioAgendamento);
		query.setParameter("dataHorarioAgendamentoFinal", DataUtil.setHoraFimDia(dataHorarioAgendamento.getTime(), "UTC"));
		query.setMaxResults(1);
		Object result = query.uniqueResult();

		if (result != null) {
			return (Long) result;
		}
		return BigDecimal.ZERO.longValue();
	}

	//	@Override
	//	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
	//	public Long quantidadeTotalPacientes() throws DAOException {
	//		StringBuilder strQuery = new StringBuilder(" SELECT ");
	//		strQuery.append(" 	COUNT(CD_PACIENTE) QUANTIDADE ");
	//		strQuery.append(" FROM ");
	//		strQuery.append("  DBAHSR.VDIC_PACIENTE_MN ");
	//
	//		Query query = criarConsultaSQL(strQuery.toString()).addScalar("QUANTIDADE", LongType.INSTANCE);
	//
	//		return (Long) query.uniqueResult();
	//	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
	public List<PacienteDTO> consultarPacientes(String nome) throws DAOException {
		StringBuilder strQuery = new StringBuilder(" SELECT ");
		strQuery.append(" 	CD_PACIENTE CD_PACIENTE, ");
		strQuery.append(" 	NM_PACIENTE NOME_PACIENTE, ");
		strQuery.append(" 	DT_NASCIMENTO DT_NASCIMENTO, ");
		strQuery.append(" 	SEXO SEXO, ");
		strQuery.append(" 	EMAIL EMAIL, ");
		strQuery.append(" 	NM_MAE NOME_MAE, ");
		strQuery.append(" 	NR_IDENTIDADE IDENTIDADE, ");
		strQuery.append(" 	DS_OM_IDENTIDADE ORGAO_EMISSOR, ");
		strQuery.append(" 	UF_IDENTIDADE UF_IDENTIDADE, ");
		//				strQuery.append(" 	NR_DDD_FONE DDD_FONE, ");
		strQuery.append(" 	NR_FONE TELEFONE, ");
		//		strQuery.append(" 	NR_CELULAR CELULAR, ");
		strQuery.append(" 	NR_CPF CPF, ");
		strQuery.append(" 	DS_ENDERECO ENDERECO, ");
		strQuery.append(" 	NM_CIDADE CIDADE, ");
		strQuery.append(" 	NM_UF UF, ");
		strQuery.append(" 	NR_CEP CEP ");
		strQuery.append(" FROM ");
		//TODO Alterar para que o nome da tabela seja capturado pelo banco
		//		strQuery.append("  DBAHSR.VDIC_PACIENTE_MN ");
		strQuery.append(PropertyUtil.getViewPacientes());
		strQuery.append(" WHERE ");
		strQuery.append(" 	 SEXO IS NOT NULL AND SEXO <> 'INDEFINIDO'");

		if (nome != null) {
			strQuery.append(" AND ( UPPER(NM_PACIENTE) LIKE UPPER(:nomePacienteAll) ");
			strQuery.append(" OR    SOUNDEX(UPPER(NM_PACIENTE)) = SOUNDEX(upper(:nomePaciente))) ");
			//			strQuery.append(" AND UPPER(NM_PACIENTE) LIKE upper(:nomePacienteAll) ");
			//			strQuery.append(" ORDER BY NM_PACIENTE ");
		}

		Query query = criarConsultaSQL(strQuery.toString()).addScalar("CD_PACIENTE", LongType.INSTANCE).addScalar("NOME_PACIENTE", StringType.INSTANCE).addScalar("DT_NASCIMENTO", CalendarType.INSTANCE).addScalar("SEXO", StringType.INSTANCE)
				.addScalar("EMAIL", StringType.INSTANCE).addScalar("NOME_MAE", StringType.INSTANCE).addScalar("IDENTIDADE", StringType.INSTANCE).addScalar("ORGAO_EMISSOR", StringType.INSTANCE).addScalar("UF_IDENTIDADE", StringType.INSTANCE)
				.addScalar("TELEFONE", StringType.INSTANCE).addScalar("CPF", StringType.INSTANCE).addScalar("ENDERECO", StringType.INSTANCE).addScalar("CIDADE", StringType.INSTANCE).addScalar("UF", StringType.INSTANCE)
				.addScalar("CEP", StringType.INSTANCE);

		if (nome != null) {
			query.setParameter("nomePacienteAll", "%" + nome + "%");
			query.setParameter("nomePaciente", nome);
		}
		query.setMaxResults(20);
		List<Object> result = query.list();

		return propularPaciente(result);
	}
	//	@Override
	//	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
	//	public List<PacienteDTO> consultarPacientes(String nome, int posicaoInicial, int posicaoFinal) throws DAOException {
	//		StringBuilder strQuery = new StringBuilder(" SELECT ");
	//		strQuery.append(" 	CD_PACIENTE CD_PACIENTE, ");
	//		strQuery.append(" 	NM_PACIENTE NOME_PACIENTE, ");
	//		strQuery.append(" 	DT_NASCIMENTO DT_NASCIMENTO, ");
	//		strQuery.append(" 	SEXO SEXO, ");
	//		strQuery.append(" 	EMAIL EMAIL, ");
	//		strQuery.append(" 	NM_MAE NOME_MAE, ");
	//		strQuery.append(" 	NR_IDENTIDADE IDENTIDADE, ");
	//		strQuery.append(" 	DS_OM_IDENTIDADE ORGAO_EMISSOR, ");
	//		strQuery.append(" 	UF_IDENTIDADE UF_IDENTIDADE, ");
	//		//				strQuery.append(" 	NR_DDD_FONE DDD_FONE, ");
	//		strQuery.append(" 	NR_FONE TELEFONE, ");
	//		//		strQuery.append(" 	NR_CELULAR CELULAR, ");
	//		strQuery.append(" 	NR_CPF CPF, ");
	//		strQuery.append(" 	DS_ENDERECO ENDERECO, ");
	//		strQuery.append(" 	NM_CIDADE CIDADE, ");
	//		strQuery.append(" 	NM_UF UF, ");
	//		strQuery.append(" 	NR_CEP CEP ");
	//		strQuery.append(" FROM ");
	//		//TODO Alterar para que o nome da tabela seja capturado pelo banco
	//		strQuery.append("  DBAHSR.VDIC_PACIENTE_MN ");
	//		strQuery.append(" WHERE ");
	//		strQuery.append(" 	 SEXO IS NOT NULL AND SEXO <> 'INDEFINIDO'");
	//		
	//		if (nome != null) {
	//			strQuery.append(" AND UPPER(NM_PACIENTE) LIKE upper(:nomePaciente) ");
	//			strQuery.append(" ORDER BY NM_PACIENTE ");
	//		}
	//		
	//		Query query = criarConsultaSQL(strQuery.toString()).addScalar("CD_PACIENTE", LongType.INSTANCE).addScalar("NOME_PACIENTE", StringType.INSTANCE).addScalar("DT_NASCIMENTO", CalendarType.INSTANCE).addScalar("SEXO", StringType.INSTANCE)
	//				.addScalar("EMAIL", StringType.INSTANCE).addScalar("NOME_MAE", StringType.INSTANCE).addScalar("IDENTIDADE", StringType.INSTANCE).addScalar("ORGAO_EMISSOR", StringType.INSTANCE).addScalar("UF_IDENTIDADE", StringType.INSTANCE)
	//				.addScalar("TELEFONE", StringType.INSTANCE).addScalar("CPF", StringType.INSTANCE).addScalar("ENDERECO", StringType.INSTANCE).addScalar("CIDADE", StringType.INSTANCE).addScalar("UF", StringType.INSTANCE)
	//				.addScalar("CEP", StringType.INSTANCE);
	//		
	//		if (nome != null) {
	//			query.setParameter("nomePaciente", "%" + nome.toUpperCase() + "%");
	//		}
	//		query.setFirstResult(posicaoInicial);
	//		query.setMaxResults(posicaoFinal);
	//		List<Object> result = query.list();
	//		
	//		return propularPaciente(result);
	//	}

	private List<PacienteDTO> propularPaciente(List<Object> result) {
		List<PacienteDTO> pacientes = new ArrayList<PacienteDTO>();
		for (Object object : result) {
			Object[] obj = (Object[]) object;
			int i = 0;
			Object valor = obj[i++];
			PacienteDTO paciente = new PacienteDTO();
			paciente.setCdIntegracao((Long) valor);
			valor = obj[i++];
			paciente.setNome(valor != null ? String.valueOf(valor) : null);
			valor = obj[i++];
			if (valor != null) {
				//				calendar.setTimeInMillis(((Timestamp) valor).getTime());
				paciente.setDataNascimento((Calendar) valor);
			}
			valor = obj[i++];
			paciente.setSexo(valor != null ? String.valueOf(valor) : null);
			valor = obj[i++];
			paciente.setEmail(valor != null ? String.valueOf(valor) : null);
			valor = obj[i++];
			paciente.setNomeMae(valor != null ? String.valueOf(valor) : null);
			valor = obj[i++];
			paciente.setNumeroRg(valor != null ? String.valueOf(valor) : null);
			valor = obj[i++];
			paciente.setEmissorRg(valor != null ? String.valueOf(valor) : null);
			valor = obj[i++];
			paciente.setUfRg(valor != null ? String.valueOf(valor) : null);
			valor = obj[i++];
			paciente.setTelefone(valor != null ? String.valueOf(valor) : null);
			valor = obj[i++];
			paciente.setNumeroCpf(valor != null ? String.valueOf(valor) : null);
			EnderecoDTO endereco = new EnderecoDTO();
			valor = obj[i++];
			endereco.setLogradouro(valor != null ? String.valueOf(valor) : null);
			valor = obj[i++];
			endereco.setCidade(valor != null ? String.valueOf(valor) : null);
			valor = obj[i++];
			endereco.setUf(valor != null ? String.valueOf(valor) : null);
			valor = obj[i++];
			endereco.setCep(valor != null ? String.valueOf(valor) : null);
			paciente.setEndereco(endereco);
			pacientes.add(paciente);
		}
		return pacientes;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
	public void inserirAgendamento(VagaPacienteDTO vagaPaciente, String tipoAcao) throws DAOException {
		StringBuilder strQuery = new StringBuilder(" INSERT INTO ");
		//TODO Alterar para que o nome da tabela seja capturado pelo banco
		//		strQuery.append("  DBAHSR.INTEGRA_ENTRADA_AGENDAMENTO ");
		strQuery.append(PropertyUtil.getTabelaAgendamentos());
		strQuery.append(" 	(CD_INTEGRA_ENTRADA_AGENDAMENTO, DT_INTEGRACAO, TP_MOVIMENTO, TP_REGISTRO, ");
		strQuery.append(" 	CD_MULTI_EMPRESA, CD_SETOR,CD_CONVENIO, DS_CONVENIO, ");
		strQuery.append(" 	CD_AGENDAMENTO, COD_EXA_MV,  ");
		if (vagaPaciente.getPaciente().getCdIntegracao() != null) {
			strQuery.append(" 	CD_PACIENTE,   ");
		}
		strQuery.append(" 	CD_PACIENTE_INTEGRA,  ");
		strQuery.append(" 	NM_PACIENTE, DT_NASCIMENTO, CD_PLANO, DS_PLANO, NM_MAE, NR_CNS, ");
		strQuery.append(" 	NM_USUARIO_SOLICITANTE,TP_ACAO_AGENDA,TP_STATUS, TP_SEXO, ");
		if (StringUtils.isNotEmpty(vagaPaciente.getPaciente().getTelefone())) {
			strQuery.append(" NR_DDD_TELEFONE, NR_TELEFONE ");
		}
		strQuery.append(" ) VALUES ( ");
		strQuery.append(" 	:cdIntegraEntrada,:dtIntegracao,:tipoMovimento, :tipoRegistro,:cdMultiEmpresa,");
		strQuery.append(" 	:cdSetor,:cdConvenio,:dsConvenio,");
		strQuery.append(" 	:cdItemAgendamento,:cdExame,");
		if (vagaPaciente.getPaciente().getCdIntegracao() != null) {
			strQuery.append(" 	:cdPaciente,");
		}
		strQuery.append(" 	:cdPacienteNuclearis,:nomePaciente,:dataNascimentoPaciente,");
		strQuery.append(" 	:cdPlano,:dsPlano,:nomeMae,:nomeCarteiraCNS,");
		strQuery.append(" 	:nmUsuarioAgendado, :tpAcao, :tpStatus, :sexo, ");
		if (StringUtils.isNotEmpty(vagaPaciente.getPaciente().getTelefone())) {
			strQuery.append(" :dddTelefone, :telefone ");
		}
		strQuery.append(" 	) ");

		Query query = criarConsultaSQL(strQuery.toString());

		query.setParameter("cdIntegraEntrada", this.getCdIntegraEntrada());
		query.setParameter("dtIntegracao", Calendar.getInstance());

		//TODO verificar se os tipos movimentos para os casos de remarcação e cancelamento
		query.setParameter("tipoMovimento", /*this.getTipoMovimento(vagaPaciente)*/"E");
		query.setParameter("tipoRegistro", 2);
		query.setParameter("cdMultiEmpresa", 1);
		query.setParameter("cdSetor", 121);
		query.setParameter("cdConvenio", vagaPaciente.getConvenio().getIdConvenio());
		query.setParameter("dsConvenio", vagaPaciente.getConvenio().getNomeConvenio());
		query.setParameter("cdItemAgendamento", vagaPaciente.getCodigoAgendamento());
		query.setParameter("cdExame", vagaPaciente.getCodigoExame());
		if (vagaPaciente.getPaciente().getCdIntegracao() != null) {
			query.setParameter("cdPaciente", vagaPaciente.getPaciente().getCdIntegracao());
		}
		query.setParameter("cdPacienteNuclearis", vagaPaciente.getPaciente().getId());
		query.setParameter("nomePaciente", vagaPaciente.getPaciente().getNome());
		query.setParameter("dataNascimentoPaciente", vagaPaciente.getPaciente().getDataNascimento().getTime());
		query.setParameter("cdPlano", vagaPaciente.getConvenio().getPlano().getIdPlano());
		query.setParameter("dsPlano", vagaPaciente.getConvenio().getPlano().getNomePlano());
		query.setParameter("nomeMae", vagaPaciente.getPaciente().getNomeMae());
		query.setParameter("nomeCarteiraCNS", vagaPaciente.getPaciente().getNumeroCNS());
		//		query.setParameter("dtValidadeCNS", vagaPaciente.getPaciente().getValidadeCNS().getTime());
		query.setParameter("nmUsuarioAgendado", vagaPaciente.getUsuarioResponsavel());
		query.setParameter("tpAcao", tipoAcao);
		query.setParameter("tpStatus", 'A');
		if (StringUtils.isNotEmpty(vagaPaciente.getPaciente().getTelefone())) {

			if (vagaPaciente.getPaciente().getTelefone().length() <= 9) {
				query.setParameter("dddTelefone", null);
				query.setParameter("telefone", vagaPaciente.getPaciente().getTelefone().substring(0, vagaPaciente.getPaciente().getTelefone().length()));
			} else {
				query.setParameter("dddTelefone", vagaPaciente.getPaciente().getTelefone().substring(0, 2));
				query.setParameter("telefone", vagaPaciente.getPaciente().getTelefone().substring(2, vagaPaciente.getPaciente().getTelefone().length() == 10 ? 10 : 11));
			}
		}
		query.setParameter("sexo", vagaPaciente.getPaciente().getSexo());
		query.executeUpdate();

	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
	private Long getCdIntegraEntrada() throws DAOException {
		StringBuilder strQuery = new StringBuilder(" SELECT ");
		strQuery.append(" 	max(CD_INTEGRA_ENTRADA_AGENDAMENTO) + 1 CD_ENTRADA_AGENDAMENTO");
		strQuery.append(" FROM ");
		//TODO Alterar para que o nome da tabela seja capturado pelo banco
		//		strQuery.append("  DBAHSR.INTEGRA_ENTRADA_AGENDAMENTO ");
		strQuery.append(PropertyUtil.getTabelaAgendamentos());
		Query query = criarConsultaSQL(strQuery.toString()).addScalar("CD_ENTRADA_AGENDAMENTO", LongType.INSTANCE);
		query.setMaxResults(1);
		Object result = query.uniqueResult();

		if (result != null) {
			return (Long) result;
		}
		return null;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
	private String getTipoMovimento(VagaPacienteDTO vagaPaciente) throws DAOException {
		StringBuilder strQuery = new StringBuilder(" SELECT ");
		strQuery.append(" 	case count(*)  ");
		strQuery.append(" 	when 0 then 'I' else 'A' end tipoMovimento ");
		strQuery.append(" FROM ");
		//TODO Alterar para que o nome da tabela seja capturado pelo banco
		//		strQuery.append("  DBAHSR.INTEGRA_ENTRADA_AGENDAMENTO ");
		strQuery.append(PropertyUtil.getTabelaAgendamentos());
		strQuery.append("  WHERE (1=1) AND ");
		if (vagaPaciente.getPaciente().getId() != null && vagaPaciente.getPaciente().getCdIntegracao() == null) {
			strQuery.append("  CD_PACIENTE = :cdPaciente");
		} else if (vagaPaciente.getPaciente().getId() == null && vagaPaciente.getPaciente().getCdIntegracao() != null) {
			strQuery.append("  CD_PACIENTE_INTEGRA = :cdPacienteNuclearis ");

		} else {
			strQuery.append("  (CD_PACIENTE = :cdPaciente OR CD_PACIENTE_INTEGRA = :cdPacienteNuclearis)");
		}
		Query query = criarConsultaSQL(strQuery.toString());
		//		query.setParameter("cdAgendamento", vagaPaciente.getCodigoAgendamento());

		if (vagaPaciente.getPaciente().getId() != null && vagaPaciente.getPaciente().getCdIntegracao() == null) {
			query.setParameter("cdPacienteNuclearis", vagaPaciente.getPaciente().getId());
		} else if (vagaPaciente.getPaciente().getId() == null && vagaPaciente.getPaciente().getCdIntegracao() != null) {
			query.setParameter("cdPaciente", vagaPaciente.getPaciente().getCdIntegracao());

		} else {
			query.setParameter("cdPacienteNuclearis", vagaPaciente.getPaciente().getId());
			query.setParameter("cdPaciente", vagaPaciente.getPaciente().getCdIntegracao());
		}
		query.setMaxResults(1);
		return String.valueOf(query.uniqueResult());
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
	public void inserirLaudo(Long cdAtendimento, Byte[] laudo) throws DAOException {
		StringBuilder strQuery = new StringBuilder(" UPDATE ");
		//		strQuery.append(" 	MVINTEGRA.INTEGRA_SAIDA_SOLICITACAO_SADT ");
		strQuery.append(PropertyUtil.getTabelaAtendimentos());
		strQuery.append(" 		SET DS_LAUDO = :laudo ");
		strQuery.append(" 	WHERE ");
		strQuery.append(" 		CD_ATENDIMENTO = :cdAtendimento ");

		Query query = criarConsultaSQL(strQuery.toString());
		query.setParameter("laudo", laudo);
		query.setParameter("cdAtendimento", cdAtendimento);
		query.executeUpdate();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
	public Long consultarCdAtendimentoPacienteInternado(Long codigoPaciente) throws DAOException {
		StringBuilder strQuery = new StringBuilder(" SELECT ");
		strQuery.append(" 	DISTINCT ss.CD_ATENDIMENTO  ");
		strQuery.append(" 	FROM ");
		//TODO Alterar para que o nome da tabela seja capturado pelo banco
		//		strQuery.append("  DBAHSR.INTEGRA_ENTRADA_AGENDAMENTO ");
		strQuery.append(PropertyUtil.getTabelaAtendimentos());
		strQuery.append("JOIN MVINTEGRA.INTEGRA_SAIDA_CAD_PESSOA PESSOA ON SS.CD_PACIENTE = PESSOA.CD_PESSOA ");
		strQuery.append("  WHERE (1=1) AND ");
		strQuery.append("  	ss.CD_EXAME IN (1036, 1689, 553) ");
		strQuery.append("  	AND ss.CD_SETOR_EXECUTANTE = 5   ");
		strQuery.append("  	AND ss.CD_PACIENTE = :codigoPaciente ");
		strQuery.append("  	and ss.CD_SETOR_SOLICITANTE <> 121 ");
		strQuery.append(" 		and ss.dt_integracao = ( ");
		strQuery.append(" 			select max (dt_integracao) from MVINTEGRA.INTEGRA_SAIDA_SOLICITACAO_SADT ss1 where  ss1.CD_ATENDIMENTO = ss.CD_ATENDIMENTO ");
		strQuery.append(" 		 ) ");
		strQuery.append(" 		AND ss.TP_REGISTRO = 2  ");//código 2 refere-se aos dados do exame
		strQuery.append(" 		AND ss.TP_PEDIDO = 'I' ");// I = Imagem
		strQuery.append(" 		AND PESSOA.CD_INTEGRA_SAIDA_CAD_PESSOA = ( ");
		strQuery.append(" 			SELECT max(CD_INTEGRA_SAIDA_CAD_PESSOA) from MVINTEGRA.INTEGRA_SAIDA_CAD_PESSOA pes ");
		strQuery.append(" 			 WHERE (1=1) ");
		strQuery.append(" 			 AND pes.CD_PESSOA = pessoa.CD_PESSOA ");
		strQuery.append(" 			 AND pes.TP_INTEGRACAO = pessoa.TP_INTEGRACAO  ");
		strQuery.append(" 			 AND pes.DT_NASCIMENTO IS NOT NULL ");
		strQuery.append(" 			 AND pes.TP_SEXO IS NOT NULL  ");
		strQuery.append(" 		 ) ");
		strQuery.append(" 			 order by ss.HR_PEDIDO, ss.CD_ITEM_PEDIDO ");
		Query query = criarConsultaSQL(strQuery.toString());
		query.setParameter("codigoPaciente", codigoPaciente);
		query.setMaxResults(1);
		return (Long) query.uniqueResult();
	}

}
