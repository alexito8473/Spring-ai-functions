package org.example.springaifunctions.functions;
import lombok.Getter;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

@Getter
public abstract class ServiceFunction<T, K> implements Function<T, K> {
    protected final String url;
    protected final String messagePromptTemplate;
    protected final String name;
    protected final String description;
    protected final Class<K> classType;
    protected final String apiKey;

    public ServiceFunction(String apiKey,
                           String url,
                           String messagePromptTemplate,
                           String name,
                           String description,
                           Class<K> classType) {
        this.apiKey = apiKey;
        this.url = url;
        this.messagePromptTemplate = messagePromptTemplate;
        this.name = name;
        this.description = description;
        this.classType = classType;
    }

    @Override
    public abstract K apply(T t);

    protected RestClient createRestClient() {
        return RestClient.builder()
                  .baseUrl(url)
                  .defaultHeaders(httpHeaders -> {
                      httpHeaders.set("X-Api-Key", apiKey);
                      httpHeaders.set("Accept", "application/json");
                      httpHeaders.set("Content-Type", "application/json");
                  }).build();
    }
}
