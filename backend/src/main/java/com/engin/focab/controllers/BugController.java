package com.engin.focab.controllers;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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
				URLDecoder.decode(String.join(" | ", bugDto.getSentences()), StandardCharsets.UTF_8));
		bugRepository.save(bug);
		return true;
	}

}
