package kh.gov.camdx.camdigikey.camdigikeyclient1demo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@Builder
@ToString
public class ApiResponse<T> {
    @JsonProperty("error")
    private Integer error;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    @JsonProperty("payload")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;

    public ApiResponse() {
        this.error = 0;
        this.message = "OK";
    }

    public ApiResponse(Integer code, String message) {
        this.error = code;
        this.message = message;
    }

    public ApiResponse(T data) {
        this.error = 0;
        this.message = "OK";
        this.data = data;
    }

    public ApiResponse(Integer code, T data) {
        this.error = 0;
        this.data = data;
    }
}
