package fun.cllc.security.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 前后端分离的方式，前端POST数据过来，那么需要重写attemptAuthentication，以正确获得username和password
 *
 * @author chenlei
 * @date 2019-12-19
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        ObjectMapper mapper = new ObjectMapper();
        UsernamePasswordAuthenticationToken authRequest;
        try (InputStream is = request.getInputStream()) {
            AuthenticationBean authenticationBean = mapper.readValue(is, AuthenticationBean.class);
            authRequest = new UsernamePasswordAuthenticationToken(authenticationBean.getUsername(),
                    authenticationBean.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
            authRequest = new UsernamePasswordAuthenticationToken("", "");
        }
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Data
    private static class AuthenticationBean {
        private String username;
        private String password;
    }
}
