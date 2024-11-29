package org.example.springaifunctions.functions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springaifunctions.model.StockPriceRequest;
import org.example.springaifunctions.model.StockPriceResponse;
import org.springframework.web.client.RestClient;

public class StockTickerServiceFunction extends ServiceFunction<StockPriceRequest, StockPriceResponse> {
    public StockTickerServiceFunction( String apiKey ) {
        super(apiKey,
                  "https://api.api-ninjas.com/v1/stockprice",
                  "You are an agent which returns back a stock price for the given stock symbol (or ticker)",
                  "CurrentStockPrice",
                  "Get the current stock price for a stock symbol",
                  StockPriceResponse.class);
    }

    @Override
    public StockPriceResponse apply( StockPriceRequest stockTickerRequest ) {
        RestClient restClient = createRestClient();
        JsonNode jsonNode = restClient.get().uri(uriBuilder -> uriBuilder.queryParam("ticker", stockTickerRequest.ticker()).build())
                  .retrieve().body(JsonNode.class);
        if (jsonNode.isEmpty()) {
            return new StockPriceResponse(null, null, null, null, null ,null);
        } else {
            return new ObjectMapper().convertValue(jsonNode,StockPriceResponse.class);
        }
    }
}
