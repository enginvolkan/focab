package com.engin.focab.cronjob;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.corpus.LexiModel;
import com.engin.focab.repository.IdiomRepository;

import edu.stanford.nlp.simple.Sentence;

@Component
public class IdiomRegexBuilderCronJob {

	private IdiomRepository idiomRepository;

	public IdiomRegexBuilderCronJob() {

	}

	public void fillNullLemmas() {
		LexiModel[] idiomModelsWithNoRegex = getIdiomRepository().findNullRegex();
		Properties props = new Properties();
		props.setProperty("pos.model", "english-left3words-distsim.tagger");

		for (LexiModel idiomModel : idiomModelsWithNoRegex) {
			Sentence idiom = new Sentence(idiomModel.getText().toLowerCase(Locale.ENGLISH));
			List<String> lemmaList = idiom.lemmas(props);

			ArrayList<String> segments = new ArrayList<>();

			segments.add(".*");

			for (String lemma : lemmaList) {
				if (lemma.contains("@") || lemma.contains("#")) {
					// special word
					segments.add("\\b ");
					// segments.set(segments.size() - 1, segments.get(segments.size() - 1) + "\\b
					// ");
					segments.add("\\w+.* ");
					// .*\bjump out of\b \w+.* \bskin\b.*
				} else { // normal word
					if (segments.get(segments.size() - 1).equals("\\w+.* ")
							|| segments.get(segments.size() - 1).equals(".*")) {
						// normal word but previous one was a special character
						// first normal word
						segments.add("\\b" + lemma);
					} else { // normal word
						segments.set(segments.size() - 1, segments.get(segments.size() - 1) + " " + lemma);
					}
				}
			}
			if (!segments.get(segments.size() - 1).equals("\\w+.* ")) {
				segments.set(segments.size() - 1, segments.get(segments.size() - 1) + "\\b");
			}
			segments.add(".*");
			idiomModel.setRegex(segments.stream().collect(Collectors.joining("")));
			idiomRepository.save(idiomModel);
		}
	}

	@Autowired
	public void setIdiomRepository(IdiomRepository idiomRepository) {
		this.idiomRepository = idiomRepository;
	}

	public IdiomRepository getIdiomRepository() {
		return idiomRepository;
	}

}
