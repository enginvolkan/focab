package com.engin.focab.controllers;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.dtos.LexiDto;
import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.FormattedSearchResult;
import com.engin.focab.jpa.corpus.LexiModel;
import com.engin.focab.services.AnalysisService;
import com.engin.focab.services.LexiDetailsService;
import com.engin.focab.services.SearchService;
import com.engin.focab.services.SessionService;

//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
public class LexiDetailsController {

	@Autowired
	private AnalysisService analysisService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private LexiDetailsService lexiDetailsService;
	@Autowired
	private SearchService searchService;
	private FormattedSearchResult definition;

	@GetMapping("/lexiDetails")
	@ResponseBody
	public LexiDto getDetails(@RequestParam String text) throws MalformedURLException, XmlRpcException {
		Customer customer = sessionService.getCurrentCustomer();

		if (customer != null) {
			LexiDto lexiDto = new LexiDto(text);
			Optional<LexiModel> lexi = lexiDetailsService.findLexi(text);

			if (lexi.isPresent()) {
				// either phrasal,idiom of common word
				lexiDto.setDefinitions(
						lexi.get().getDefinitions().stream().map(x -> x.getDefinition()).collect(Collectors.toList()));

				lexiDto.setOtherExamples(lexi.get().getDefinitions().stream().flatMap(x -> x.getExamples().stream())
						.collect(Collectors.toList()));
			} else {
				FormattedSearchResult definition = searchService.getDefinition(text);
				if(definition!=null){
				List<String> dictionaryDefinitions = definition.getDefs().stream()
						.map(x -> x.getDefinition()).collect(Collectors.toList());
				lexiDto.setDefinitions(dictionaryDefinitions);
				}
			}
			return lexiDto;
		} else {
			return new LexiDto("");
		}

	}

	public AnalysisService getAnalysisService() {
		return analysisService;
	}

	public void setAnalysisService(AnalysisService analysisService) {
		this.analysisService = analysisService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

}
