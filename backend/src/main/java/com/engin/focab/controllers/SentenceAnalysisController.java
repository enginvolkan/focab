package com.engin.focab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.jpa.SubtitleModel;
import com.engin.focab.services.AnalysisService;

//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
public class SentenceAnalysisController {

	@Autowired
	private AnalysisService analysisService;

	@GetMapping("/detectSentence")
	@ResponseBody
	public SubtitleModel analyseSentence(@RequestParam String sentence, @RequestParam boolean analyseIdioms,
			@RequestParam boolean analysePhrasalVerbs, @RequestParam boolean analyseSingleWords) {
		return analysisService.analyzeSentence(sentence, analyseIdioms, analysePhrasalVerbs, analyseSingleWords);
	}

}
