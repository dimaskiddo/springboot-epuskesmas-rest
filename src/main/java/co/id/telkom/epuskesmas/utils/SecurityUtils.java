package co.id.telkom.epuskesmas.utils;

public class SecurityUtils {

    public static final String SECRET = "GYF32mlnIXVuqAGuDgohzurOB9IBDg===";
    public static final long EXPIRATION_TIME = 900_000; // 15 Minutes
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_IN_URL = "/api/v1/login";
    public static final String SIGN_UP_URL = "/api/v1/register";

}
