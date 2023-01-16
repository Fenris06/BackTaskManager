package client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final HttpClient client;
    private final String urlKVServer;
    private final String apiToken;

    public KVTaskClient(String url) throws URISyntaxException, IOException, InterruptedException {
        this.urlKVServer = url;
        this.client = HttpClient.newHttpClient();
        this.apiToken = authorization();
    }

    private String authorization() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(urlKVServer + "/register"))
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        return response.body();
    }

    public void  put(String key, String json) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(new URI(urlKVServer + "/save/" + key + "?API_TOKEN=" + apiToken))
                .build();
        HttpResponse.BodyHandler<Void> handler = HttpResponse.BodyHandlers.discarding();
        client.send(request, handler);
    }

    public String load(String key)throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(urlKVServer + "/load?" + key + "?API_TOKEN=" + apiToken))
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        return response.body();
    }
}

