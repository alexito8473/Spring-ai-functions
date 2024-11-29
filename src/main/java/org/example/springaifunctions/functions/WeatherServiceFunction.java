package org.example.springaifunctions.functions;

import org.example.springaifunctions.model.WeatherRequest;
import org.example.springaifunctions.model.WeatherResponse;
import org.springframework.web.client.RestClient;

public class WeatherServiceFunction extends ServiceFunction<WeatherRequest, WeatherResponse> {

    public WeatherServiceFunction( String apiKey ) {
        super(apiKey,
                  "https://api.api-ninjas.com/v1/weather",
                  "You are a weather service. You receive weather information from a service which gives you the information based on the metrics system.When answering the weather in an imperial system country, you should convert the temperature to Fahrenheit and the wind speed to miles per hour. ",
                  "Weather",
                  "Get current weather for a location",
                  WeatherResponse.class);
    }

    @Override
    public WeatherResponse apply( WeatherRequest weatherRequest ) {
        RestClient restClient = createRestClient();
        return restClient.get().uri(uriBuilder -> {
            System.out.println("Building Uri");
            uriBuilder.queryParam("lat", weatherRequest.lat());
            uriBuilder.queryParam("lon", weatherRequest.lon());
            return uriBuilder.build();
        }).retrieve().body(WeatherResponse.class);
    }
}
