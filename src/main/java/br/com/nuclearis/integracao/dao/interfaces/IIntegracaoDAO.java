package br.com.nuclearis.integracao.dao.interfaces;

import br.com.nuclearis.integracao.dto.IntegracaoDTO;
import br.com.nuclearis.integracao.exceptions.DAOException;

public interface IIntegracaoDAO extends IDAOGenericoInt<IntegracaoDTO> {

	void inserirLaudo(Long cdAtendimento, Byte[] laudo) throws DAOException;

}
