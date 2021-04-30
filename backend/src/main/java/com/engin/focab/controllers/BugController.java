package com.engin.focab.controllers;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.dtos.BugDto;
import com.engin.focab.jpa.corpus.BugModel;
import com.engin.focab.repository.BugRepository;

//@CrossOrigin(origins = "http://localhost:4200", allowCredentials="true")
@RestController
public class BugController {

	@Autowired
	private BugRepository bugRepository;


	@PostMapping("/addBug")
	@ResponseBody
	public boolean addBug(@RequestBody BugDto bugDto) {

		BugModel bug = new BugModel(bugDto.getText(), bugDto.getType(),
				StringEscapeUtils.unescapeHtml(String.join(" | ", bugDto.getSentences())));
		bugRepository.save(bug);
		return true;
	}

}
