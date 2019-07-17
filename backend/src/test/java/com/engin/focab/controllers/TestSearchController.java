package com.engin.focab.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;

import com.engin.focab.jpa.Vocabulary;
import com.engin.focab.services.SearchService;

@RunWith(SpringRunner.class)
@WebMvcTest(SearchController.class)
public class TestSearchController {

	@Mock
	private SearchService searchService;
	@InjectMocks
	private SearchController searchController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
	@Test
	public void searchTest_PositiveResponse() throws Exception {
		Vocabulary v0 = new Vocabulary("car");
		Vocabulary v1 = new Vocabulary("career");
		ArrayList<Vocabulary> list = new ArrayList<Vocabulary>();
		list.add(v0);
		list.add(v1);
		
        when(searchService.getSearchResults("car")).thenReturn(list);

//		mockMvc.perform(MockMvcRequestBuilders.get("/search?q=car")
//	    	      .accept(MediaType.APPLICATION_JSON))
//	    		  .andDo(print())
//	    	      .andExpect(status().isOk())
//	    	      .andExpect(jsonPath("$", hasSize(2)))
//	    	      .andExpect(jsonPath("$[0].text", is(v0.getText())))
//				  .andExpect(jsonPath("$[1].text", is(v1.getText())));

	}
		

}
