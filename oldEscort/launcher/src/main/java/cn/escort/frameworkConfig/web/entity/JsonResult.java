package cn.escort.frameworkConfig.web.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "通用响应码")
@Getter
@Setter
public class JsonResult<T> {
    @Schema(description = "非200 其他都是错误码")
    private int statusCode;

    @Schema(description = "错误信息")
    private String message;

    @Schema(description = "真实数据")
    private T data;

    private JsonResult(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<>(200, "OK", data);
    }

    public static <T> JsonResult<T> success(String message, T data) {
        return new JsonResult<>(200, message, data);
    }

    public static <T> JsonResult<T> error(int statusCode, String message) {
        return new JsonResult<>(statusCode, message, null);
    }
}
