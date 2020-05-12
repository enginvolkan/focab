package com.engin.focab.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.services.PhrasalVerbsDetectionService;

import edu.stanford.nlp.simple.Sentence;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
public class PhrasalVerbDetectionController {

	@Autowired
	@Qualifier("constituencyParser")
	private PhrasalVerbsDetectionService phrasalDetectionService;

	@GetMapping("/detectPhrasalVerb")
	@ResponseBody
	public List<String> detectIdioms(@RequestParam String sentence) {
		return phrasalDetectionService.detectPhrasalVerbs(new Sentence(sentence));
	}

}
