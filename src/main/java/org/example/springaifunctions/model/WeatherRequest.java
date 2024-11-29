package org.example.springaifunctions.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties("Weather API request")
public record WeatherRequest(
          @JsonProperty(required = true,value = "lat")
          @JsonPropertyDescription("Latitude of desired location.")
          String lat,
          @JsonProperty(required = true,value = "lon")
          @JsonPropertyDescription("Longitude of desired location.")
          String lon) { }
