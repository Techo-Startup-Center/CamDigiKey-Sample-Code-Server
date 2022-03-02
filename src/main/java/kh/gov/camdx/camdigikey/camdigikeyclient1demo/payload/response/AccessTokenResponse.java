package kh.gov.camdx.camdigikey.camdigikeyclient1demo.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AccessTokenResponse {
    @JsonProperty("accessToken")
    private String accessToken;
}
