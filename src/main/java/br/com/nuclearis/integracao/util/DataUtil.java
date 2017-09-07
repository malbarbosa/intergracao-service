package br.com.nuclearis.integracao.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

public class DataUtil {

	private static String calendarToString(Calendar c, String modelo, String siglaPais, String locale) {

		if (c == null) {
			return "";
		}

		String timezone = "";

		if (c.getTimeZone() != null && c.getTimeZone().getID() == null) {
			timezone = "UTC";
		} else {
			timezone = c.getTimeZone().getID();
		}

		String data = "";

		if (c != null) {
			final Locale pais = new Locale(locale != null ? locale : "pt", siglaPais != null ? siglaPais : "BR");
			SimpleDateFormat formatoData = new SimpleDateFormat(modelo, pais);
			formatoData.setTimeZone(TimeZone.getTimeZone(timezone));
			data = formatoData.format(c.getTime());
		}

		return data;
	}

	private static Calendar stringToCalendar(String data, String modelo, String timezone) throws ParseException {

		if (timezone == null || timezone.trim().length() == 0) {
			timezone = "UTC";
		}

		SimpleDateFormat formatoData = new SimpleDateFormat(modelo);
		formatoData.setLenient(true);
		formatoData.setTimeZone(TimeZone.getTimeZone(timezone));

		Calendar novaData = Calendar.getInstance(TimeZone.getTimeZone(timezone));

		if (StringUtils.isNotEmpty(data)) {
			try {
				novaData.setTime(formatoData.parse(data));
			} catch (Exception e) {
				throw new ParseException("Formato de data inválido. [data=" + data + ", modelo=" + modelo + ", timezone=" + timezone + "]", 1);
			}

			return novaData;
		} else {
			return null;
		}

	}

	/*
	 * 
	 * Conversão de Calendar em String
	 */
	public static String calendarToStringHhmm(Calendar c) {
		String modeloData = "HH:mm";
		return calendarToString(c, modeloData, null, null);
	}

	public static String calendarToStringMmSs(Calendar c) {
		String modeloData = "mm:ss";
		return calendarToString(c, modeloData, null, null);
	}

	public static String calendarToStringHhmmss(Calendar c) {
		String modeloData = "HH:mm:ss";
		return calendarToString(c, modeloData, null, null);
	}

	public static String calendarToStringDd(Calendar c) {
		String modeloData = "dd";
		return calendarToString(c, modeloData, null, null);
	}

	public static String calendarToStringDdMmAaaa(Calendar c) {
		String modeloData = "dd/MM/yyyy";
		return calendarToString(c, modeloData, null, null);
	}

	public static String calendarToStringDdMm(Calendar c) {
		String modeloData = "dd/MM";
		return calendarToString(c, modeloData, null, null);
	}

	public static String calendarToStringDdMmAaaaHhMm(Calendar c) {
		String modeloData = "dd/MM/yyyy HH:mm";
		return calendarToString(c, modeloData, null, null);
	}

	public static String calendarToStringDdMmAaaaHhmmss(Calendar c) {
		String modeloData = "dd/MM/yyyy HH:mm:ss";
		return calendarToString(c, modeloData, null, null);
	}

	public static String calendarToStringDdMmAaaaHhmm(Calendar c) {
		String modeloData = "dd/MM/yyyy HH:mm";
		return calendarToString(c, modeloData, null, null);
	}

	public static String calendarToStringDdMmAaaaHhmmssZ(Calendar c) {
		String modeloData = "dd/MM/yyyy HH:mm:ss z";
		return calendarToString(c, modeloData, null, null);
	}

	public static String calendarToStringAaaaMm(Calendar c) {
		String modeloData = "yyyy-MM";
		return calendarToString(c, modeloData, null, null);
	}

	public static String calendarToStringAaaa(Calendar c) {
		String modeloData = "yyyy";
		return calendarToString(c, modeloData, null, null);
	}

	public static String calendarToStringYyyymm(Calendar c) {
		String modeloData = "yyyy-MM";
		return calendarToString(c, modeloData, null, null);
	}

	public static String calendarToStringYyyymmdd(Calendar c) {
		String modeloData = "yyyy-MM-dd";
		return calendarToString(c, modeloData, null, null);
	}

	public static String calendarToStringYyyymmddHhmmss(Calendar c) {
		String modeloData = "yyyy-MM-dd HH:mm:ss";
		return calendarToString(c, modeloData, null, null);
	}

	public static String calendarToStringHhmmssDdMmYyyy(Calendar c) {
		String modeloData = "HH:mm:ss dd/MM/yyyy";
		return calendarToString(c, modeloData, null, null);
	}

	public static String calendarToStringDiaDaSemanaCompleto(Calendar c, String siglaPais, String locale) {
		String modeloData = "EEEE";
		return calendarToString(c, modeloData, siglaPais, locale);
	}

	public static String calendarToStringDiaDaSemanaTresLetras(Calendar c, String siglaPais, String locale) {
		String modeloData = "EEE";
		return calendarToString(c, modeloData, siglaPais, locale);
	}

	public static String calendarToStringDdDeMesDeAaaaAsHhMm(Calendar c, String siglaPais, String locale, String modeloData) {
		return calendarToString(c, modeloData, siglaPais, locale);
	}

	public static String calendarToStringMes(Calendar c, String siglaPais, String locale) {
		String modeloData = "MMMM";
		return calendarToString(c, modeloData, siglaPais, locale);
	}

	/**
	 * 
	 * Converte um Calendar em String. Ex.: Se o calendar for "02/01/1970 10:20:00" será retornado "34:20".
	 */
	public static String calendarToStringTotalHHmm(Calendar calendar) {
		long intervaloInMillis = calendar.getTimeInMillis();

		long horas = TimeUnit.MILLISECONDS.toHours(intervaloInMillis);
		long minutos = TimeUnit.MILLISECONDS.toMinutes(intervaloInMillis) % 60;

		String tempo = (horas < 10 ? "0" + horas : horas) + ":" + (minutos < 10 ? "0" + minutos : minutos);

		return tempo;
	}

	/**
	 * 
	 * Converte um Calendar em String. Ex.: Se o calendar for "02/01/1970 10:20:00" será retornado "34:20".
	 */
	public static String calendarToStringTotalHHHmm(Calendar calendar) {
		long intervaloInMillis = calendar.getTimeInMillis();

		long horas = TimeUnit.MILLISECONDS.toHours(intervaloInMillis);
		long minutos = TimeUnit.MILLISECONDS.toMinutes(intervaloInMillis) % 60;

		String tempo = (horas < 10 ? "00" + horas : horas > 10 && horas < 99 ? "0" + horas : horas) + ":" + (minutos < 10 ? "0" + minutos : minutos);

		return tempo;
	}

	public static String calendarToStringTotalMmSs(Calendar calendar) {
		long intervaloInMillis = calendar.getTimeInMillis();

		long minutos = TimeUnit.MILLISECONDS.toMinutes(intervaloInMillis) % 60;
		long segundos = TimeUnit.MILLISECONDS.toSeconds(intervaloInMillis) % 60 % 60;

		String tempo = (minutos < 10 ? "0" + minutos : minutos) + ":" + (segundos < 10 ? "0" + segundos : segundos);

		return tempo;
	}

	public static String calendarToStringHoras(Calendar calendar) {
		long intervaloInMillis = calendar.getTimeInMillis();

		long horas = TimeUnit.MILLISECONDS.toHours(intervaloInMillis);

		String tempo = (horas < 10 ? "0" + horas : String.valueOf(horas));

		return tempo;
	}

	public static String calendarToStringMinutos(Calendar calendar) {
		long intervaloInMillis = calendar.getTimeInMillis();

		long minutes = TimeUnit.MILLISECONDS.toMinutes(intervaloInMillis);

		String time = (minutes < 10 ? "0" + minutes : String.valueOf(minutes));

		return time;
	}

	/*
	 * 
	 * Converter Date para String
	 */

	public static String dateToStringDdMmAaaa(Date d, String timezone) {
		return calendarToStringDdMmAaaa(convertDateToCalendar(d, timezone));
	}

	public static String dateToStringDdMmAaaaHhmmss(Date d, String timezone) {
		return calendarToStringDdMmAaaaHhmmss(convertDateToCalendar(d, timezone));
	}

	public static String dateToStringDdMmAaaaHhmm(Date d, String timezone) {
		return calendarToStringDdMmAaaaHhmm(convertDateToCalendar(d, timezone));
	}

	public static String dateToStringDdMmAaaaHhmmssZ(Date d, String timezone) {
		return calendarToStringDdMmAaaaHhmmssZ(convertDateToCalendar(d, timezone));
	}

	public static String dateToStringHhmmss(Date d, String timezone) {
		return calendarToStringHhmmss(convertDateToCalendar(d, timezone));
	}

	public static String dateToStringHhmm(Date d, String timezone) {
		return calendarToStringHhmm(convertDateToCalendar(d, timezone));
	}

	/*
	 * 
	 * Conversão de String para Calendar
	 */
	public static Calendar stringHhMmToCalendar(String d, String timezone) throws ParseException {
		String modeloData = "HH:mm";
		return stringToCalendar(d, modeloData, timezone);
	}

	public static Calendar stringMmSsToCalendar(String d, String timezone) throws ParseException {
		String modeloData = "mm:ss";
		return stringToCalendar(d, modeloData, timezone);
	}

	public static Calendar stringDdMmAaaaToCalendar(String d, String timezone) throws ParseException {
		String modeloData = "dd/MM/yyyy";
		Calendar c = stringToCalendar(d, modeloData, timezone);
		return c;
	}

	public static Calendar stringDdMmAaaaHhMmSsToCalendar(String d, String timezone) throws ParseException {
		String modeloData = "dd/MM/yyyy HH:mm:ss";
		return stringToCalendar(d, modeloData, timezone);
	}

	public static Calendar stringAaMmDdHhMmToCalendar(String d, String timezone) throws ParseException {
		String modeloData = "yyMMddHHmm";
		Calendar c = stringToCalendar(d, modeloData, timezone);
		return c;
	}

	public static Calendar stringMmDdAaaaHhMmSsToCalendar(String d, String timezone) throws ParseException {
		String modeloData = "MM/dd/yyyy HH:mm:ss";
		return stringToCalendar(d, modeloData, timezone);
	}

	public static Calendar stringDdMmAaaaHhMmToCalendar(String d, String timezone) throws ParseException {
		String modeloData = "dd/MM/yyyy HH:mm";
		return stringToCalendar(d, modeloData, timezone);
	}

	public static Calendar stringAaaaMmDdToCalendar(String d, String timezone) throws ParseException {
		String modeloData = "yyyy-MM-dd";
		Calendar c = stringToCalendar(d, modeloData, timezone);
		return c;
	}

	public static Calendar stringAaaaMmDdHhMmSsToCalendar(String d, String timezone) throws ParseException {
		String modeloData = "yyyy-MM-dd HH:mm:ss";
		return stringToCalendar(d, modeloData, timezone);
	}

	public static Calendar setHoraInicioDia(Calendar c) {
		if (c != null) {
			Calendar atual = c;
			c.set(atual.get(Calendar.YEAR), atual.get(Calendar.MONTH), atual.get(Calendar.DATE), 0, 0, 0);
			c.set(Calendar.MILLISECOND, 0);
		}

		return c;
	}

	public static Calendar setHoraInicioDia(Date d, String timezone) {
		Calendar calendar = setHoraInicioDia(convertDateToCalendar(d, timezone));
		try {
			calendar = convertDateToCalendar(convertDateToDateInTimeZone(calendar.getTime(), timezone), timezone);
		} catch (ParseException e) {
			return null;
		}

		return calendar;
	}

	public static Calendar setHoraMeioDia(Calendar c) {
		if (c != null) {
			Calendar atual = c;
			c.set(atual.get(Calendar.YEAR), atual.get(Calendar.MONTH), atual.get(Calendar.DATE), 12, 0, 0);
			c.set(Calendar.MILLISECOND, 0);
		}

		return c;
	}

	public static Calendar setHoraMeioDia(Date d, String timezone) {
		Calendar calendar = setHoraMeioDia(convertDateToCalendar(d, timezone));
		try {
			calendar = convertDateToCalendar(convertDateToDateInTimeZone(calendar.getTime(), timezone), timezone);
		} catch (ParseException e) {
			return null;
		}

		return calendar;
	}

	public static Calendar setHoraFimDia(Calendar c) {
		if (c != null) {
			Calendar atual = c;
			c.set(atual.get(Calendar.YEAR), atual.get(Calendar.MONTH), atual.get(Calendar.DATE), 23, 59, 59);
		}

		return c;
	}

	public static Calendar setHoraFimDia(Date d, String timezone) {
		Calendar calendar = setHoraFimDia(convertDateToCalendar(d, timezone));

		if (timezone != null) {
			calendar = convertDateToCalendar(calendar.getTime(), null);
		}

		return calendar;
	}

	public static int diferencaCalendarEmSegundo(Calendar inicio, Calendar fim) {

		long m1 = inicio.getTimeInMillis() / 1000; // convertCalendarInMillis(inicio);
		long m2 = fim.getTimeInMillis() / 1000; // convertCalendarInMillis(fim);

		long x = ((m2 - m1));

		return Integer.valueOf(x + "").intValue();
	}

	public static int diferencaCalendarEmMilisegundo(Calendar inicio, Calendar fim) {

		long m1 = convertCalendarInMillis(inicio);
		long m2 = convertCalendarInMillis(fim);

		long x = m2 - m1;

		return Integer.valueOf(x + "").intValue();
	}

	public static int diferencaCalendarEmMinuto(Calendar inicio, Calendar fim) {

		long m1 = convertCalendarInMillis(inicio);
		long m2 = convertCalendarInMillis(fim);

		long x = ((m2 - m1) / (60 * 1000));

		return Integer.valueOf(x + "").intValue();
	}

	public static int diferencaCalendarEmHora(Calendar inicio, Calendar fim) {

		long m1 = convertCalendarInMillis(inicio);
		long m2 = convertCalendarInMillis(fim);

		long x = ((m2 - m1) / (60 * 60 * 1000));

		return Integer.valueOf(x + "").intValue();
	}

	public static int diferencaCalendarEmDia(Calendar inicio, Calendar fim) {

		long m1 = convertCalendarInMillis(inicio);
		long m2 = convertCalendarInMillis(fim);

		long x = ((m2 - m1) / 1000 / 60 / 60 / 24);

		return Integer.valueOf(x + "").intValue();
	}

	public static int diferencaCalendarEmSemana(Calendar inicio, Calendar fim) {

		long m1 = convertCalendarInMillis(inicio);
		long m2 = convertCalendarInMillis(fim);

		long x = ((m2 - m1) / 1000 / 60 / 60 / 24 / 7);

		return Integer.valueOf(x + "").intValue();
	}

	public static int diferencaCalendarEmDiaIgnorandoHorario(Calendar inicio, Calendar fim) {

		long m1 = convertCalendarInMillis(setHoraInicioDia(inicio));
		long m2 = convertCalendarInMillis(setHoraInicioDia(fim));

		long x = ((m2 - m1) / 1000 / 60 / 60 / 24);

		return Integer.valueOf(x + "").intValue();
	}

	public static int diferencaCalendarEmMinutoIgnorandoData(Calendar dataInicio, Calendar dataFim) {

		if (dataInicio != null && dataFim != null) {

			Calendar inicio = (Calendar) dataInicio.clone();
			Calendar fim = (Calendar) dataFim.clone();

			inicio.set(1111, 11, 11, dataInicio.get(Calendar.HOUR_OF_DAY), dataInicio.get(Calendar.MINUTE), dataInicio.get(Calendar.SECOND));
			fim.set(1111, 11, 11, dataFim.get(Calendar.HOUR_OF_DAY), dataFim.get(Calendar.MINUTE), dataFim.get(Calendar.SECOND));

			return diferencaCalendarEmMinuto(inicio, fim);

		} else {
			return 0;
		}

	}

	public static Calendar convertDateToCalendar(Date d, String timezone) {

		if (timezone == null) {
			timezone = "UTC";
		}

		SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String data = formatoData.format(d);

		Calendar c;
		try {
			c = stringToCalendar(data, "yyyy-MM-dd HH:mm:ss", timezone);
		} catch (ParseException e) {
			return null;
		}

		return c;
	}

	public static Calendar copiarHorarioDeDateParaCalendar(Date horario, Calendar c) {
		int ano = c.get(Calendar.YEAR);
		int mes = c.get(Calendar.MONTH);
		int dia = c.get(Calendar.DATE);

		c.setTimeInMillis(horario.getTime());

		c.set(ano, mes, dia);

		return c;
	}

	public static Calendar getDataInicioSemanaContemData(Calendar data) {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		c.clear();
		c.setFirstDayOfWeek(1);
		data.setFirstDayOfWeek(1);
		c.set(Calendar.WEEK_OF_YEAR, data.get(Calendar.WEEK_OF_YEAR));
		c.set(Calendar.YEAR, data.get(Calendar.YEAR));
		return c;
	}

	public static Float converterUTCtoFloat(String utc) throws Exception {

		Float tempo = 0f;
		try {
			String[] utcPart = utc.trim().split(":");
			String sinal = utcPart[0].substring(0, 1);
			String horas = utcPart[0].substring(1, 3);
			String minutos = utcPart[1];

			tempo = Float.valueOf(horas).floatValue() + (Float.valueOf(minutos).floatValue() / 60);
			if (sinal.equals("−") || sinal.equals("-")) {
				tempo = tempo * -1;
			}
		} catch (Exception e) {
			throw new Exception("Falha na conversão do UTC. Detalhes: " + e.getMessage());
		}

		return tempo;
	}

	public static Long convertCalendarInMillis(Calendar c) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, c.get(Calendar.MILLISECOND));
		return calendar.getTimeInMillis();

	}

	public static Calendar getCalendarUTC() {
		return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	}

	public static Calendar getCalendarUTCZerado() {
		Calendar calendar = getCalendarUTC();
		calendar.setTime(new Date(0));

		return calendar;
	}

	public static Date convertDateWithTimezoneToDateUTC(Date d, String timezone) throws ParseException {
		TimeZone timeZone = TimeZone.getTimeZone(timezone);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		formatter.setTimeZone(timeZone);

		String data = DataUtil.dateToStringDdMmAaaaHhmmssZ(d, timeZone.getID());

		Date dataUTC;
		try {

			dataUTC = formatter.parse(data);

		} catch (ParseException e) {
			throw new ParseException("Falha na conversão de data para data com timezone.", 1);
		}

		return dataUTC;
	}

	public static Calendar convertDateWithTimezoneToCalendarUTC(Date d, String timezone) throws ParseException {

		Date dataInUTC = convertDateWithTimezoneToDateUTC(d, timezone);

		return convertDateToCalendar(dataInUTC, "UTC");
	}

	public static String convertDateToStringInTimeZone(Date data, String timezone) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone(timezone));
		return sdf.format(data);
	}

	public static String convertDateToStringInTimeZone(Date data, String dataFormato, String timezone) {
		SimpleDateFormat sdf = new SimpleDateFormat(dataFormato);
		sdf.setTimeZone(TimeZone.getTimeZone(timezone));
		return sdf.format(data);
	}

	public static Date convertDateToDateInTimeZone(Date date, String timezone) throws ParseException {
		String data = convertDateToStringInTimeZone(date, timezone);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		try {
			return sdf.parse(data);
		} catch (ParseException e) {
			throw new ParseException("Falha na conversão de data para data com timezone.", 1);
		}
	}

	public static String getGMTString(Float utcUsuario) {
		Integer utc = (int) ((float) utcUsuario);

		String gmtString = "GMT";

		if (utc > 0) {
			gmtString += "+" + utc;
		} else if (utc < 0) {
			gmtString += utc;
		}

		return gmtString;
	}

	public static Calendar acrescentarDias(Date data, int dias, String timezone) {
		Calendar calendar = convertDateToCalendar(data, timezone);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + dias);

		return calendar;
	}

	public static Date acrescentarHoras(Date data, int horas, String timezone) {
		Calendar calendar = convertDateToCalendar(data, timezone);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + horas);

		return calendar.getTime();
	}

	public static Calendar acrescentarMinutos(Date data, int minutos, String timezone) {
		Calendar calendar = convertDateToCalendar(data, timezone);
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minutos);

		return calendar;
	}

	public static Calendar getCalendarUsuario(String timezone) throws ParseException {
		Date date = new Date();
		date = convertDateToDateInTimeZone(date, timezone);

		return convertDateToCalendar(date, null);
	}

	/**
	 * Metodo que retorna a idade em relação a data de nascimento
	 * 
	 * @param dataNascimento
	 * @return int - idade da pessoa
	 */
	public static int getIdadePorDataNascimento(Calendar dataNascimento) {

		if (dataNascimento == null) {
			return 0;
		}

		int idadePaciente = 0;

		int anoNascimento = dataNascimento.get(Calendar.YEAR);
		int mesNascimento = dataNascimento.get(Calendar.MONTH) + 1;
		int diaNascimento = dataNascimento.get(Calendar.DAY_OF_MONTH);

		Calendar now = Calendar.getInstance();
		int anoAtual = now.get(Calendar.YEAR);
		int mesAtual = now.get(Calendar.MONTH) + 1;
		int diaAtual = now.get(Calendar.DAY_OF_MONTH);

		if (mesAtual >= mesNascimento) {
			if (diaAtual >= diaNascimento) {
				idadePaciente = anoAtual - anoNascimento;
			} else {
				idadePaciente = anoAtual - anoNascimento - 1;
			}

		} else {
			idadePaciente = anoAtual - anoNascimento - 1;
		}

		return idadePaciente;

	}

	/**
	 * Metodo que retorna um Calendar zerado (01/01/1970) + o tempo passado como parametro em minutos. (Ex.: 109 min = 01/01/1970 01:49:00)
	 * 
	 * @param min
	 * @return Calendar - Data zerada mais o minuto passado como parametro
	 */
	public static Calendar getDataZeradaPorMinuto(Float min) {
		Calendar cal = Calendar.getInstance();

		int segundos = (int) (min * 60);

		cal.clear(); // para limpar o campo hora
		cal.set(Calendar.SECOND, segundos);

		return cal;
	}

	/**
	 * Metodo que retorna um Calendar zerado (01/01/1970) + o tempo passado como parametro em horas. (Ex.: 6,02 horas = 01/01/1970 06:01:12)
	 * 
	 * @param hora
	 * @return Calendar - Data zerada mais a hora passada como parametro
	 */
	public static Calendar getDataZeradaPorHora(Float hora) {
		Calendar cal = Calendar.getInstance();

		int segundos = (int) (hora * 60 * 60);

		cal.clear(); // para limpar o campo hora
		cal.set(Calendar.SECOND, segundos);

		return cal;
	}

	/**
	 * Metodo que retorna um Calendar zerado (01/01/1970) + o tempo passado como parametro em dias. (Ex.: 14,28 dias = 15/01/1970 06:43:12)
	 * 
	 * @param dia
	 * @return Calendar - Data zerada mais o dia passado como parametro
	 */
	public static Calendar getDataZeradaPorDia(Float dia) {
		Calendar cal = Calendar.getInstance();

		int segundos = (int) (dia * 24 * 60 * 60);

		cal.clear(); // para limpar o campo hora
		cal.set(Calendar.SECOND, segundos);

		return cal;
	}

	public static Calendar calendarToCalendarDdMmAaaa(Calendar c, String timezone) throws ParseException {
		return stringDdMmAaaaToCalendar(calendarToStringDdMmAaaa(c), timezone);
	}

	public static Calendar getCalendarInicioSemana(String timezone) throws ParseException {
		Calendar cal = setHoraInicioDia(getCalendarUTC());
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

		return convertDateToCalendar(cal.getTime(), timezone);
	}

	public static Calendar getCalendarUTCInicioMes(Calendar date) {
		Calendar cal = setHoraInicioDia(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

		return cal;
	}

	public static Calendar getCalendarUTCFimMes(Calendar date) {
		Calendar cal = setHoraFimDia(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

		return cal;
	}

	public static boolean datasDiferentes(final Date dataInicial, final Date dataFinal, final String nomeTimeZoneUsuario) throws ParseException {

		// -> Pega a data atual do servidor e converte para a data no timezone do usuário
		Date dataAtual = DataUtil.convertDateToDateInTimeZone(Calendar.getInstance().getTime(), nomeTimeZoneUsuario);

		// -> Remove o time, deixando apenas a data para comparar com a data de validade.
		Calendar calendarSemHoras = DataUtil.setHoraInicioDia(dataAtual, "UTC");
		Calendar calendarInicial = DataUtil.setHoraInicioDia(dataInicial, "UTC");
		Calendar calendarFinal = DataUtil.setHoraInicioDia(dataFinal, "UTC");

		if (calendarInicial.equals(calendarSemHoras) && calendarFinal.equals(calendarSemHoras)) {
			return false;
		}

		return true;

	}

	public static String getTimeZoneSistema() {
		return ZoneId.systemDefault().getId();
	}

	public static void main(String[] args) {
		Double valor = 5040d;
		Locale locale = new Locale("pt", "BR");
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(3);

		System.out.println(df.format(valor));
		//		System.out.println(NumberFormat.getNumberInstance(locale).format(valor));
	}

	public static Calendar getCalendarSemHorario(Long timeInMillis) {
		Calendar calInicioSemHoras = Calendar.getInstance();
		calInicioSemHoras.setTimeInMillis(timeInMillis);
		calInicioSemHoras.set(Calendar.MILLISECOND, 0);
		calInicioSemHoras.set(Calendar.SECOND, 0);
		calInicioSemHoras.set(Calendar.MINUTE, 0);
		calInicioSemHoras.set(Calendar.HOUR_OF_DAY, 0);

		return calInicioSemHoras;
	}

	public static Timestamp stringToSqlTimestamp(String data) {
		try {
			DateFormat formatter;
			formatter = new SimpleDateFormat("dd/MM/yyyy");
			// you can change format of date
			Date date = formatter.parse(data);
			java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());

			return timeStampDate;
		} catch (ParseException e) {
			System.out.println("Exception :" + e);
			return null;
		}
	}
}