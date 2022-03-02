package kh.gov.camdx.camdigikey.camdigikeyclient1demo.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginTokenResponse {
    @JsonProperty("loginToken")
    private String loginToken;
    @JsonProperty("loginUrl")
    private String loginUrl;
}
