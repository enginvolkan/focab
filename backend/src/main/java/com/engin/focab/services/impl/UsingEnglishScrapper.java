package com.engin.focab.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Example;
import com.engin.focab.jpa.PhrasalVerbModel;
import com.engin.focab.jpa.QuoDBResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Component
public class UsingEnglishScrapper {
	private String url = "https://www.usingenglish.com/reference/phrasal-verbs/list.html";

	@Value("${chromedriver.path}")
	private String chromeDriverPath;

	public PhrasalVerbModel[] getPhrasalVebs() throws InterruptedException {

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

		List<WebElement> elements = driver.findElements(By.tagName("li"));
		List<WebElement> as = elements.stream().map(x -> x.findElement(By.tagName("a"))).collect(Collectors.toList());
		PhrasalVerbModel[] phrasalVerbs = new PhrasalVerbModel[elements.size()];
		int i = 0;

		for (WebElement a : as) {
//    		<a rel="popover" class="btn btn-mini" data-trigger="hover" onclick="window.context_quotes(43601576,'M568161114')" ;=""><i class="icon-align-center"></i>Context</a>

			String verb = a.getText();
			String reference = a.getAttribute("href");
			driver.get(reference);
			List<WebElement> h2 = driver.findElements(By.tagName("h2"));

			String id1 = id0.substring(22, id0.indexOf(','));
			String id2 = id0.substring(id0.indexOf(',') + 2, id0.indexOf(')') - 1);
			Example newExample = extractExample(id1, id2, vocabulary.getText());
			if (newExample != null) {
				examples[i] = newExample;
				i++;
			}
		}
		driver.close();

		return examples;
	}

	private Example extractExample(String id1, String id2, String word) {
		try {
			URL url = new URL("http://api.quodb.com/quotes/" + id2 + "/" + id1);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestProperty("Accept", "application/json");

			// read the output from the server
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
			Type listType = new TypeToken<ArrayList<QuoDBResult>>() {
			}.getType();
			Gson gson = new GsonBuilder().registerTypeAdapter(listType, new QuoDBResultDeserializer()).create();

			ArrayList<QuoDBResult> qoudbResults = gson.fromJson(stringBuilder.toString().trim(), listType);

//			QuoDBResultHeader qoudbResult = gson.fromJson(stringBuilder.toString().trim(), listType);

			String title = "";
			String phrase = "- ";
			for (Iterator<QuoDBResult> iterator = qoudbResults.iterator(); iterator.hasNext();) {
				QuoDBResult result = iterator.next();
				if (title == "") {
					title = result.getTitle() + " (" + result.getYear() + ")";
				} else {
					phrase = phrase.concat(" <br> -  ");
				}
				phrase = phrase.concat(result.getPhrase());
			}

			phrase = phrase.replaceAll(word, "<strong>" + word + "</strong>");

			return new Example(phrase, url.getHost() + url.getPath(), title);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
