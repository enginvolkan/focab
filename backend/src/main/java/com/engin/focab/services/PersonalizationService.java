package com.engin.focab.services;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.SummerizedMovieAnalysisModel;

public interface PersonalizationService {

	SummerizedMovieAnalysisModel personalize(SummerizedMovieAnalysisModel analyzeMovie, Customer customer);

}
