package org.example.springaifunctions.services;

import lombok.RequiredArgsConstructor;
import org.example.springaifunctions.functions.ServiceFunction;
import org.example.springaifunctions.functions.StockTickerServiceFunction;
import org.example.springaifunctions.functions.WeatherServiceFunction;
import org.example.springaifunctions.model.Answer;
import org.example.springaifunctions.model.Question;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OpenAiServicesImpl implements OpenAIServices {

    @Value("${sfg.aiapp.apiNinjasKey}")
    private String apiNinjaKey;
    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Override
    public Answer getAnswer( Question question ) {
        return callOpenAiFunction(
                  question,new WeatherServiceFunction(apiNinjaKey));
    }

    @Override
    public Answer getStockPrice( Question question ) {
       return callOpenAiFunction(question, new StockTickerServiceFunction(apiNinjaKey));
}

    private <T,K> Answer callOpenAiFunction( Question question, ServiceFunction<T,K> serviceFunction) {
        OpenAiChatOptions options=OpenAiChatOptions.builder().withFunctionCallbacks(List.of(FunctionCallbackWrapper.builder(serviceFunction)
                                      .withName(serviceFunction.getName())
                                      .withDescription(serviceFunction.getDescription())
                                      .withResponseConverter(response -> ModelOptionsUtils.getJsonSchema(serviceFunction.getClassType(), false)
                                                + "\n" + ModelOptionsUtils.toJsonString(response)).build()))
                  .build();
        Message userMessage= new PromptTemplate(question.question()).createMessage();
        Message systemMessage = new SystemPromptTemplate(serviceFunction.getMessagePromptTemplate()).createMessage();
        return new Answer(openAiChatModel.call(new Prompt(List.of(userMessage, systemMessage), options)).getResult().getOutput().getContent());
    }
}
