package com.engin.focab.services.impl.srt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.engin.focab.jpa.SubtitleModel;

public final class SrtParserUtils {

	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	public final static long MILLIS_IN_SECOND = 1000;
	public final static long MILLIS_IN_MINUTE = MILLIS_IN_SECOND * 60; // 60000
	public final static long MILLIS_IN_HOUR = MILLIS_IN_MINUTE * 60; // 3600000

	protected final static Pattern PATTERN_TIME = Pattern.compile("([\\d]{2}):([\\d]{2}):([\\d]{2}),([\\d]{3})");

	/**
	 * Metodo responsavel por converter uma String com o formato de tempo
	 * HH:mm:ss,SSS em millis
	 * 
	 * @param time
	 * @return texto convertido em millis
	 * @throws Exception
	 */

	public static long textTimeToMillis(final String time) throws Exception {

		Matcher matcher = PATTERN_TIME.matcher(time);
		if (time == null || time.isEmpty() || !matcher.find())
			throw new Exception("incorret time format...");

		long msTime = 0;
		short hours = Short.parseShort(matcher.group(1));
		byte min = Byte.parseByte(matcher.group(2));
		byte sec = Byte.parseByte(matcher.group(3));
		short millis = Short.parseShort(matcher.group(4));

		if (hours > 0)
			msTime += hours * MILLIS_IN_HOUR;
		if (min > 0)
			msTime += min * MILLIS_IN_MINUTE;
		if (sec > 0)
			msTime += sec * MILLIS_IN_SECOND;

		msTime += millis;

		return msTime;
	}

	/**
	 * Metodo responsavel por converter millis em texto formato HH:mm:ss,SSS
	 * 
	 * @param millisToText
	 * @return
	 */

	public static String millisToText(final long millisToText) {

		int millisToSeconds = (int) millisToText / 1000;
		long hours = millisToSeconds / 3600;
		long minutes = (millisToSeconds % 3600) / 60;
		long seconds = millisToSeconds % 60;
		long millis = millisToText % 1000;

		if (hours < 0)
			hours = 0;

		if (minutes < 0)
			millis = 0;

		if (seconds < 0)
			seconds = 0;

		if (millis < 0)
			millis = 0;

		return String.format("%02d:%02d:%02d,%03d", hours, minutes, seconds, millis);
//		return (hours > 9 ? hours + ":" : "0" + hours + ":") + (minutes > 9 ?  minutes + ":" : "0" + minutes + ":") + (seconds > 9 ?  seconds : "0" + seconds) + "," + (millis > 99 ? millis : millis > 9 ? "0" + millis : millis >= 0 ? "00" + millis : "000");
	}

	/**
	 * Método responsavel por converter millisIn e millisOut em texto formato
	 * HH:mm:ss,SSS --> HH:mm:ss,SSS
	 * 
	 * @param millisIn
	 * @param millisOut
	 * @return
	 */

	public static String millisToText(final long millisIn, final long millisOut) {
		return millisToText(millisIn) + " --> " + millisToText(millisOut);
	}

	/**
	 * Metodo responsavel por buscar um Subtitle em uma lista a partir do tempo
	 * passado <b>timeMillis</b>
	 * 
	 * @param listSubtitles
	 * @param timeMillis
	 * @return um Subtitle ou null no caso de nada encontrado
	 */
	public static SubtitleModel findSubtitle(ArrayList<SubtitleModel> listSubtitles, long timeMillis) {

		if (listSubtitles == null || listSubtitles.isEmpty())
			return null;

		// most likely is first index
		if (timeMillis == 0 || timeMillis < 1000)
			return listSubtitles.get(0);

		for (int i = 0; i < listSubtitles.size(); i++) {
			SubtitleModel sub = listSubtitles.get(i);

			if (inTime(sub, timeMillis))
				return sub;

			if (sub.getNextSubtitle() != null && sub.getNextSubtitle().getTimeIn() >= timeMillis)
				return sub.getNextSubtitle();
			else if (listSubtitles.size() <= i + 1) // is last?
				continue;

			// get next element for test if is a legend
			sub = listSubtitles.get(i + 1);
			if (sub.getTimeIn() >= timeMillis)
				return sub;
		}
		return null;
	}

	/**
	 * Metodo responsavel por buscar um Subtitle a partir de um {@link Subtitle},
	 * utilizando node<br>
	 * Obs. Deve ser configurado no load do arquivo para utilizar Node
	 * #{@link DefaultSrtParserService#getSubtitlesFromFile(String, boolean, boolean)}
	 * 
	 * @param subtitle
	 * @param timeMillis
	 * @return
	 */
	public static SubtitleModel findSubtitle(final SubtitleModel subtitle, long timeMillis) {

		if (subtitle == null)
			return subtitle;

		SubtitleModel subAux;
		while ((subAux = subtitle.getNextSubtitle()) != null)
			if (inTime(subAux, timeMillis))
				return subAux;
//		while ((subAux = subAux.previousSubtitle) != null)
//			if (time >= subAux.timeIn && time <= subAux.timeOut)
//				return  subAux;
//		
		return subAux;
	}

	/**
	 * Method responsavel por testar se um subtititulo est� dentro do tempo buscado.
	 * 
	 * @param subtitle
	 * @param timeMillis
	 * @return
	 */
	private static boolean inTime(final SubtitleModel subtitle, long timeMillis) {
		return timeMillis >= subtitle.getTimeIn() && timeMillis <= subtitle.getTimeOut();
	}

	/**
	 * Método responsavel por realizar a sincronização do subtitulo e escrever um
	 * novo arquivo com o novo tempo sincronizado no arquivo
	 * 
	 * @param listSubtitles
	 * @param timeInMillis
	 * @return
	 */
	public static boolean speedSynchronization(final ArrayList<SubtitleModel> listSubtitles, long timeInMillis,
			File fileOut) {

		if (listSubtitles == null || listSubtitles.isEmpty() || timeInMillis == 0 || fileOut == null)
			return false;

//		if (!fileOut.exists())
//			fileOut.mkdirs();		

		try (FileOutputStream fos = new FileOutputStream(fileOut);
				OutputStreamWriter osw = new OutputStreamWriter(fos, DEFAULT_CHARSET);
				BufferedWriter bos = new BufferedWriter(osw);) {

			final int length = listSubtitles.size();
			for (int i = 0; i < length; i++) {
				SubtitleModel subtitle = listSubtitles.get(i);
				bos.write(String.valueOf(subtitle.getId()));
				bos.newLine();
				bos.write(SrtParserUtils.millisToText(subtitle.getTimeIn() + timeInMillis,
						subtitle.getTimeOut() + timeInMillis));
				bos.newLine();
				bos.write(subtitle.getText());
				bos.newLine();
			}
			bos.flush();
			return true;
		} catch (Exception e) {

		}
		return false;

	}

}