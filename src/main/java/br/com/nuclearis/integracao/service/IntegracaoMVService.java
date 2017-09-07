/**
 * 
 */
package br.com.nuclearis.integracao.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.nuclearis.integracao.business.interfaces.IIntegracaoMVBO;
import br.com.nuclearis.integracao.dto.AutorizacaoExameDTO;
import br.com.nuclearis.integracao.dto.ConvenioDTO;
import br.com.nuclearis.integracao.dto.IntegracaoRestParamDTO;
import br.com.nuclearis.integracao.dto.PacienteDTO;
import br.com.nuclearis.integracao.dto.PlanoDTO;
import br.com.nuclearis.integracao.dto.VagaPacienteDTO;
import br.com.nuclearis.integracao.exceptions.DAOException;
import br.com.nuclearis.integracao.service.interfaces.IntegracaoAtivaService;
import br.com.nuclearis.integracao.util.DataUtil;
import br.com.nuclearis.integracao.util.PropertyUtil;

/**
 * 
 * @author malbarbosa
 *
 */
@RestController("integracaoMVService")
public class IntegracaoMVService implements IntegracaoAtivaService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2906288875179501626L;

	private static Logger log = Logger.getLogger(IntegracaoMVService.class);

	@Resource(name = "boIntegracaoMV")
	private IIntegracaoMVBO boIntegracaoMV;

	@Override
	@RequestMapping(value = "/MV/realizarAgendamento", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> realizarAgendamento(@RequestBody List<VagaPacienteDTO> vagasPaciente) {
		try {
			if (vagasPaciente == null | vagasPaciente.isEmpty()) {
				return new ResponseEntity<String>("RequiredVagaPacienteIntegracao", HttpStatus.NOT_FOUND);
			}
			boIntegracaoMV.realizarAgendamento(vagasPaciente);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (DAOException e) {
			log.error("Falha ao consultar a lista de pacientes.", e);
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Falha ao consultar a lista de pacientes.", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/MV/cancelarAgendamento", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> cancelarAgendamento(@RequestBody List<VagaPacienteDTO> vagasPaciente) {
		try {
			if (vagasPaciente == null | vagasPaciente.isEmpty()) {
				return new ResponseEntity<String>("RequiredVagaPacienteIntegracao", HttpStatus.NOT_FOUND);
			}
			boIntegracaoMV.cancelarAgendamento(vagasPaciente);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (DAOException e) {
			log.error("Falha ao consultar a lista de pacientes.", e);
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Falha ao consultar a lista de pacientes.", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/MV/remarcarAgendamento", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> remarcarAgendamento(@RequestBody List<VagaPacienteDTO> vagasPaciente) {
		try {
			if (vagasPaciente == null | vagasPaciente.isEmpty()) {
				return new ResponseEntity<String>("RequiredVagaPacienteIntegracao", HttpStatus.NOT_FOUND);
			}
			boIntegracaoMV.remarcarAgendamento(vagasPaciente);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (DAOException e) {
			log.error("Falha ao consultar a lista de pacientes.", e);
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Falha ao consultar a lista de pacientes.", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//	@CrossOrigin(origins = "*")
	//	@RequestMapping(value = "/MV/quantidadeTotalPacientes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	//	public @ResponseBody ResponseEntity<Long> quantidadeTotalPacientes() {
	//		Long quantidade;
	//		try {
	//			quantidade = boIntegracaoMV.quantidadeTotalPacientes();
	//			return new ResponseEntity<Long>(quantidade, HttpStatus.OK);
	//		} catch (DAOException | QueryParametroInvalidoException e) {
	//			log.error("Falha ao consultar a lista de pacientes.", e);
	//			return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
	//		} catch (Exception e) {
	//			log.error("Falha ao consultar a lista de pacientes.", e);
	//			return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
	//		}
	//	}

	@Override
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/MV/consultarPacientes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<List<PacienteDTO>> consultarPacientes(@RequestBody IntegracaoRestParamDTO request) {
		List<PacienteDTO> pacientes;
		try {
			pacientes = boIntegracaoMV.consultarPacientes(request.getNomePaciente());
			return new ResponseEntity<List<PacienteDTO>>(pacientes, HttpStatus.OK);
		} catch (DAOException e) {
			log.error("Falha ao consultar a lista de pacientes.", e);
			return new ResponseEntity<List<PacienteDTO>>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Falha ao consultar a lista de pacientes.", e);
			return new ResponseEntity<List<PacienteDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/MV/consultarPlanosDoConvenio", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<List<PlanoDTO>> consultarPlanosDoConvenio(@RequestBody IntegracaoRestParamDTO parametros) {
		List<PlanoDTO> planos;
		try {
			planos = boIntegracaoMV.consultarPlanosPorConvenio(parametros.getIdConvenio(), parametros.getIdsEtapas());
			return new ResponseEntity<List<PlanoDTO>>(planos, HttpStatus.OK);
		} catch (DAOException e) {
			log.error("Falha ao consultar a lista de planos.", e);
			return new ResponseEntity<List<PlanoDTO>>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Falha ao consultar a lista de planos.", e);
			return new ResponseEntity<List<PlanoDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/MV/consultarConveniosPorExame", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<ConvenioDTO>> consultarConveniosPorExame(@RequestBody IntegracaoRestParamDTO parametros) {
		List<ConvenioDTO> convenios;
		try {

			convenios = boIntegracaoMV.consultarConveniosPorExame(parametros.getIdsEtapas());
			return new ResponseEntity<List<ConvenioDTO>>(convenios, HttpStatus.OK);
		} catch (DAOException e) {
			log.error("Falha ao consultar a lista de convênios.", e);
			return new ResponseEntity<List<ConvenioDTO>>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Falha ao consultar a lista de convênios.", e);
			return new ResponseEntity<List<ConvenioDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/MV/consultarAutorizacaoPlano", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<AutorizacaoExameDTO>> consultarAutorizacaoPlano(@RequestBody IntegracaoRestParamDTO parametros) {
		List<AutorizacaoExameDTO> autorizacoes = null;
		try {

			autorizacoes = boIntegracaoMV.consultarAutorizacaoPlano(parametros.getIdPlano(), parametros.getIdConvenio(), parametros.getIdsEtapas());
			return new ResponseEntity<List<AutorizacaoExameDTO>>(autorizacoes, HttpStatus.OK);
		} catch (DAOException e) {
			log.error("Falha ao consultar a lista de autorizações do plano e exame", e);
			return new ResponseEntity<List<AutorizacaoExameDTO>>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Falha ao consultar a lista de autorizações do plano e exame", e);
			return new ResponseEntity<List<AutorizacaoExameDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@RequestMapping(value = "/MV/inserirLaudo", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> inserirLaudo(@RequestBody VagaPacienteDTO vagaPaciente) {
		//TODO falta fazer
		try {

			boIntegracaoMV.inserirLaudo(vagaPaciente);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			log.error("Falha ao consultar a lista de convênios.", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//	@Override
	//	@RequestMapping(value = "/MV/consultarAtendimentos", method = RequestMethod.POST)
	//	public @ResponseBody ResponseEntity<List<VagaPacienteDTO>> consultarAtendimentos(@RequestBody List<VagaPacienteDTO> atendimentos) {
	//		List<VagaPacienteDTO> atendimentosRecepcao = null;
	//		try {
	//
	//			//consultar os agendamentos do dia
	//			RestTemplate restTemplate = new RestTemplate();
	//			ResponseEntity<VagaPacienteDTO[]> agendamentosDoDia = restTemplate.getForEntity("http://localhost:8080/consultarAgendamentosDoDia", VagaPacienteDTO[].class);
	//			List<VagaPacienteDTO> listaAgendamentosDoDia = new ArrayList<VagaPacienteDTO>();
	//			if (agendamentosDoDia.getBody() != null && agendamentosDoDia.getBody().length > 0) {
	//				listaAgendamentosDoDia.addAll(Arrays.asList(agendamentosDoDia.getBody()));
	//			}
	//
	//			atendimentosRecepcao = boIntegracaoMV.consultarAtendimentos(atendimentos, listaAgendamentosDoDia);
	//
	//			return new ResponseEntity<List<VagaPacienteDTO>>(atendimentosRecepcao, HttpStatus.OK);
	//		} catch (Exception e) {
	//			log.error("Falha ao consultar a lista de convênios.", e);
	//			return new ResponseEntity<List<VagaPacienteDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
	//		}
	//	}

	@Override
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/MV/consultarVaga", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<BigDecimal> consultarVaga(@RequestBody IntegracaoRestParamDTO parametros) {
		BigDecimal cdIntegracao = BigDecimal.ZERO;
		try {

			Calendar calendar = DataUtil.stringDdMmAaaaHhMmSsToCalendar(parametros.getDataHoraVaga(), "UTC");
			Date data = DataUtil.convertDateToDateInTimeZone(calendar.getTime(), parametros.getTimezoneUsuario());
			calendar.setTimeInMillis(data.getTime());

			cdIntegracao = BigDecimal.valueOf(boIntegracaoMV.consultarVaga(calendar));
			return new ResponseEntity<BigDecimal>(cdIntegracao, HttpStatus.OK);
		} catch (DAOException e) {
			log.error("Falha ao consultar o có	digo da vaga.", e);
			return new ResponseEntity<BigDecimal>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Falha ao consultar o código da vaga.", e);
			return new ResponseEntity<BigDecimal>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/MV/confirmarAtendimentos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

				List<VagaPacienteDTO> atendimentosRecepcao = boIntegracaoMV.consultarAtendimentos(atendimentosDoDia, listaAgendamentosDoDia);

				ResponseEntity<String> result = restTemplate.postForEntity(PropertyUtil.getUriHostNuclearis().concat("/confirmarAtendimentos"), atendimentosRecepcao, String.class);

				return new ResponseEntity<String>(result.getBody(), result.getStatusCode());
			}
			return new ResponseEntity<String>(HttpStatus.CONTINUE);
		} catch (Exception e) {
			log.error("Falha ao confirmar Atendimentos.", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
