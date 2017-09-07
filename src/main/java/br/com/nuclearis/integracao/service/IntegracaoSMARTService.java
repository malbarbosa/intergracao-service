package br.com.nuclearis.integracao.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.nuclearis.integracao.business.interfaces.IIntegracaoBO;
import br.com.nuclearis.integracao.dto.VagaPacienteDTO;
import br.com.nuclearis.integracao.service.interfaces.IIntegracaoPassivaService;
import br.com.nuclearis.integracao.util.PropertyUtil;

@RestController("integracaoSMARTService")
public class IntegracaoSMARTService implements IIntegracaoPassivaService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3287446935114998741L;

	private static Logger log = Logger.getLogger(IntegracaoSMARTService.class);

	@Resource(name = "boIntegracaoSMART")
	private IIntegracaoBO boIntegracaoPassiva;

	@Override
	public ResponseEntity<String> inserirLaudo(@RequestBody VagaPacienteDTO vagaPaciente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> confirmarAtendimentos(@RequestBody List<VagaPacienteDTO> atendimentosDoDia) {
		try {
			if (atendimentosDoDia != null && !atendimentosDoDia.isEmpty()) {

				//consultar os agendamentos do dia
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<VagaPacienteDTO[]> agendamentosDoDia = restTemplate.getForEntity(PropertyUtil.getUriHostNuclearis().concat("/consultarAgendamentosDoDia"), VagaPacienteDTO[].class);
				List<VagaPacienteDTO> listaAgendamentosDoDia = new ArrayList<VagaPacienteDTO>();
				if (agendamentosDoDia.getBody() != null && agendamentosDoDia.getBody().length > 0) {
					listaAgendamentosDoDia.addAll(Arrays.asList(agendamentosDoDia.getBody()));
				}

				List<VagaPacienteDTO> atendimentosRecepcao = boIntegracaoPassiva.consultarAtendimentos(atendimentosDoDia, listaAgendamentosDoDia);

				ResponseEntity<String> result = restTemplate.postForEntity(PropertyUtil.getUriHostNuclearis().concat("/confirmarAtendimentos"), atendimentosRecepcao, String.class);

				return new ResponseEntity<String>(result.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<String>(HttpStatus.CONTINUE);
		} catch (Exception e) {
			log.error("Falha ao consultar a lista de convênios.", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<String> realizarAgendamento(@RequestBody List<VagaPacienteDTO> agendamentosDoDia) {
		try {
			if (agendamentosDoDia != null && !agendamentosDoDia.isEmpty()) {

				//consultar os agendamentos do dia
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<String> result = restTemplate.getForEntity(PropertyUtil.getUriHostNuclearis().concat("/confirmarAgendamentos"), String.class);
				if (!result.getStatusCode().equals(HttpStatus.OK)) {
					return new ResponseEntity<String>(result.getBody(), HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<String>(result.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<String>(HttpStatus.CONTINUE);
		} catch (Exception e) {
			log.error("Falha ao consultar a lista de convênios.", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
