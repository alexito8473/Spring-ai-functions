package org.example.springaifunctions.services;

import org.example.springaifunctions.model.Answer;
import org.example.springaifunctions.model.Question;


public interface OpenAIServices  {
    Answer getAnswer( Question question);
    Answer getStockPrice(Question question);
}