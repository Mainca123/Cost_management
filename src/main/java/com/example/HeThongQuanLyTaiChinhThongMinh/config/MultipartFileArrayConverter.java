package com.example.HeThongQuanLyTaiChinhThongMinh.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.Iterator;

@Component
public class MultipartFileArrayConverter implements ModelConverter {

    @Override
    public Schema<?> resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain) {
        Type javaType = type.getType();
        JavaType jt = TypeFactory.defaultInstance().constructType(javaType);

        if (jt.isArrayType() && MultipartFile.class.isAssignableFrom(jt.getContentType().getRawClass())) {
            ArraySchema arraySchema = new ArraySchema();
            arraySchema.items(new StringSchema().format("binary"));
            return arraySchema;
        }

        if (chain.hasNext()) {
            return chain.next().resolve(type, context, chain);
        }
        return null;
    }
}
