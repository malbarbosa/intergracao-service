package br.com.nuclearis.integracao.service.interfaces;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.nuclearis.integracao.dto.AutorizacaoExameDTO;
import br.com.nuclearis.integracao.dto.ConvenioDTO;
import br.com.nuclearis.integracao.dto.IntegracaoRestParamDTO;
import br.com.nuclearis.integracao.dto.PacienteDTO;
import br.com.nuclearis.integracao.dto.PlanoDTO;

public interface IntegracaoAtivaService extends IIntegracaoPassivaService {

	@ResponseBody
	ResponseEntity<BigDecimal> consultarVaga(IntegracaoRestParamDTO parametros);

	@ResponseBody
	ResponseEntity<List<PacienteDTO>> consultarPacientes(@RequestBody IntegracaoRestParamDTO request);

	@ResponseBody
	ResponseEntity<List<PlanoDTO>> consultarPlanosDoConvenio(@RequestBody IntegracaoRestParamDTO parametros);

	@ResponseBody
	ResponseEntity<List<ConvenioDTO>> consultarConveniosPorExame(@RequestBody IntegracaoRestParamDTO parametros);

	@ResponseBody
	ResponseEntity<List<AutorizacaoExameDTO>> consultarAutorizacaoPlano(@RequestBody IntegracaoRestParamDTO parametros);

}
