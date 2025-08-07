package cn.escort.frameworkConfig.web.decrypt;

import cn.escort.frameworkConfig.web.encrypt.Encrypt;
import cn.escort.utils.crypto.AesUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;



@ControllerAdvice
public class DecryptRequestAdvice implements RequestBodyAdvice {
    private static  final byte[] aesKey = "oyb8ZvjMd+VvP/mQ".getBytes();
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        if(methodParameter.getMethod().isAnnotationPresent(Decrypt.class)){
            return true;
        }
        if(methodParameter.hasParameterAnnotation(Decrypt.class)){
            return true;
        }
        return false;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        // 获取原始请求数据
        byte[] encryptedData = StreamUtils.copyToByteArray(inputMessage.getBody());

        final byte[] bytes = AesUtils.diyDecrypt(encryptedData, aesKey);

        HttpInputMessage decryptedInputMessage = new HttpInputMessage() {
            @Override
            public InputStream getBody() {
                return new ByteArrayInputStream(bytes);
            }
            @Override
            public HttpHeaders getHeaders() {
                return inputMessage.getHeaders();
            }
        };

        return decryptedInputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    public static void main(String[] args) {
        final String qwqwe = AesUtils.diyEncrypt("stesdfdsdfsdf", aesKey);
        final String s = AesUtils.diyDecrypt("Lj3BPisWRExYQMNcTYpcwvOUp1R0GwWwTaNYDxZgucs2mt7MLsDo8vVoFi5mDvN9/UHixfF8VpyCCG1pCF86Jg==", "oyb8ZvjMd+VvP/mQ".getBytes());
        System.out.println(s);
    }
}