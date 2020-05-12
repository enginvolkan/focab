package com.engin.focab;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.engin.focab.cronjob.IdiomRegexBuilderCronJob;

@SpringBootApplication
@EnableScheduling
public class FocabApplication {

	@Autowired
	IdiomRegexBuilderCronJob idiomLemmaBuilderCronJob;

	public static void main(String[] args) {
		SpringApplication.run(FocabApplication.class, args);
	}

	@Scheduled(cron = "0 0 22 * * *")
//	@Scheduled(fixedDelay = 1000)
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
	public List<String> phrasalVerbOmitTags() {
		return Arrays.asList("PRP$", "PRP", "RB", "$", "CC", "DT", "DT IN", "PDT");
	}

	@Bean
	public List<String> phrasalVerbFinalTags() {
		return Arrays.asList("", "NN", "JJ", "DT", "CD", "IN", "NN VBN");
	}
}
