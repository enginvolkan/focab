package com.engin.focab.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.services.impl.IdiomDetectionService;
import com.engin.focab.services.impl.SentenceTaggingService;

import edu.stanford.nlp.simple.Sentence;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
public class IdiomDetectionController {

	@Autowired
	private IdiomDetectionService idiomDetectionService;
	@Autowired
	private SentenceTaggingService sentenceTaggingService;

	@GetMapping("/detectIdiom")
	@ResponseBody
	public Set<String> detectIdioms(@RequestParam String sentence) {
		String[] taggedSentence = sentenceTaggingService.tagString(sentence);
		return idiomDetectionService.detectIdioms(taggedSentence, new Sentence(sentence.toLowerCase()));
	}

}
