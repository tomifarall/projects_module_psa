package com.pca.projects.projects_module.service.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public abstract class ApiClient {
    private static final String BASE_URL = "https://rrhh-squad6-1c2023.onrender.com/";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final CloseableHttpClient httpClient;

    public ApiClient() {
        httpClient = HttpClients.createDefault();
    }

    public <T> T get(String endpoint, Class<T> responseType) throws IOException {
        String apiUrl = BASE_URL + endpoint;
        HttpGet httpGet = new HttpGet(apiUrl);
        HttpResponse response = httpClient.execute(httpGet);
        return handleResponse(response, responseType);
    }

    public <T> T post(String endpoint, Object requestBody, Class<T> responseType) throws IOException {
        String apiUrl = BASE_URL + endpoint;
        HttpPost httpPost = new HttpPost(apiUrl);
        String requestBodyJson = objectMapper.writeValueAsString(requestBody);
        StringEntity requestEntity = new StringEntity(requestBodyJson, ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(httpPost);
        return handleResponse(response, responseType);
    }

    public <T> T put(String endpoint, Object requestBody, Class<T> responseType) throws IOException {
        String apiUrl = BASE_URL + endpoint;
        HttpPut httpPut = new HttpPut(apiUrl);
        String requestBodyJson = objectMapper.writeValueAsString(requestBody);
        StringEntity requestEntity = new StringEntity(requestBodyJson, ContentType.APPLICATION_JSON);
        httpPut.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(httpPut);
        return handleResponse(response, responseType);
    }

    public <T> T delete(String endpoint, Class<T> responseType) throws IOException {
        String apiUrl = BASE_URL + endpoint;
        HttpDelete httpDelete = new HttpDelete(apiUrl);
        HttpResponse response = httpClient.execute(httpDelete);
        return handleResponse(response, responseType);
    }

    private <T> T handleResponse(HttpResponse response, Class<T> responseType) throws IOException {
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            return objectMapper.readValue(responseString, responseType);
        }

        return null;
    }
}
