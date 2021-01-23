package com.engin.focab.services;

import java.io.IOException;
import java.util.List;

import com.engin.focab.jpa.corpus.ExampleModel;
import com.engin.focab.jpa.corpus.LexiModel;

public interface ScrapperService {
	ExampleModel[] getExamples(LexiModel lexiModel) throws IOException, InterruptedException;
}
