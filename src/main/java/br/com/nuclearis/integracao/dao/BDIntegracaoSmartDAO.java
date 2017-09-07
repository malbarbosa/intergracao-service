package br.com.nuclearis.integracao.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.com.nuclearis.integracao.dao.interfaces.IIntegracaoDAO;
import br.com.nuclearis.integracao.dto.IntegracaoDTO;
import br.com.nuclearis.integracao.exceptions.DAOException;

@Repository("bdIntegracaoSMARTDAO")
@Scope("prototype")
public class BDIntegracaoSmartDAO extends DAONuclearisInt<IntegracaoDTO> implements IIntegracaoDAO {

	public BDIntegracaoSmartDAO() {
		super(IntegracaoDTO.class);
	}

	//	@Override
	//	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
	//	public VagaPacienteDTO getPacientePorId(Long idPacienteIntegracao) throws DAOException {
	//
	//		String hql = "SELECT  ";
	//		hql += "		VPE_REG_PACIENTE CD_PACIENTE ";
	//		hql += "		,VPE_NOME NOME ";
	//		hql += "		,VPE_DATA_NASCIMENTO DATA_NASCIMENTO ";
	//		hql += "		,VPE_SEXO SEXO ";
	//		hql += "		,VPE_CELULAR CELULAR ";
	//		hql += "		,VPE_CEP CEP ";
	//		hql += "		,VPE_ENDERECO ENDERECO ";
	//		hql += "		,VPE_CIDADE CIDADE ";
	//		hql += "		,VPE_PAIS PAIS ";
	//		hql += "		,VPE_UF UF ";
	//		hql += "		,VPE_CPF CPF ";
	//		hql += "		,VPE_MAE NOME_MAE ";
	//		hql += "		,VPE_EMAIL EMAIL ";
	//		hql += "		,VPE_RG RG ";
	//		hql += "		,VPE_EMISSOR_RG EMISSOR_RG ";
	//		hql += "		,VPE_UF_RG UF_RG ";
	//		hql += "		,VPE_PESO PESO ";
	//		hql += "		,VPE_ALTURA ALTURA ";
	//		hql += "		,VPE_COMPLEMENTO COMPLEMENTO ";
	//		hql += "      	FROM SMART.VW_NUC_PACIENTES ";
	//		hql += "      	WHERE VPE_REG_PACIENTE = :idPacienteIntegracao ";
	//
	//		try {
	//			Query query = criarConsultaSQL(hql.toString()).addScalar("CD_PACIENTE", LongType.INSTANCE).addScalar("NOME", StringType.INSTANCE).addScalar("DATA_NASCIMENTO", CalendarType.INSTANCE).addScalar("SEXO", StringType.INSTANCE)
	//					.addScalar("CELULAR", StringType.INSTANCE).addScalar("CEP", StringType.INSTANCE).addScalar("ENDERECO", StringType.INSTANCE).addScalar("CIDADE", StringType.INSTANCE).addScalar("PAIS", StringType.INSTANCE)
	//					.addScalar("UF", StringType.INSTANCE).addScalar("CPF", StringType.INSTANCE).addScalar("NOME_MAE", StringType.INSTANCE).addScalar("EMAIL", StringType.INSTANCE).addScalar("RG", StringType.INSTANCE)
	//					.addScalar("EMISSOR_RG", StringType.INSTANCE).addScalar("UF_RG", StringType.INSTANCE).addScalar("PESO", BigDecimalType.INSTANCE).addScalar("ALTURA", BigDecimalType.INSTANCE).addScalar("COMPLEMENTO", StringType.INSTANCE);
	//			query.setParameter("idPacienteIntegracao", idPacienteIntegracao);
	//
	//			Object result = query.uniqueResult();
	//			if (result != null) {
	//				Object[] obj = (Object[]) result;
	//				int i = 0;
	//				VagaPacienteDTO dadosPOJO = new VagaPacienteDTO();
	//				i = this.getDadosBasicosPaciente(obj, i, dadosPOJO);
	//				i = this.getDadosEndereco(obj, i, dadosPOJO);
	//				Object valor = obj[i++];
	//				dadosPOJO.getPaciente().setNumeroCpf(valor == null ? null : String.valueOf(valor));
	//				valor = obj[i++];
	//				dadosPOJO.getPaciente().setNomeMae(valor == null ? null : String.valueOf(valor));
	//				valor = obj[i++];
	//				dadosPOJO.getPaciente().setEmail(valor == null ? null : String.valueOf(valor));
	//				valor = obj[i++];
	//				dadosPOJO.getPaciente().setNumeroRg(valor == null ? null : String.valueOf(valor));
	//				valor = obj[i++];
	//				dadosPOJO.getPaciente().setEmissorRg(valor == null ? null : String.valueOf(valor));
	//				valor = obj[i++];
	//				dadosPOJO.getPaciente().setUfRg(valor == null ? null : String.valueOf(valor));
	//				valor = obj[i++];
	//				dadosPOJO.setPesoPaciente(valor == null ? Constantes.PADRAO_MASSA_PACIENTE : ((BigDecimal) valor).floatValue());
	//				valor = obj[i++];
	//				dadosPOJO.setAlturaPaciente(valor == null ? Constantes.PADRAO_ALTURA_PACIENTE : ((BigDecimal) valor).floatValue());
	//				valor = obj[i++];
	//				dadosPOJO.getPaciente().getEndereco().setComplemento(valor == null ? null : String.valueOf(valor));
	//				return dadosPOJO;
	//			}
	//
	//			return null;
	//		} catch (DAOException e) {
	//			throw new DAOException(e);
	//		}
	//	}

	//	@Override
	//	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
	//	public ConvenioDTO getConvenioEPlano(String nomeConvenioIntegracao, String nomePlanoIntegracao) throws DAOException {
	//
	//		String sqlQuery = "SELECT  ";
	//		sqlQuery += "      VPC_COD_CONVENIO COD_CONVENIO ";
	//		sqlQuery += "      ,VPC_DESC_CONVENIO DESC_CONVENIO ";
	//		sqlQuery += "      ,VPC_COD_PLANO COD_PLANO ";
	//		sqlQuery += "      ,VPC_DESC_PLANO DESC_PLANO ";
	//		sqlQuery += "      FROM SMART.VW_NUC_PLANO_CONVENIO ";
	//		sqlQuery += "      WHERE  ";
	//		sqlQuery += "      	(1=1) ";
	//		sqlQuery += "      AND 	(:nomeConvenioIntegracao IS NULL OR VPC_DESC_CONVENIO like :nomeConvenioIntegracao) ";
	//		sqlQuery += "      AND 	(:nomePlanoIntegracao IS NULL OR VPC_DESC_PLANO like :nomePlanoIntegracao) ";
	//
	//		try {
	//			Query query = criarConsultaSQL(sqlQuery);
	//			query.setParameter("nomeConvenioIntegracao", nomeConvenioIntegracao);
	//			query.setParameter("nomePlanoIntegracao", nomePlanoIntegracao);
	//
	//			Object result = query.uniqueResult();
	//			if (result != null) {
	//				Object[] obj = (Object[]) result;
	//				int i = 0;
	//				VagaPacienteDTO dadosPOJO = new VagaPacienteDTO();
	//				this.getDadosConvenio(obj, i, dadosPOJO);
	//				return dadosPOJO.getConvenio();
	//			}
	//
	//			return null;
	//		} catch (DAOException e) {
	//			throw new DAOException(e);
	//		}
	//	}

	//	@Override
	//	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
	//	public ConvenioDTO getConvenioEPlanoPorId(Long idConvenioIntegracao, Long idPlanoIntegracao) throws DAOException {
	//
	//		String sqlQuery = "SELECT  ";
	//		sqlQuery += "      VPC_COD_CONVENIO COD_CONVENIO ";
	//		sqlQuery += "      ,VPC_DESC_CONVENIO DESC_CONVENIO ";
	//		sqlQuery += "      ,VPC_COD_PLANO COD_PLANO ";
	//		sqlQuery += "      ,VPC_DESC_PLANO DESC_PLANO ";
	//		sqlQuery += "      FROM SMART.VW_NUC_PLANO_CONVENIO ";
	//		sqlQuery += "      WHERE  ";
	//		sqlQuery += "      	(1=1) ";
	//		sqlQuery += "      AND 	VPC_COD_CONVENIO = :idConvenioIntegracao ";
	//		sqlQuery += "      AND 	VPC_DESC_PLANO = :idPlanoIntegracao ";
	//
	//		try {
	//			Query query = criarConsultaSQL(sqlQuery);
	//			query.setParameter("idConvenioIntegracao", idConvenioIntegracao);
	//			query.setParameter("idPlanoIntegracao", idPlanoIntegracao);
	//
	//			Object result = query.uniqueResult();
	//			if (result != null) {
	//				Object[] obj = (Object[]) result;
	//				int i = 0;
	//				VagaPacienteDTO dadosPOJO = new VagaPacienteDTO();
	//				this.getDadosConvenio(obj, i, dadosPOJO);
	//				return dadosPOJO.getConvenio();
	//			}
	//
	//			return null;
	//		} catch (DAOException e) {
	//			throw new DAOException(e);
	//		}
	//	}

	//	@Override
	//	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, timeout = 15, value = "txManagerNuclearisInt")
	//	public Usuario getUsuarioByLogin(final String login) throws DAOException {
	//		String hql = "SELECT VUS_NOME, VUS_EMAIL, VUS_SEXO, VUS_STATUS ";
	//		hql += "      FROM SMART.VW_NUC_USUARIOS ";
	//		hql += "      WHERE  VUS_LOGIN = :loginUsuario AND VUS_SEXO IS NOT NULL ";
	//
	//		try {
	//			Query query = criarConsultaSQL(hql);
	//			query.setParameter("loginUsuario", login);
	//
	//			Object result = query.uniqueResult();
	//			if (result != null) {
	//				Object[] obj = (Object[]) result;
	//				Usuario usuario = new Usuario();
	//				usuario.setNome(String.valueOf(obj[0]));
	//				usuario.setEmail(String.valueOf(obj[1]));
	//				TipoSexoEnum tipoSexoEnum = TipoSexoEnum.getPorSigla(String.valueOf(obj[2]));
	//				TipoSexo tpSexo = new TipoSexo(tipoSexoEnum.getId());
	//				usuario.setTipoSexo(tpSexo);
	//				usuario.setAtivo(Boolean.valueOf(String.valueOf(obj[3])));
	//				return usuario;
	//			}
	//
	//			return null;
	//		} catch (DAOException e) {
	//			throw new DAOException(e);
	//		}
	//	}

	@Override
	public void inserirLaudo(Long cdAtendimento, Byte[] laudo) throws DAOException {
		// TODO Auto-generated method stub

	}

}
