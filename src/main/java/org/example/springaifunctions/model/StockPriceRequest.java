package org.example.springaifunctions.model;

import com.fasterxml.jackson.annotation.*;

@JsonClassDescription("Stock price request")
public record StockPriceRequest(@JsonPropertyDescription("ticker name of the stock to quote") String ticker) { }