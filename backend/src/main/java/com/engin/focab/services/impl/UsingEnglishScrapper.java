package com.engin.focab.services.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.corpus.DefinitionModel;
import com.engin.focab.jpa.corpus.LexiModel;

@Component
public class UsingEnglishScrapper {
	private String url = "https://www.usingenglish.com/reference/phrasal-verbs/list.html";

	private String chromeDriverPath = "/home/engin/focab/backend/src/main/resources/chromedriver";

	public ArrayList<LexiModel> getPhrasalVebs() {

		// Set the path of the driver to driver executable. For Chrome, set the
		// properties as following:
		//
		File file = new File(chromeDriverPath);
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");

		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get(url);

		List<WebElement> wraplist = driver.findElements(By.className("wraplist"));
		List<WebElement> elements = new ArrayList<WebElement>();
		wraplist.stream().forEach(x -> elements.addAll(x.findElements(By.tagName("a"))));
		HashSet<String> as = new HashSet<String>();

		Optional<String> link;
		for (WebElement webElement : elements) {
			link = Optional.ofNullable(webElement.getAttribute("href"));
			link.ifPresent(x -> {
				if (x.contains("/reference/phrasal-verbs")) {
					as.add(x.substring(0, x.indexOf('#')));
				}
			});
		}
		ArrayList<LexiModel> phrasalVerbs = new ArrayList<LexiModel>();
		System.out.println(as.size() + "links to go...");
		for (String a : as) {
			System.out.println("Trying:" + a);

			do {
				driver.get(a);
			} while (driver.getCurrentUrl().contains("#google_vignette"));

			List<WebElement> cards = driver.findElements(By.className("card"));
			for (Iterator iterator = cards.iterator(); iterator.hasNext();) {
				try {
					WebElement card = (WebElement) iterator.next();
					String text = card.findElement(By.tagName("h2")).getText();
					String meaning = card.findElement(By.tagName("strong")).getText().substring(8).trim();
					String sentence = card.findElement(By.tagName("em")).getText();
					String type = card.findElement(By.tagName("li")).getText();

					boolean isSeparable;
					switch (type.trim()) {
					case "Intransitive":
						isSeparable = false;
						break;
					case "Inseparable":
						isSeparable = false;
						break;
					case "Separable [obligatory]":
						isSeparable = true;
						break;
					case "Separable [optional]":
						isSeparable = true;
						break;
					default:
						isSeparable = false;
						break;
					}
					DefinitionModel definitionModel = new DefinitionModel(text, meaning, isSeparable, sentence, "UsingEnglish");
					LexiModel phrasalVerb = new LexiModel(text);
					List<DefinitionModel> definitionModels = phrasalVerb.getDefinitions();
					if (definitionModels == null) {
						definitionModels = new ArrayList<DefinitionModel>();
					}
					definitionModels.add(definitionModel);
					phrasalVerb.setDefinitions(definitionModels);
					phrasalVerbs.add(phrasalVerb);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Failed at:" + a);
				}
			}
		}
		driver.close();

		return phrasalVerbs;
	}

}
