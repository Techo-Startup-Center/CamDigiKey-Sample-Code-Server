# CamDigiKey Client Library

> This repository only includes sample codes for Web Application/Website integration with CamDigiKey, it is not a sample project.


## Currently, the client library of CamDigiKey is available to be implemented in Java and Node.js. To use CamDigiKey client library in your project please follow the steps below:

## 1. CamDigiKey Java Library


1. Register **Jar File** to Local Maven Repository
2. Add CamDigiKey Dependency in **pom.xml**

```xml
<dependency>
  <groupId>kh.gov.camdx.camdigikey</groupId>
  <artifactId>client</artifactId>
  <version>1.5</version>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
    
3. Add CamDigiKey Dependency in **pom.xml**

```properties
# add client credential properties
camdigikey.client-id = <client_id>       # provided by CamDigiKey
camdigikey.hmac-key = <hmac_key>         # provided by CamDigiKey
camdigikey.aes-secret-key = <secret_key> # provided by CamDigiKey
camdigikey.aes-iv-params = <iv_params>   # provided by CamDigiKey
camdigikey.client-domain = <your_application_domain>
camdigikey.server-based-url= <CamDigiKey Url> # DEV : https://mobile-id.demo.camdx.io:8443
                                              # Prod: https://camdigikey.camdx.gov.kh:8443

                        
# add client connection key store file configuration
camdigikey.client-keystore-file = <path to client key store file>
camdigikey.client-keystore-file-password = <password of key store file>
camdigikey.client-keystore-client-key-entry-name= <client_key_entry_name>
camdigikey.client-keystore-client-key-entry-password= <client_key_entry_password>
camdigikey.client-trust-store-file = <path of the trust store>
camdigikey.client-trust-store-file-password = <trust_password>
```

4. Use `@Autowired` annotation of class `CamDigiKeyClient`

    The client Java library provides methods:
    1.	`validateJwt(String jwt) : HashMap<String, Object>`
    2.	`getOrganizationAccessToken() : HashMap<String, Object>`
    3.	`getLoginToken() : HashMap<String, Object>` 
    4.	`getUserAccessToken(String authToken) : HashMap<String, Object>` 
    5.	`refreshUserAccessToken(String accessToken) : HashMap<String, Object>` 
    6.	`logoutAccessToken(String accessToken) : HashMap<String, Object>`
    7.	`lookupUserProfile(String accessToken, String personal_code) : HashMap<String, Object>`
    8.	`verifyUserProfile(String accessToken : HashMap<String, Object>`
    9.	`verifyAccountToken(String accountToken): HashMap<String, Object>`

5. Add a simple Login Page with CamDigiKey Button to request LoginToken from CamDigiKey Client Server Member and Redirect to CamDigiKey Authorization Server

    5.1. Add Login with CamDigiKey Button to request to **/loginToken** from CamDigiKey Client Server Member

    ```html
    <form action="/loginToken" method="POST">
        <div class="text-center mt-2">
            <h1 class="text-2xl font-bold">Login with CamDigiKey account</h1>
        </div>
        <div class="w-full flex justify-center w-auto">
            <button class="w-full mt-4 mb-3 bg-blue-700 hover:bg-blue-600 text-white py-2 rounded-md transition duration-100">
                Login with CamDigiKey
            </button>
        </div>
    </form>
    ```
    
    5.2. Create **/loginToken** Endpoint in CamDigiKey Client Server Member to handle the request from Login Page

    * Sample Model for LoginTokenResponse, AccessTokenResponse, UserInfoResponse
    ```java
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public class LoginTokenResponse {
        @JsonProperty("loginToken")
        private String loginToken;
        
        @JsonProperty("loginUrl")
        private String loginUrl;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public class AccessTokenResponse {
        @JsonProperty("accessToken")
        private String accessToken;
    }

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
    }

    ```
    * In Controller
    ```java
    // Injection CamDigikeyClient object using Constructor, or @Autowired
    @Autowired
    private final CamDigiKeyClient camdigiKeyClientService;
    
    ```

    ```java
    // Request to CamDigiKey Server
    LoginTokenResponse loginTokenResponse = camdigikeyClientService.getLoginToken();

    // Redirect to CamDigiKey Authorization Server via LoginUrl
    return "redirect:" + loginTokenResponse.getLoginUrl();
    ```

    5.3. Create a Success Callback URL Endpoint: **/callback?authToken={AUTH_TOKEN_URL_ENCODED_STRING}**

    ```java
    // in Java Spring we can use @RequestParam("authToken") String authToken in the controller method
    String authToken = {AUTH_TOKEN_URL_ENCODED_STRING};

    // After Success Request to get User Accesss Token by authToken
    HashMap<String, Object> accessTokenResponse = camdigikeyClientService.getUserAccessToken(authToken);
    String accessToken = accessTokenResponse.get("accessToken")

    // To validate the accessToken JWT and get the UserInfo
    HashMap<String, Object> userInfoResponse = camdigikeyClientService.validateJwt(accessToken);

    // You can store the accessToken in your secure place such as Cookie Storage

    // You can use your accessToken to request the apis
    
    ```

    5.4. Create a Fallback Callback URL Endpoint: **/fallback?errorCode={ERROR_CODE}&errorMessage={ERROR_MESSAGE}**
    ```java
    // in Java Spring we can use @RequestParam("errorCode") String errorCode, @RequestParam("errorMessage") String errorMessage in the controller method

    String errorCode = {ERROR_CODE};
    String errorMessage = {ERROR_MESSAGE};
    ```

---
    
# CamDigiKey Client App

CamDigiKey client application is a Java application that build on top of CamDigiKey client library. The client application provides API access (REST) without any authentication. For security concern, please install the client application for local system access only. Please follow the steps to config app credential:

* Edit `src/main/resources/application.properties`: 

```
# add client credential properties
camdigikey.client-id = <client_id>       # provided by CamDigiKey
camdigikey.hmac-key = <hmac_key>         # provided by CamDigiKey
camdigikey.aes-secret-key = <secret_key> # provided by CamDigiKey
camdigikey.aes-iv-params = <iv_params>   # provided by CamDigiKey
camdigikey.client-domain = <your_application_domain>
camdigikey.server-based-url= <CamDigiKey Url> # Dev : https://mobile-id.demo.camdx.io:8443
                                              # Prod: https://camdigikey.camdx.gov.kh:8443

# add client connection key store file configuration
camdigikey.client-keystore-file = <path to client key store file>
camdigikey.client-keystore-file-password = <password of key store file>
camdigikey.client-keystore-client-key-entry-name = <client_key_entry_name>
camdigikey.client-keystore-client-key-entry-password = <client_key_entry_password>
camdigikey.client-trust-store-file = <path of the trust store>
camdigikey.client-trust-store-file-password = <trust_password>
```

* Rebuild and run app
    * Go to app folder
    * Run: `mvn package`
    * Run: `java -jar target/application-1.0.0.jar`


---

## Contact us

- Tel: +855 81 922 329
- Email: info@techostartup.center
- Address: RUPP's Compound Russian Federation Blvd., Toul Kork, Phnom Penh, Cambodia
