package com.engin.focab.controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.jpa.Definition;
import com.engin.focab.jpa.Vocabulary;
import com.engin.focab.repository.VocabularyRepository;
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
	@Autowired
	private VocabularyRepository rep;

	@GetMapping("/detectIdiom")
	@ResponseBody
	public Set<String> detectIdioms(@RequestParam String sentence) {
		String[] taggedSentence = sentenceTaggingService.tagString(sentence);
		return idiomDetectionService.detectIdioms(taggedSentence, new Sentence(sentence.toLowerCase()));
	}

	@GetMapping("/delete")
	@ResponseBody
	public HashMap<String, Boolean> delete() {
		List<Vocabulary> list = rep.findAll();
		HashMap<String, Boolean> result = new HashMap<String, Boolean>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Vocabulary vocabulary = (Vocabulary) iterator.next();
			List<Definition> definitions = vocabulary.getDefinitions();
			boolean flag = true;
			for (Iterator iterator2 = definitions.iterator(); iterator2.hasNext();) {
				Definition definition = (Definition) iterator2.next();
				flag = flag && definition.isSeparable();

			}
			result.put(vocabulary.getText(), flag);

		}
		return result;
	}

}
