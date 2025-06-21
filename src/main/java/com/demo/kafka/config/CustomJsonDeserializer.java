package com.demo.kafka.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import java.io.IOException;

@Slf4j
public class CustomJsonDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getValueAsString();
        log.info("JSON string value: {}", value);
        if (StringUtils.isEmpty(value)) {
            return value;
        }

        String escapedValue = Jsoup.clean(value, Safelist.basic());
        log.info("JSON string value escaped: {}", escapedValue);
        return escapedValue;
    }
}
