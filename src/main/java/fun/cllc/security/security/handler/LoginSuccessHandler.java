package fun.cllc.security.security.handler;

import fun.cllc.security.security.constant.SecurityConstant;
import fun.cllc.security.util.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author chenlei
 * @date 2019-12-19
 */
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Authentication authentication) {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try (Writer writer = httpServletResponse.getWriter()) {
            String jwt = JwtHelper.createJWT(authentication.getName(), SecurityConstant.JWT_PRIVATE_KEY,
                    SecurityConstant.JWT_VALID_DAYS);
            writer.write(jwt);
            writer.flush();
        } catch (IOException ignored) {
            log.error("Failed to write response msg");
        }
    }
}
