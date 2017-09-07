package br.com.nuclearis.integracao.util;

public class PropertyUtil {

	private static String hostNuclearis;

	private static String portaNuclearis;
	private static String viewPacientes;
	private static String viewVagas;
	private static String viewProibicoes;
	private static String viewConvenios;
	private static String viewPlanos;
	private static String tabelaAgendamentos;
	private static String tabelaAtendimentos;
	private static String contextNuclearis;

	public static String getContextNuclearis() {
		return contextNuclearis;
	}

	public static void setArguments(String hostNuclearis, String portaNuclearis, String contextNuclearis, String viewPacientes, String viewVagas, String viewProibicoes, String viewConvenios, String viewPlanos, String tabelaAgendamentos,
			String tabelaAtendimentos) {
		PropertyUtil.hostNuclearis = hostNuclearis;
		PropertyUtil.portaNuclearis = portaNuclearis;
		PropertyUtil.contextNuclearis = contextNuclearis;
		PropertyUtil.viewPacientes = viewPacientes;
		PropertyUtil.viewVagas = viewVagas;
		PropertyUtil.viewProibicoes = viewProibicoes;
		PropertyUtil.viewConvenios = viewConvenios;
		PropertyUtil.viewPlanos = viewPlanos;
		PropertyUtil.tabelaAgendamentos = tabelaAgendamentos;
		PropertyUtil.tabelaAtendimentos = tabelaAtendimentos;
	}

	public static String getUriHostNuclearis() {
		return hostNuclearis.concat(":").concat(portaNuclearis).concat("/").concat(contextNuclearis);
	}

	public static String getViewPacientes() {
		return viewPacientes;
	}

	public static String getViewVagas() {
		return viewVagas;
	}

	public static String getViewProibicoes() {
		return viewProibicoes;
	}

	public static String getViewConvenios() {
		return viewConvenios;
	}

	public static String getViewPlanos() {
		return viewPlanos;
	}

	public static String getTabelaAgendamentos() {
		return tabelaAgendamentos;
	}

	public static String getTabelaAtendimentos() {
		return tabelaAtendimentos;
	}

}
