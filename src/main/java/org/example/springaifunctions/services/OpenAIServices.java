package org.example.springaifunctions.services;

import lombok.RequiredArgsConstructor;
import org.example.springaifunctions.model.Answer;
import org.example.springaifunctions.model.Question;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;


public interface OpenAIServices  {
    public Answer getAnswer( Question question);
}