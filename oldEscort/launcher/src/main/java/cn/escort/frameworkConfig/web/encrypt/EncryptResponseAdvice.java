package cn.escort.frameworkConfig.web.encrypt;


import cn.escort.frameworkConfig.web.entity.JsonResult;
import cn.escort.utils.crypto.AesUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@RestControllerAdvice
//@Order(1)
public class EncryptResponseAdvice implements ResponseBodyAdvice<JsonResult> {

    private  ObjectMapper objectMapper = new ObjectMapper();
    private  final byte[] aesKey = "KDHpjtQuysmq8rVO".getBytes();

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        return returnType.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public JsonResult beforeBodyWrite(JsonResult body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        try {
            final byte[] bytes = objectMapper.writeValueAsBytes(body.getData());
            final String data = AesUtils.diyEncrypt(bytes, aesKey);
            return JsonResult.success(body.getMessage(),data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}