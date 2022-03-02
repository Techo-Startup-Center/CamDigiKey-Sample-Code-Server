package kh.gov.camdx.camdigikey.camdigikeyclient1demo.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AccessTokenRequest {
    @JsonProperty("authToken")
    @NotEmpty(message = "AuthToken cannot be null or empty")
    private String authToken;

}
