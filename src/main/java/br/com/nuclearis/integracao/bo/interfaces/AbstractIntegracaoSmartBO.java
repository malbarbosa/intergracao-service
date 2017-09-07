package br.com.nuclearis.integracao.bo.interfaces;
//package br.com.nuclearis.integracao.bo.interfaces;
//
//import java.util.Calendar;
//
//import javax.annotation.Resource;
//
//import br.com.nuclearis.core.dataaccess.bo.TipoSexoBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.IIdiomaBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.ITimeZoneBO;
//import br.com.nuclearis.core.dataaccess.bo.interfaces.IUsuarioBO;
//import br.com.nuclearis.core.dataaccess.exception.DAOException;
//import br.com.nuclearis.core.dataaccess.exception.IdPushAndroidDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.IdPushIOSDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.IdiomaInexistenteException;
//import br.com.nuclearis.core.dataaccess.exception.QueryParametroInvalidoException;
//import br.com.nuclearis.core.dataaccess.exception.SalvarOuAtualizarUsuarioException;
//import br.com.nuclearis.core.dataaccess.exception.UsuarioEmailDuplicadoException;
//import br.com.nuclearis.core.dataaccess.exception.UsuarioInexistenteException;
//import br.com.nuclearis.core.entity.usuario.Usuario;
//import br.com.nuclearis.integracao.dao.interfaces.IIntegracaoSmartDAO;
//import br.com.nuclearis.integracao.bo.BOGenericoInt;
//import br.com.nuclearis.integracao.dto.ConvenioDTO;
//import br.com.nuclearis.integracao.dto.VagaPacienteDTO;
//
//public abstract class AbstractIntegracaoSmartBO extends BOGenericoInt<VagaPacienteDTO> implements IIntegracaoBO {
//
//	@Resource(name = "daoSmart")
//	protected IIntegracaoSmartDAO dao;
//
//	@Resource
//	protected TipoSexoBO tipoSexoBO;
//
//	@Resource
//	private IUsuarioBO boUsuario;
//
//	@Resource
//	private ITimeZoneBO boTimeZone;
//
//	@Resource
//	private IIdiomaBO boIdioma;
//
//	public abstract VagaPacienteDTO getPacientePorId(final Long idPacienteIntegracao) throws DAOException;
//
//	public abstract ConvenioDTO getConvenioEPlano(final String idConvenioIntegracao, final String idPlanoIntegracao) throws DAOException;
//
//	public abstract ConvenioDTO getConvenioEPlanoPorId(Long idConvenioIntegracao, Long idPlanoIntegracao) throws DAOException;
//
//	public Usuario getUsuarioByLogin(String login) throws DAOException, QueryParametroInvalidoException, UsuarioEmailDuplicadoException, SalvarOuAtualizarUsuarioException, IdPushAndroidDuplicadoException, IdPushIOSDuplicadoException,
//			UsuarioInexistenteException, IdiomaInexistenteException {
//		Usuario usuario = dao.getUsuarioByLogin(login);
//		final Long idTimeZoneBahia = 77L;
//		final Long idIdiomaPTBR = 1l;
//		usuario.setTipoSexo(tipoSexoBO.getPorId(usuario.getTipoSexo().getId()));
//		Usuario usuarioGerenciado = boUsuario.getPorEmail(usuario.getEmail());
//		if (usuarioGerenciado == null) {
//			usuario.setEmailConfirmado(true);
//			usuario.setDataCadastro(Calendar.getInstance());
//			usuario.setDesativado(false);
//			usuario.setQuantidadeTentativaLoginInvalido(0);
//			usuario.setTimezone(boTimeZone.getPorId(idTimeZoneBahia));
//			usuario.setIdioma(boIdioma.getPorId(idIdiomaPTBR));
//			usuarioGerenciado = boUsuario.salvarOuAtualizar(usuario);
//		}
//		return usuarioGerenciado;
//	}
//}
