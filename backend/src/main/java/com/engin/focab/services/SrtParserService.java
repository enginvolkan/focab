package com.engin.focab.services;

import java.io.BufferedReader;
import java.util.ArrayList;

import com.engin.focab.jpa.SubtitleModel;
import com.engin.focab.services.impl.srt.DefaultSrtParserService;

public interface SrtParserService {

	ArrayList<SubtitleModel> getSubtitlesFromString(String text);

	/**
	 * Metodo responsavel por fazer parse de um arquivos de legenda. <br>
	 * Obs. O texto não vai conter quebra de linhas e não é usado Node {@link DefaultSrtParserService#getSubtitlesFromFile(String, boolean, boolean)}}
	 * @param path
	 * @return
	 */

	ArrayList<SubtitleModel> getSubtitlesFromFile(String path);

	/**
	 * Metodo responsavel por fazer parse de um arquivos de legenda. <br>
	 * Obs. O texto pode ou nao conter quebra de linhas e não é usado Node {@link DefaultSrtParserService#getSubtitlesFromFile(String, boolean, boolean)}}
	 * @param path
	 * @returnSubtitle
	 */
	ArrayList<SubtitleModel> getSubtitlesFromFile(String path, boolean twm);

	/**
	 * Metodo responsavel por fazer parse de um arquivos de legenda. <br>
	 * Obs. O texto não vai conter quebra de linhas e pode ser usado Node
	 * @param path
	 * @return
	 */
	ArrayList<SubtitleModel> getSubtitlesFromFile(String path, boolean twm, boolean usingNodes);

	ArrayList<SubtitleModel> getSubtitlesFromBufferedReader(BufferedReader br, boolean twm, boolean usingNodes);

}