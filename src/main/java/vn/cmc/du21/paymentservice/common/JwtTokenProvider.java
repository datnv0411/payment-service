package vn.cmc.du21.paymentservice.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import vn.cmc.du21.paymentservice.presentation.internal.response.UserResponse;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class JwtTokenProvider {
    private JwtTokenProvider() {
        throw new IllegalStateException("Utility class");
    }

    public static UserResponse getInfoUserFromToken(HttpServletRequest request, Environment env) throws AuthenticationException {

        log.info("Mapped getInfoUserFromToken method");

        UserResponse userLogin;

        try
        {
            String[] arr = request.getHeader("Authorization").split(" ");
            String token = arr[1];
            final String uri = env.getProperty("path.user-service") + "/api/v1.0/authentication/verify?token=" + token;
            RestTemplate restTemplate = new RestTemplate();

            userLogin = restTemplate.getForObject(uri, UserResponse.class);
        }
        catch (Exception e)
        {
            throw new AuthenticationException("BAD token !!!");
        }

        return userLogin;
    }
}
