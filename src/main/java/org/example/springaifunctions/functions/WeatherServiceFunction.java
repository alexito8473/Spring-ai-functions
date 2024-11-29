package org.example.springaifunctions.functions;

import org.example.springaifunctions.model.WeatherRequest;
import org.example.springaifunctions.model.WeatherResponse;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

public class WeatherServiceFunction implements Function<WeatherRequest, WeatherResponse> {

    public static final String WEATHER_URL = "https://api.api-ninjas.com/v1/weather";

    private final String apiNinjaKey;

    public WeatherServiceFunction(String apiNinjaKey) {
        this.apiNinjaKey=apiNinjaKey;
    }

    @Override
    public WeatherResponse apply( WeatherRequest weatherRequest ) {
        RestClient restClient = RestClient.builder()
                  .baseUrl(WEATHER_URL)
                  .defaultHeaders(httpHeaders ->{
                      httpHeaders.set("X-Api-Key",apiNinjaKey);
                      httpHeaders.set("Accept","application/json");
                      httpHeaders.set("Content-Type","application/json");
                  })
                  .build();
        return restClient.get().uri(uriBuilder -> {
            System.out.println("Building Uri");
            uriBuilder.queryParam("lat",weatherRequest.lat());
            uriBuilder.queryParam("lon",weatherRequest.lon());
            return uriBuilder.build();
        }).retrieve().body(WeatherResponse.class);
    }
}
