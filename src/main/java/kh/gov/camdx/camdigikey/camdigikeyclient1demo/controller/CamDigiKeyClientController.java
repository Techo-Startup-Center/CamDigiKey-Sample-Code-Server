package kh.gov.camdx.camdigikey.camdigikeyclient1demo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kh.gov.camdx.camdigikey.camdigikeyclient1demo.common.ApiResponse;
import kh.gov.camdx.camdigikey.camdigikeyclient1demo.payload.request.AccessTokenRequest;
import kh.gov.camdx.camdigikey.camdigikeyclient1demo.payload.response.AccessTokenResponse;
import kh.gov.camdx.camdigikey.camdigikeyclient1demo.payload.response.LoginTokenResponse;
import kh.gov.camdx.camdigikey.camdigikeyclient1demo.payload.response.LogoutResponse;
import kh.gov.camdx.camdigikey.camdigikeyclient1demo.payload.response.UserInfoResponse;
import kh.gov.camdx.camdigikey.client.CamDigiKeyClient;
import kh.gov.camdx.camdigikey.client.exception.DecryptPayloadErrorException;
import kh.gov.camdx.camdigikey.client.exception.InvalidTokenSignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CamDigiKeyClientController {

    private final CamDigiKeyClient camDigiKeyClient;
    private final ObjectMapper mapper;

    @GetMapping({"/login", ""})
    public String loginPage() {
        return "login";
    }

    @PostMapping("/loginToken")
    public String requestLoginTokenAndRedirectToAuthServer() throws InvalidTokenSignatureException {
        final ApiResponse<LoginTokenResponse> apiResponse = mapper.convertValue(camDigiKeyClient.getLoginToken(), new TypeReference<ApiResponse<LoginTokenResponse>>() {
        });
        return "redirect:" + apiResponse.getData().getLoginUrl();
    }


    @PostMapping("/accessToken")
    @ResponseBody
    public ApiResponse<AccessTokenResponse> requestAccessToken(@RequestBody AccessTokenRequest request) throws InvalidTokenSignatureException, UnsupportedEncodingException {
        final ApiResponse<AccessTokenResponse> apiResponse = mapper.convertValue(camDigiKeyClient.getUserAccessToken(request.getAuthToken()), new TypeReference<ApiResponse<AccessTokenResponse>>() {
        });
        return new ApiResponse<>(apiResponse.getData());
    }

    @PostMapping("/userInfo")
    @ResponseBody
    public ApiResponse<UserInfoResponse> requestUserInfo(@RequestHeader("Authorization") String accessToken) throws InvalidTokenSignatureException {
        System.out.println(accessToken);
        final ApiResponse<UserInfoResponse> apiResponse = mapper.convertValue(camDigiKeyClient.validateJwt(accessToken.replaceAll("Bearer ", "")), new TypeReference<ApiResponse<UserInfoResponse>>() {
        });
        if (apiResponse.getError() == 0) {
            return new ApiResponse<>(apiResponse.getPayload());
        }
        throw new RuntimeException("RequestUserInfo is error");
    }

    @PostMapping("/logout")
    @ResponseBody
    public ApiResponse<LogoutResponse> requestLogout(@RequestHeader("Authorization") String accessToken) throws InvalidTokenSignatureException, DecryptPayloadErrorException {
        System.out.println(accessToken);
        final ApiResponse<LogoutResponse> apiResponse = mapper.convertValue(camDigiKeyClient.logoutAccessToken(accessToken.replaceAll("Bearer ", "")), new TypeReference<ApiResponse<LogoutResponse>>() {
        });
        if (apiResponse.getError() == 0) {
            return new ApiResponse<>(apiResponse.getPayload());
        }
        throw new RuntimeException("RequestLogout is error");
    }

    @GetMapping("/callback")
    public String authenticationCallbackURL(@RequestParam("authToken") String authToken, Model model) {
        model.addAttribute("authToken", authToken);
        return "success";
    }

    @GetMapping("/fallback")
    public String authenticationFallbackURL(@RequestParam("errorCode") String errorCode, @RequestParam("errorMessage") String errorMessage, Model model) {
        model.addAttribute("errorCode", errorCode);
        model.addAttribute("errorMessage", errorMessage);
        return "fallback";
    }
}
