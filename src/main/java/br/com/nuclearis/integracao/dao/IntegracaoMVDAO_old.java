//package br.com.nuclearis.integracao.dao;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.hibernate.Query;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import br.com.nuclearis.core.dataaccess.exception.DAOException;
//import br.com.nuclearis.core.util.Constantes;
//import br.com.nuclearis.integracao.dao.interfaces.IIntegracaoMVDAO_old;
//import br.com.nuclearis.integracao.dto.VagaPacienteDTO;
//
////@Repository("daoMV")
////@Scope("prototype")
//public class IntegracaoMVDAO_old extends DAONuclearisInt<VagaPacienteDTO> implements IIntegracaoMVDAO_old {
//
//	//TODO está fixo apenas porque vamos começar a integrar somente com o PET-CT. Este parametro será retirado depois. O código 533 refere-se ao SPECT-Cerebral, mas o HSR trata este exame como PET
//	private final List<String> listaCodigosExame = Arrays.asList(new String[] { "1036", "1689", "553" });
//	//	private final List<String> listaCodigosExame = Arrays.asList(new String[] { "510", "511", "512" });
//
//	public IntegracaoMVDAO_old() {
//		super(VagaPacienteDTO.class);
//	}
//
//	/**
//	 * Essa consulta busca pela lista os atendimentos do dia corrente que foram gerados pelo sistema MV.
//	 * 
//	 * @param listaAtendimentosExistentes
//	 *            -> Representa a lista dos códigos dos atendimentos que já possuem exame execução gerados. Essa lista é utilizada para que esses atendimentos não venham na consulta mais de uma vez.
//	 * @return List retorna a lista de atendimentos do MV
//	 */
//
//	@Override
//	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
//	public List<VagaPacienteDTO> getListaAtendimentosDoDia(Set<Long> listaAtendimentosExistentes, Long cdAtendimento) throws DAOException {
//
//		StringBuilder strQuery = new StringBuilder(" SELECT ");
//		strQuery.append(" DISTINCT ss.HR_PEDIDO,ss.CD_SETOR_SOLICITANTE,ss.CD_ATENDIMENTO,ss.CD_PEDIDO, ss.CD_EXAME,ss.DS_EXAME, ");
//		strQuery.append(" 	ss.CD_ITEM_PEDIDO,ss.NR_ALTURA, ss.NR_PESO,ss.CD_PLANO,ss.DS_PLANO,ss.CD_CONVENIO,ss.NM_CONVENIO, ss.CD_PACIENTE, ");
//		strQuery.append(" 	PESSOA.NM_PESSOA, PESSOA.DT_NASCIMENTO,PESSOA.TP_SEXO,PESSOA.NR_FONE,PESSOA.NR_CGC_CPF, PESSOA.DS_EMAIL, PESSOA.NM_MAE, PESSOA.NR_IDENTIDADE,   ");
//		strQuery.append(" 	PESSOA.DS_ENDERECO,PESSOA.CD_UF, PESSOA.NR_CEP, PESSOA.NR_ENDERECO, PESSOA.NM_CIDADE, ");
//		strQuery.append(" 	PESSOA.NM_BAIRRO,PESSOA.DS_OM_IDENTIDADE,PESSOA.NR_CNS,PESSOA.TP_ESTADO_CIVIL,ss.CD_PRESTADOR_SOLICITANTE, ss.NM_PRESTADOR_SOLICITANTE, ss.NR_CONSELHO_SOLICITANTE, ss.DT_INTEGRACAO ");
//		strQuery.append(" FROM   ");
//		strQuery.append(" 	MVINTEGRA.INTEGRA_SAIDA_SOLICITACAO_SADT ss  ");
//		strQuery.append(" 	JOIN MVINTEGRA.INTEGRA_SAIDA_CAD_PESSOA PESSOA ON SS.CD_PACIENTE = PESSOA.CD_PESSOA  ");
//		strQuery.append(" WHERE  ");
//		strQuery.append(" 		(1=1) ");
//		if (cdAtendimento == null) {
//			strQuery.append(" 		AND ss.CD_EXAME IN (:codigoPetCT) "); // Códigos do PET-CT
//			strQuery.append(" 		AND ss.CD_SETOR_EXECUTANTE = 5  ");//código setor da medicina nuclear
//			if (listaAtendimentosExistentes != null && listaAtendimentosExistentes.size() > 0) {
//				strQuery.append(" 	AND ss.CD_ITEM_PEDIDO NOT IN (:lstCdAtendimentos)  ");//código do item do pedido
//			}
//			strQuery.append(" 		AND ss.DT_PEDIDO = TRUNC(SYSDATE)  ");//data do pedido
//			strQuery.append(" 		and ss.dt_integracao BETWEEN trunc(SYSDATE) and trunc(SYSDATE+1) ");//data de integracao
//		} else {
//			strQuery.append(" 		AND ss.CD_ATENDIMENTO = :cdAtendimento ");
//		}
//		strQuery.append(" 		and ss.dt_integracao = ( ");
//		strQuery.append(" 			select max (dt_integracao) from MVINTEGRA.INTEGRA_SAIDA_SOLICITACAO_SADT ss1 where  ss1.CD_ATENDIMENTO = ss.CD_ATENDIMENTO ");
//		strQuery.append(" 		 ) ");
//		if (cdAtendimento == null) {
//			strQuery.append(" 		AND ss.TP_REGISTRO = 2  ");//código 2 refere-se aos dados do exame
//			strQuery.append(" 		AND ss.TP_PEDIDO = 'I' ");// I = Imagem
//		}
//		strQuery.append(" 		AND PESSOA.CD_INTEGRA_SAIDA_CAD_PESSOA = ( ");
//		strQuery.append(" 			SELECT max(CD_INTEGRA_SAIDA_CAD_PESSOA) from MVINTEGRA.INTEGRA_SAIDA_CAD_PESSOA pes ");
//		strQuery.append(" 			 WHERE (1=1) ");
//		strQuery.append(" 			 AND pes.CD_PESSOA = pessoa.CD_PESSOA ");
//		strQuery.append(" 			 AND pes.TP_INTEGRACAO = pessoa.TP_INTEGRACAO  ");
//		strQuery.append(" 			 AND pes.DT_NASCIMENTO IS NOT NULL ");
//		strQuery.append(" 			 AND pes.TP_SEXO IS NOT NULL  ");
//		strQuery.append(" 		 ) ");
//		strQuery.append(" 			 order by ss.HR_PEDIDO, ss.CD_ITEM_PEDIDO ");
//
//		Query query = criarConsultaSQL(strQuery.toString());
//
//		if (cdAtendimento == null) {
//			query.setParameterList("codigoPetCT", listaCodigosExame);
//			if (listaAtendimentosExistentes != null && listaAtendimentosExistentes.size() > 0) {
//				query.setParameterList("lstCdAtendimentos", listaAtendimentosExistentes);
//			}
//		} else {
//			query.setParameter("cdAtendimento", cdAtendimento);
//		}
//
//		List<Object> result = query.list();
//		//		List<Object> result = listaAtendimentos;
//		List<VagaPacienteDTO> atendimentos = popularAtendimento(result);
//
//		return atendimentos;
//	}
//
//	private List<VagaPacienteDTO> popularAtendimento(List<Object> result) {
//		List<VagaPacienteDTO> atendimentos = new ArrayList<VagaPacienteDTO>();
//
//		for (Object object : result) {
//			Object[] obj = (Object[]) object;
//			int i = 0;
//			VagaPacienteDTO atendimentoMV = new VagaPacienteDTO();
//			Calendar calendar = Calendar.getInstance();
//			Object valor = obj[i++];
//			if (valor != null) {
//				calendar.setTimeInMillis(((Timestamp) valor).getTime());
//				atendimentoMV.setDtHoraExame(calendar);
//			}
//			valor = obj[i++];
//			atendimentoMV.setCodigoSetorSolicitante(valor == null ? null : ((BigDecimal) valor).intValue());
//			valor = obj[i++];
//			atendimentoMV.setCodigoAtendimento(valor == null ? null : ((BigDecimal) valor).longValue());
//			valor = obj[i++];
//			atendimentoMV.setCodigoAgendamento(valor == null ? null : ((BigDecimal) valor).longValue());
//			valor = obj[i++];
//			atendimentoMV.setCodigoExame(valor == null ? null : ((BigDecimal) valor).longValue());
//			valor = obj[i++];
//			atendimentoMV.setDescricaoExame(String.valueOf(obj[6]));
//			valor = obj[i++];
//			atendimentoMV.setCodSolicitacaoExame(valor == null ? null : ((BigDecimal) valor).longValue());
//			valor = obj[i++];
//
//			String valorStr = null;
//			if (valor == null) {
//				atendimentoMV.setAlturaPaciente(Constantes.PADRAO_ALTURA_PACIENTE);
//			} else if (valor.toString().replaceAll("[^\\d]", "").length() >= 3) {
//				valorStr = valor.toString().replaceAll("[^\\d]", "");
//				atendimentoMV.setAlturaPaciente((BigDecimal.valueOf(Long.valueOf(valorStr))).floatValue());
//			} else {
//				valorStr = valor.toString().replaceAll("[^\\d]", "");
//				atendimentoMV.setAlturaPaciente((BigDecimal.valueOf(Long.valueOf(valorStr))).multiply(BigDecimal.TEN).floatValue());
//			}
//			valor = obj[i++];
//			atendimentoMV.setPesoPaciente(valor == null ? Constantes.PADRAO_MASSA_PACIENTE : ((BigDecimal) valor).floatValue());
//			valor = obj[i++];
//			i = getDadosConvenio(obj, atendimentoMV, valor, i);
//			i = getPaciente(obj, i, atendimentoMV);
//			i = getPrestadorSolicitante(obj, i, atendimentoMV);
//			valor = obj[i++];
//			if (valor != null) {
//				calendar = Calendar.getInstance();
//				calendar.setTimeInMillis(((Timestamp) valor).getTime());
//				//TODO verificar se essa informação está sendo utilizada em alguma parte da integração
//				atendimentoMV.setDataChegadaRecepcao(calendar);
//			}
//			atendimentos.add(atendimentoMV);
//			atendimentoMV = null;
//		}
//		return atendimentos;
//	}
//
//	private int getPaciente(Object[] obj, int i, VagaPacienteDTO vagaPaciente) {
//		Object valor;
//		i = this.getDadosBasicosPaciente(obj, i, vagaPaciente);
//		valor = obj[i++];
//		vagaPaciente.getPaciente().setNumeroCpf(valor == null ? null : String.valueOf(valor));
//		valor = obj[i++];
//		vagaPaciente.getPaciente().setEmail(valor == null ? null : String.valueOf(valor));
//		valor = obj[i++];
//		vagaPaciente.getPaciente().setNomeMae(valor == null ? null : String.valueOf(valor));
//		valor = obj[i++];
//		vagaPaciente.getPaciente().setNumeroRg(valor == null ? null : String.valueOf(valor));
//		valor = obj[i++];
//		i = getEndereco(obj, i, vagaPaciente, valor);
//		valor = obj[i++];
//		vagaPaciente.getPaciente().setEmissorRg(valor == null ? null : String.valueOf(valor));
//		valor = obj[i++];
//		vagaPaciente.getPaciente().setNumeroCNS(valor == null ? null : String.valueOf(valor));
//		valor = obj[i++];
//		vagaPaciente.getPaciente().setEstadoCivil(valor == null ? null : String.valueOf(valor));
//		return i;
//	}
//
//	private int getDadosBasicosPaciente(Object[] obj, int i, VagaPacienteDTO vagaPaciente) {
//		Calendar calendar;
//		Object valor;
//		valor = obj[i++];
//		vagaPaciente.getPaciente().setId(valor == null ? null : ((BigDecimal) valor).longValue());
//		valor = obj[i++];
//		vagaPaciente.getPaciente().setNome(valor == null ? null : String.valueOf(valor));
//		valor = obj[i++];
//		if (valor != null) {
//			calendar = Calendar.getInstance();
//			calendar.setTimeInMillis(((Timestamp) valor).getTime());
//			vagaPaciente.getPaciente().setDataNascimento(calendar);
//		}
//
//		valor = obj[i++];
//		vagaPaciente.getPaciente().setSexo(valor == null ? null : String.valueOf(valor));
//		valor = obj[i++];
//		vagaPaciente.getPaciente().setTelefone(valor == null ? null : String.valueOf(valor));
//		return i;
//	}
//
//	private int getEndereco(Object[] obj, int i, VagaPacienteDTO atendimentoMV, Object valor) {
//		atendimentoMV.getPaciente().getEndereco().setLogradouro(valor == null ? null : String.valueOf(valor));
//		valor = obj[i++];
//		atendimentoMV.getPaciente().getEndereco().setUf(valor == null ? null : String.valueOf(valor));
//		valor = obj[i++];
//		atendimentoMV.getPaciente().getEndereco().setCep(valor == null ? null : String.valueOf(valor));
//		valor = obj[i++];
//		atendimentoMV.getPaciente().getEndereco().setNumero(valor == null ? null : String.valueOf(valor));
//		valor = obj[i++];
//		atendimentoMV.getPaciente().getEndereco().setCidade(valor == null ? null : String.valueOf(valor));
//		valor = obj[i++];
//		atendimentoMV.getPaciente().getEndereco().setBairro(valor == null ? null : String.valueOf(valor));
//		return i;
//	}
//
//	private int getPrestadorSolicitante(Object[] obj, int i, VagaPacienteDTO atendimentoMV) {
//		Object valor;
//		valor = obj[i++];
//		atendimentoMV.getPrestadorSolicitantePOJO().setId(valor == null ? null : ((BigDecimal) valor).longValue());
//		valor = obj[i++];
//		atendimentoMV.getPrestadorSolicitantePOJO().setNome(valor == null ? null : String.valueOf(valor));
//		valor = obj[i++];
//		atendimentoMV.getPrestadorSolicitantePOJO().setNumeroConselho(valor == null ? null : String.valueOf(valor));
//		return i;
//	}
//
//	@Override
//	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
//	public Map<Long, VagaPacienteDTO> getListaAgendamentos() throws DAOException {
//		StringBuilder strQuery = new StringBuilder(" SELECT ");
//		strQuery.append(" 	TO_DATE(marcacao.DT_AGENDA || marcacao.HR_AGENDA,'dd/mm/yyyy hh24:mi:ss') ");
//		strQuery.append(" 	,marcacao.CD_USUARIO USUARIO_RESPONSAVEL ");
//		strQuery.append(" 	,marcacao.CD_PAC CD_PACIENTE ");
//		strQuery.append(" 	,marcacao.NM_PACIENTE NOME_PACIENTE ");
//		strQuery.append(" 	,marcacao.DT_NASCIMENTO ");
//		strQuery.append(" 	,CASE WHEN(marcacao.TP_SEXO = 'F') THEN 'Feminino' ELSE 'Masculino' END ");
//		strQuery.append(" 	,marcacao.FONE ");
//		strQuery.append(" 	,marcacao.VL_ALTURA ");
//		strQuery.append(" 	,marcacao.QT_PESO ");
//		strQuery.append(" 	,marcacao.TP_SITUACAO ");
//		strQuery.append(" 	,marcacao.CD_IT_AGENDA_CENTRAL CD_ITEM_PEDIDO ");
//		strQuery.append(" 	,marcacao.CD_EXAME ");
//		strQuery.append(" 	,marcacao.PROCEDIMENTO DS_EXAME");
//		strQuery.append(" 	,marcacao.CD_CONVENIO ");
//		strQuery.append(" 	,marcacao.NM_CONVENIO ");
//		strQuery.append(" 	,marcacao.CD_PLANO ");
//		strQuery.append(" 	,marcacao.NM_PLANO ");
//		strQuery.append(" 	,marcacao.OBS ");
//		strQuery.append(" FROM ");
//		strQuery.append("  		DBAHSR.VDIC_MARCACAO_MN marcacao");
//		strQuery.append(" WHERE ");
//		strQuery.append(" 		marcacao.CD_EXAME IN (:codigoPetCT) ");
//		strQuery.append(" AND	marcacao.DT_GRAVACAO || marcacao.HR_GRAVACAO = ( ");
//		strQuery.append(" 			SELECT max(m1.DT_GRAVACAO || m1.HR_GRAVACAO) ");
//		strQuery.append(" 			FROM DBAHSR.VDIC_MARCACAO_MN m1 ");
//		strQuery.append(" 			WHERE m1.CD_PAC = marcacao.cd_pac ) ");
//		strQuery.append(" AND	(marcacao.TP_SITUACAO <> 'C' OR marcacao.TP_SITUACAO IS NULL )");
//		strQuery.append(" ORDER BY ");
//		strQuery.append(" 	TO_DATE(marcacao.DT_AGENDA || marcacao.HR_AGENDA,'dd/mm/yyyy hh24:mi:ss') ");
//
//		Query query = criarConsultaSQL(strQuery.toString());
//
//		query.setParameterList("codigoPetCT", listaCodigosExame); //TODO está fixo apenas porque vamos começar a integrar somente com o PET-CT. Este parametro será retirado depois
//		List<Object> result = query.list();
//		//		List<Object> result = lista;
//		Map<Long, VagaPacienteDTO> resultados = new LinkedHashMap<Long, VagaPacienteDTO>();
//
//		for (Object object : result) {
//			Object[] obj = (Object[]) object;
//			int i = 0;
//			VagaPacienteDTO vagaPaciente = new VagaPacienteDTO();
//			Calendar calendar = Calendar.getInstance();
//			Object valor = obj[i++];
//			calendar.setTimeInMillis(valor == null ? null : ((Timestamp) valor).getTime());
//			vagaPaciente.setDtHoraExame(calendar);
//			valor = obj[i++];
//			vagaPaciente.setUsuarioResponsavel(valor == null ? null : String.valueOf(valor));
//			i = getDadosBasicosPaciente(obj, i, vagaPaciente);
//			valor = obj[i++];
//			vagaPaciente.setPesoPaciente(valor == null ? Constantes.PADRAO_MASSA_PACIENTE : ((BigDecimal) valor).floatValue());
//			valor = obj[i++];
//			String valorStr = null;
//			if (valor == null) {
//				vagaPaciente.setAlturaPaciente(Constantes.PADRAO_ALTURA_PACIENTE);
//			} else if (valor.toString().replaceAll("[^\\d]", "").length() >= 3) {
//				valorStr = valor.toString().replaceAll("[^\\d]", "");
//				vagaPaciente.setAlturaPaciente((BigDecimal.valueOf(Long.valueOf(valorStr))).floatValue());
//			} else {
//				valorStr = valor.toString().replaceAll("[^\\d]", "");
//				vagaPaciente.setAlturaPaciente((BigDecimal.valueOf(Long.valueOf(valorStr))).multiply(BigDecimal.TEN).floatValue());
//			}
//			valor = obj[i++];
//			vagaPaciente.setSituacao(valor == null ? null : String.valueOf(valor));
//			valor = obj[i++];
//			vagaPaciente.setCodSolicitacaoExame(valor == null ? null : ((BigDecimal) valor).longValue());
//			valor = obj[i++];
//			vagaPaciente.setCodigoExame(valor == null ? null : ((BigDecimal) valor).longValue());
//			valor = obj[i++];
//			vagaPaciente.setDescricaoExame(valor == null ? null : String.valueOf(valor));
//			valor = obj[i++];
//			i = getDadosConvenio(obj, vagaPaciente, valor, i);
//			valor = obj[i++];
//			vagaPaciente.setObservacao(valor == null ? null : String.valueOf(valor));
//			resultados.put(vagaPaciente.getCodSolicitacaoExame(), vagaPaciente);
//
//			vagaPaciente = null;
//			calendar = null;
//		}
//
//		return resultados;
//
//	}
//
//	private int getDadosConvenio(Object[] obj, VagaPacienteDTO vagaPaciente, Object valor, int i) {
//		vagaPaciente.getConvenio().setIdConvenio(valor == null ? null : ((BigDecimal) valor).longValue());
//		valor = obj[i++];
//		vagaPaciente.getConvenio().setNomeConvenio(valor == null ? null : String.valueOf(valor));
//		valor = obj[i++];
//		vagaPaciente.getConvenio().getPlano().setIdPlano(valor == null ? null : ((BigDecimal) valor).longValue());
//		valor = obj[i++];
//		vagaPaciente.getConvenio().getPlano().setNomePlano(valor == null ? null : String.valueOf(valor));
//		return i;
//	}
//
//}
