package com.engin.focab.constants;

import java.util.Set;

public class FocabConstants {
	public static final Set<String> WORDS_TO_SKIP = Set.of("the", "a", "in", "of", "to", "on", "be", "and", "out",
			"have", "for", "it", "not", "at", "or", "you", "OpenSubtitles", "from", "-", "am", "are");
	public static final String WORD_REGEX_PATTERN = "([a-zA-Z-]+)";
//	private final Pattern punctuationPattern = Pattern.compile(".*\\p{Punct}.*");

}
