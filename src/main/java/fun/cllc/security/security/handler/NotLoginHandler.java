package fun.cllc.security.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author chenlei
 * @date 2019-12-19
 */
@Slf4j
public class NotLoginHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            AuthenticationException e) {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try (Writer writer = httpServletResponse.getWriter()) {
            writer.write("User not login");
            writer.flush();
        } catch (IOException ignored) {
            log.error("Failed to write response msg");
        }
    }
}
