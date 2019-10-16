package com.engin.focab.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.dtos.ExampleDto;
import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.Example;
import com.engin.focab.jpa.FavoriteEntry;
import com.engin.focab.jpa.Vocabulary;
import com.engin.focab.services.FavoriteService;
import com.engin.focab.services.SessionService;
import com.engin.focab.services.VocabularyService;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials="true")
@RestController
public class LearnController {

	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private VocabularyService vocabularyService;
	@Autowired
	private SessionService sessionService;


	@GetMapping("/learn")
	@ResponseBody
	public ExampleDto learn() throws IOException, InterruptedException {
		Customer customer = sessionService.getCurrentCustomer();
 		Example example = favoriteService.getARandomExample(customer);
		return convertToDtoExample(example) ;
	}
	
	public void setFavoriteService(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	private ExampleDto convertToDtoExample(Example example) {
		ModelMapper modelMapper = new ModelMapper();
		PropertyMap<Example, ExampleDto> exampleMap = new PropertyMap<Example, ExampleDto> () {
			  protected void configure() {
				    map().setVocabulary(source.getVocabulary().getText());
				  };
		};
		
		modelMapper.addMappings(exampleMap);
		ExampleDto exampleDto = modelMapper.map(example,ExampleDto.class);
		return exampleDto;
	}
}
