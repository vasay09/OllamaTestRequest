package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import org.json.JSONObject;

class OllamaClient {

    private static final String OLLAMA_API_URL = "http://localhost:11434/api/generate"; // URL API Ollama
    private static final String MODEL_NAME = "hf.co/bartowski/google_gemma-3-4b-it-GGUF:BF16"; // Имя модели

    public static void main(String[] args) {
        String prompt = "Что ты знаешь об солнце?"; // Ваш запрос к модели

        try {
            String response = sendRequestToOllama(prompt);
            System.out.println("Combined Response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String sendRequestToOllama(String prompt) throws Exception {
        // Создаем JSON-тело запроса
        String jsonBody = String.format("{\"model\": \"%s\", \"prompt\": \"%s\"}", MODEL_NAME, prompt);

        // Создаем HTTP-клиент
        HttpClient client = HttpClient.newHttpClient();

        // Создаем HTTP-запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OLLAMA_API_URL))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(jsonBody))
                .build();

        // Отправляем запрос и получаем ответ
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        // Обрабатываем ответ
        StringBuilder combinedResponse = new StringBuilder();
        String[] lines = response.body().split("\n");

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                JSONObject jsonObject = new JSONObject(line);
                combinedResponse.append(jsonObject.getString("response"));
            }
        }

        // Возвращаем объединенный ответ
        return combinedResponse.toString();
    }
}