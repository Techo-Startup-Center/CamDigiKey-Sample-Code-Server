package kh.gov.camdx.camdigikey.camdigikeyclient1demo.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserInfoResponse {
    @JsonProperty("personal_code")
    private String personalCode;
    @JsonProperty("first_name_kh")
    private String firstNameKh;
    @JsonProperty("last_name_kh")
    private String lastNameKh;
    @JsonProperty("first_name_en")
    private String firstNameEn;
    @JsonProperty("last_name_en")
    private String lastNameEn;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("mobile_phone")
    private String mobilePhone;
    @JsonProperty("email")
    private String email;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("status")
    private String status;
    @JsonProperty("iss")
    private String iss;
    @JsonProperty("type")
    private String type;
    @JsonProperty("camdigikey_id")
    private String camdigikeyId;
    @JsonProperty("exp")
    private String exp;
}
