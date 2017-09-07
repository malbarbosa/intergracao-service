package br.com.nuclearis.integracao.business.interfaces;

import java.util.Set;

public interface IGerenciadorIntegracaoBO_old {

	Set<Long> iniciarAtendimentos();

	Set<Long> realizarAgendamentos();

	void verificarCancelamentos(Set<Long> listaCdIntegracao);

}
