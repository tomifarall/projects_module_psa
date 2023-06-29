package com.pca.projects.projects_module.service.client;

import com.pca.projects.projects_module.exception.BadRequestException;
import com.pca.projects.projects_module.exception.BaseAPIException;
import com.pca.projects.projects_module.exception.NotFoundException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;

public abstract class ApiClient {

    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CloseableHttpClient httpClient;

    public ApiClient() {
        httpClient = HttpClients.createDefault();
    }

    public <T> T get(String endpoint, Class<T> responseType) throws IOException {
        HttpGet httpGet = new HttpGet(endpoint);
        HttpResponse response = httpClient.execute(httpGet);
        return handleResponse(response, responseType);
    }

    public <T> T post(String endpoint, Object requestBody, Class<T> responseType) throws IOException {
        HttpPost httpPost = new HttpPost(endpoint);
        String requestBodyJson = objectMapper.writeValueAsString(requestBody);
        StringEntity requestEntity = new StringEntity(requestBodyJson, ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(httpPost);
        return handleResponse(response, responseType);
    }

    public <T> T put(String endpoint, Object requestBody, Class<T> responseType) throws IOException {
        HttpPut httpPut = new HttpPut(endpoint);
        String requestBodyJson = objectMapper.writeValueAsString(requestBody);
        StringEntity requestEntity = new StringEntity(requestBodyJson, ContentType.APPLICATION_JSON);
        httpPut.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(httpPut);
        return handleResponse(response, responseType);
    }

    public <T> T delete(String endpoint, Class<T> responseType) throws IOException {
        HttpDelete httpDelete = new HttpDelete(endpoint);
        HttpResponse response = httpClient.execute(httpDelete);
        return handleResponse(response, responseType);
    }

    private <T> T handleResponse(HttpResponse response, Class<T> responseType) throws IOException {

        HttpStatus status = HttpStatus.resolve(response.getStatusLine().getStatusCode());

        if (status.is2xxSuccessful()) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseString = EntityUtils.toString(entity);
                return objectMapper.readValue(responseString, responseType);
            }
        }
        throw new HttpClientErrorException(status, response.getStatusLine().getReasonPhrase());
    }

    protected void handleClientException(HttpStatusCodeException ex, String method) {
        switch (ex.getStatusCode()) {
            case BAD_REQUEST:
                throw new BadRequestException(String.format("Bad Request performing method: %s", method),
                        ex.getStatusCode(), HttpStatus.BAD_REQUEST.name().toLowerCase());
            case NOT_FOUND:
                throw new NotFoundException(String.format("Entity not found performing method: %s", method),
                        ex.getStatusCode(), HttpStatus.NOT_FOUND.name().toLowerCase());
            default:
                throw new BaseAPIException(String.format("Something went wrong performing method: %s", method),
                        HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name().toLowerCase());
        }
    }
}
