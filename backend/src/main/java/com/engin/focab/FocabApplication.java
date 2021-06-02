package com.engin.focab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import com.engin.focab.cronjob.IdiomRegexBuilderCronJob;
import com.engin.focab.jpa.corpus.LexiModel;
import com.engin.focab.repository.CommonWordRepository;

@SpringBootApplication
@EnableScheduling
public class FocabApplication {

	@Autowired
	IdiomRegexBuilderCronJob idiomLemmaBuilderCronJob;
	@Autowired
	CommonWordRepository commonWordRepository;

	public static void main(String[] args) {
		SpringApplication.run(FocabApplication.class, args);
	}

	// @Scheduled(cron = "0 0 22 * * *")
	@Scheduled(fixedDelay = 3600000)
	public void scheduleFixedDelayTask() {
		System.out.println("IdiomLemmaBuilderCronJob is running...");
		idiomLemmaBuilderCronJob.fillNullLemmas();
	}

	@Bean
	public LinkedHashMap<String, String> phrasalVerbTagRules() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("NN NN", "NN");
		map.put("JJ NN", "NN");
		map.put("CD NN", "NN");
		map.put("IN NN", "NN");
		map.put("PRP NN", "NN");
		map.put("JJ CD", "CD");
		return map;
	}

	@Bean
	public List<Set<String>> commonWords() {
		List<LexiModel> commonWords = commonWordRepository.findAll();
		List<Set<String>> listOfSets = new ArrayList<Set<String>>();

		for (int i = 0; i < 5; i++) {
			listOfSets.add(new HashSet<String>());
		}

		commonWords.stream().filter(x -> x.getCommonWordLevel() != null)
				.forEach(x -> listOfSets.get(x.getCommonWordLevel()).add(x.getText()));
		return listOfSets;
	}

	@Bean
	public List<String> phrasalVerbOmitTags() {
		return Arrays.asList("PRP$", "PRP", "RB", "$", "CC", "DT", "DT IN", "PDT");
	}

	@Bean
	public List<String> phrasalVerbFinalTags() {
		return Arrays.asList("", "NN", "JJ", "DT", "CD", "IN", "NN VBN");
	}

	@Bean
	public CookieSerializer cookieSerializer() {
		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
		serializer.setCookieName("JSESSIONID");
		serializer.setCookiePath("/");
		serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
		serializer.setSameSite("None");
		serializer.setUseSecureCookie(true);
		serializer.setUseHttpOnlyCookie(false);
		return serializer;
	}

}
