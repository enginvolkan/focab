package com.engin.focab.services.impl;

import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.SummerizedMovieAnalysisModel;
import com.engin.focab.services.PersonalizationService;

@Component
public class DefaultPersonalizationService implements PersonalizationService {

	@Override
	public SummerizedMovieAnalysisModel personalize(SummerizedMovieAnalysisModel analysis, Customer customer) {
		return analysis;
	}

}
