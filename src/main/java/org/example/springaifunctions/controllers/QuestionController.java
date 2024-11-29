package org.example.springaifunctions.controllers;
import lombok.RequiredArgsConstructor;
import org.example.springaifunctions.model.Answer;
import org.example.springaifunctions.model.Question;
import org.example.springaifunctions.services.OpenAIServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final OpenAIServices openAIServices;

    @PostMapping("/weather")
    public Answer askQuestion( @RequestBody Question question) {
        return openAIServices.getAnswer(question);
    }
    @PostMapping("/stockTicker")
    public Answer askTicker( @RequestBody Question question) {
        return openAIServices.getStockPrice(question);
    }
}