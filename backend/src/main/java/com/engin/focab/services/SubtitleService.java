package com.engin.focab.services;

import com.github.wtekiela.opensub4j.response.SubtitleFile;

public interface SubtitleService {

	SubtitleFile getASubtitleByImdbId(String id);

}
