package br.com.nuclearis.integracao.service.interfaces;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.nuclearis.integracao.dto.VagaPacienteDTO;

public interface IIntegracaoPassivaService extends Serializable {

	public @ResponseBody ResponseEntity<String> inserirLaudo(VagaPacienteDTO vagaPaciente);

	public @ResponseBody ResponseEntity<String> confirmarAtendimentos(List<VagaPacienteDTO> vagasPaciente);

	public @ResponseBody ResponseEntity<String> realizarAgendamento(List<VagaPacienteDTO> vagasPaciente);

}
