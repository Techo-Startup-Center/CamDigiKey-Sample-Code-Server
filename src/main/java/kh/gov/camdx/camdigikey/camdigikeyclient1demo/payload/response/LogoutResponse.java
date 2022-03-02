package kh.gov.camdx.camdigikey.camdigikeyclient1demo.payload.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LogoutResponse {
    private String status;
    private String expiry;
    private String issued;
    private String accessToken;
}
