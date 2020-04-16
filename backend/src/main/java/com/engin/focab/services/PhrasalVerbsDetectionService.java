package com.engin.focab.services;

import java.util.List;

import edu.stanford.nlp.simple.Sentence;

public interface PhrasalVerbsDetectionService {

	List<String> detectPhrasalVerbs(Sentence sentence);

}
