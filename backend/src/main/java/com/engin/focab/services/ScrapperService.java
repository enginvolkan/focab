package com.engin.focab.services;

import java.io.IOException;
import java.util.List;

import com.engin.focab.jpa.Example;
import com.engin.focab.jpa.Vocabulary;

public interface ScrapperService {
	Example[] getExamples(Vocabulary vocabulary) throws IOException, InterruptedException;
}
