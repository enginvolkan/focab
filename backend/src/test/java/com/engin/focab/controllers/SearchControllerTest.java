package com.engin.focab.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.engin.focab.services.SearchService;

@RunWith(SpringRunner.class)
@WebMvcTest(SearchController.class)
public class SearchControllerTest {

	@Mock
	private SearchService searchService;
	@InjectMocks
	private SearchController searchController;
	
	@Test
	private void searchTest_PositiveResponse() {
		
	}
}
