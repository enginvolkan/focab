package com.engin.focab.services.impl.srt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.engin.focab.jpa.SubtitleModel;
import com.engin.focab.services.SrtParserService;
import com.engin.focab.services.impl.srt.SrtParserUtils;

@Component
public final class DefaultSrtParserService implements SrtParserService {

	private static final Pattern PATTERN_TIME = Pattern.compile("([\\d]{2}:[\\d]{2}:[\\d]{2},[\\d]{3}).*([\\d]{2}:[\\d]{2}:[\\d]{2},[\\d]{3})");
	private static final Pattern PATTERN_NUMBERS = Pattern.compile("(\\d+)");
	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	
	/* (non-Javadoc)
	 * @see com.engin.focab.services.impl.srt.SrtParserService#getSubtitlesFromString(java.lang.String)
	 */
	@Override
	public ArrayList<SubtitleModel> getSubtitlesFromString (String text) {
		
	     Reader inputString = new StringReader(text);
	     BufferedReader reader = new BufferedReader(inputString);
		return getSubtitlesFromBufferedReader(reader, false, false);
	}
	
	/* (non-Javadoc)
	 * @see com.engin.focab.services.impl.srt.SrtParserService#getSubtitlesFromFile(java.lang.String)
	 */
	
	@Override
	public ArrayList<SubtitleModel> getSubtitlesFromFile (String path) {
		return getSubtitlesFromFile(path, false, false);
	}
	
	/* (non-Javadoc)
	 * @see com.engin.focab.services.impl.srt.SrtParserService#getSubtitlesFromFile(java.lang.String, boolean)
	 */
	@Override
	public ArrayList<SubtitleModel> getSubtitlesFromFile (String path, boolean twm) {
		return getSubtitlesFromFile(path, twm, false);
	}
	
	/* (non-Javadoc)
	 * @see com.engin.focab.services.impl.srt.SrtParserService#getSubtitlesFromFile(java.lang.String, boolean, boolean)
	 */
	@Override
	public ArrayList<SubtitleModel> getSubtitlesFromFile (String path, boolean twm, boolean usingNodes) {
		
		ArrayList<SubtitleModel> subtitles = null;


		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), DEFAULT_CHARSET));
			subtitles = getSubtitlesFromBufferedReader(br, false, false);
			
		} catch (Exception e) {
		} 
		return subtitles;
	}
	
	/* (non-Javadoc)
	 * @see com.engin.focab.services.impl.srt.SrtParserService#getSubtitlesFromBufferedReader(java.io.BufferedReader, boolean, boolean)
	 */
	@Override
	public ArrayList<SubtitleModel> getSubtitlesFromBufferedReader (BufferedReader br, boolean twm, boolean usingNodes) {
		
		ArrayList<SubtitleModel> subtitles = null;
		SubtitleModel sub = null;
		StringBuilder srt = null;

		try {
			
			subtitles = new ArrayList<>();
			sub = new SubtitleModel();
			srt = new StringBuilder();
			
			while (br.ready()) {
				
				String line = br.readLine();
				
				Matcher matcher = PATTERN_NUMBERS.matcher(line); 

				if (matcher.find()) {
					sub.id = Integer.parseInt(matcher.group(1)); // index
					line = br.readLine();
				}
				
				matcher = PATTERN_TIME.matcher(line);
				
				if (matcher.find()) {
					sub.startTime = matcher.group(1); // start time
					sub.timeIn = SrtParserUtils.textTimeToMillis(sub.startTime);
					sub.endTime = matcher.group(2); // end time
					sub.timeOut = SrtParserUtils.textTimeToMillis(sub.endTime);
				}
				
				String aux;
				int counter = 0;
				while ((aux = br.readLine()) != null && !aux.isEmpty()) {
					if(!twm && counter>0 && aux.trim().startsWith("-")) {
						srt.append("\n");
					}
					srt.append(aux);
					if (twm)
						srt.append("\n");
					else {
						if (!line.endsWith(" ")) // for new lines '\n' removed from BufferedReader
							srt.append(" ");
					}
					counter++;
				}

				srt.delete(srt.length()-1, srt.length()); // remove '\n' or space from end string

				line = srt.toString();
				srt.setLength(0);

				if (line != null && !line.isEmpty())
					line = line.replaceAll("<[^>]*>", ""); // clear all tags
				
				sub.text = line;
				subtitles.add(sub);

				if (usingNodes) {
					sub.nextSubtitle = new SubtitleModel();
					sub = sub.nextSubtitle;
				} else {
					sub = new SubtitleModel();
				}
			}
		} catch (Exception e) {
		} 
		return subtitles;
	}
}
