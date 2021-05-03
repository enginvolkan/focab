package com.engin.focab.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engin.focab.services.SessionService;

import edu.stanford.nlp.simple.Sentence;

@Component
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SingleWordDetectionService {
	@Autowired
	private SessionService sessionService;
	@Autowired
	private List<Set<String>> commonWords;

	private final Logger logger = LoggerFactory.getLogger(SingleWordDetectionService.class);
	private HashSet<String> customersCommonWords = new HashSet<>();

	public Set<String> getCustomersCommonWords() {
		if (CollectionUtils.isEmpty(customersCommonWords)) {
			int customerLevel = sessionService.getCurrentCustomer().getLevel() / 1000;
			commonWords.stream().limit(customerLevel).forEach(set -> customersCommonWords.addAll(set));
		}
		return customersCommonWords;
	}

	public SingleWordDetectionService() {
//		super();
	}

	private boolean isPunctuation(String s) {
		return s.equals("``");
	}

	public Set<String> reduce(Set<String> singleWordSet) {

//		Customer customer = sessionService.getCurrentCustomer();
		singleWordSet.removeAll(getCustomersCommonWords());
//		singleWordSet.removeAll(getKnownWords(customer));

		return singleWordSet;

	}

	public String findSingleWords(Sentence sentence, List<String> sentenceLemmas) {
		Properties props = new Properties();
		props.setProperty("ner.model",
				"english.all.3class.distsim.crf.ser.gz,english.conll.4class.distsim.crf.ser.gz,english.muc.7class.distsim.crf.ser.gz");
		props.setProperty("ner.useSUTime", "false");
		props.setProperty("ner.applyFineGrained", "false");

		List<String> namedEntities = sentence.nerTags(props);
		StringBuilder result = new StringBuilder();

		for (int j = 0; j < sentenceLemmas.size(); j++) {
			if (namedEntities.get(j).equals("O")
					&& !getCustomersCommonWords().contains(sentenceLemmas.get(j).toLowerCase())
					&& !isPunctuation(sentenceLemmas.get(j))) {
				result.append(" ").append(sentenceLemmas.get(j));
			} else {
//				if (!namedEntities.get(j).equals("O")) {
//					logger.debug("Named Entity disregarded: " + sentenceLemmas.get(j) + " | " + namedEntities.get(j));
//				} else {
//					logger.debug("Common word disregarded: {}", sentenceLemmas.get(j));
//				}
			}

		}
		return result.toString().trim();
	}
}
