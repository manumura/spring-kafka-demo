package com.demo.kafka.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@ControllerAdvice
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<?
            extends HttpMessageConverter<?>> converterType) throws IOException {
        String body = IOUtils.toString(inputMessage.getBody(), UTF_8);
        log.info("beforeBodyRead - Data from Body: {}", body);
//        String escapedBody = Jsoup.clean(body, Safelist.basic()).replace("\"","\\\"");
//        log.info("Escaped Data from Body: {}", escapedBody);

        HttpInputMessage myMessage = new HttpInputMessage(){
            @Override
            public InputStream getBody() {
                return new ByteArrayInputStream(body.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                return inputMessage.getHeaders();
            }
        };

        return super.beforeBodyRead(myMessage, parameter, targetType, converterType);
    }

    @Nullable
    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("Data from Body is empty");
        return super.handleEmptyBody(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("afterBodyRead - Type: {}", targetType.getTypeName());

        try {
            String bodyAsJsonString = objectMapper.writeValueAsString(body);
            log.info("afterBodyRead - Data from Body as json string: {}", bodyAsJsonString);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
}
