package org.example.springaifunctions.services;

import lombok.RequiredArgsConstructor;
import org.example.springaifunctions.functions.WeatherServiceFunction;
import org.example.springaifunctions.model.Answer;
import org.example.springaifunctions.model.Question;
import org.example.springaifunctions.model.WeatherResponse;
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
        OpenAiChatOptions options=OpenAiChatOptions.builder().
                  withFunctionCallbacks(List.of(
                            FunctionCallbackWrapper.builder(
                                      new WeatherServiceFunction(apiNinjaKey))
                                      .withName("weather")
                                      .withDescription("CurrentWeather")
                                      .withDescription("Get current weather for a location")
                                      .withResponseConverter(weatherResponse -> ModelOptionsUtils.getJsonSchema(WeatherResponse.class, false) + "\n" + ModelOptionsUtils.toJsonString(weatherResponse)).build()))
                  .build();
        Message userMessage= new PromptTemplate(question.question()).createMessage();
        Message systemMessage = new SystemPromptTemplate("You are a weather service. You receive weather information from a service which gives you the information based on the metrics system." +
                  " When answering the weather in an imperial system country, you should convert the temperature to Fahrenheit and the wind speed to miles per hour. ").createMessage();
        return new Answer(openAiChatModel.call(new Prompt(List.of(userMessage,systemMessage),options)).getResult().getOutput().getContent());
    }
}
