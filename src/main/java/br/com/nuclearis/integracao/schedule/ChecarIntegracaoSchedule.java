package br.com.nuclearis.integracao.schedule;

import org.apache.log4j.Logger;

import br.com.nuclearis.integracao.business.interfaces.IGerenciadorIntegracaoBO_old;

//@Component
public class ChecarIntegracaoSchedule {

	private static Logger log = Logger.getLogger(ChecarIntegracaoSchedule.class);

	//TODO necessário informar qual a implementacao deverá ser usada para a integracao desejada. Ex.: boGerenciadorIntegracaoSmart ou boGerenciadorIntegracaoMV
	//Para isso, basta ajustar o @Resource para @Resource(name=NomeDaIntegracao) 
	//	@Resource(name = "boGerenciadorIntegracaoSmart")
	private IGerenciadorIntegracaoBO_old boGerenciadorIntegracao;

	// -> Run every 1 minute from 5 AM at 23 PM daily
	//	@Scheduled(cron = "0 */1 5-23 * * MON-FRI", zone = "GMT")
	//	@Scheduled(cron = "0 */1 1-23 * * *", zone = "GMT")
	private void checarIntegracao() {
		//		try {
		//			log.warn("Iniciou");
		//			Set<Long> cdIntegracaoAgendamentos = boGerenciadorIntegracao.consultarAgendamentos();
		//			Set<Long> cdIntegracaoAtendimentos = boGerenciadorIntegracao.consultarAtendimentos();
		//			Set<Long> listaCdsIntegracao = new HashSet<Long>(cdIntegracaoAgendamentos);
		//			listaCdsIntegracao.addAll(cdIntegracaoAtendimentos);
		//			boGerenciadorIntegracao.verificarCancelamentos(listaCdsIntegracao);
		//			log.warn("Finalizou");
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//			log.error("Falha ao tentar recuperar a lista de agendamentos.", e);
		//		}
	}

}
