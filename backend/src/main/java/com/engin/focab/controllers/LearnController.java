package com.engin.focab.controllers;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.dtos.ExampleDto;
import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.corpus.ExampleModel;
import com.engin.focab.services.FavoriteService;
import com.engin.focab.services.LexiDetailsService;
import com.engin.focab.services.SessionService;

//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
public class LearnController {

	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private LexiDetailsService lexiDetailsService;
	@Autowired
	private SessionService sessionService;

	@GetMapping("/learn")
	@ResponseBody
	public ExampleDto learn() throws IOException, InterruptedException {
		Customer customer = sessionService.getCurrentCustomer();
		ExampleModel exampleModel = favoriteService.getARandomExample(customer);
		return convertToDtoExample(exampleModel);
	}

	public void setFavoriteService(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	private ExampleDto convertToDtoExample(ExampleModel exampleModel) {
		ModelMapper modelMapper = new ModelMapper();
		PropertyMap<ExampleModel, ExampleDto> exampleMap = new PropertyMap<ExampleModel, ExampleDto>() {
			@Override
			protected void configure() {
				map().setVocabulary(source.getVocabulary().getText());
			};
		};

		modelMapper.addMappings(exampleMap);
		ExampleDto exampleDto = modelMapper.map(exampleModel, ExampleDto.class);
		return exampleDto;
	}
}
