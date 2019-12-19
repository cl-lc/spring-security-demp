package fun.cllc.security.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author chenlei
 * @date 2019-12-19
 */
@Slf4j
public class NoAccessHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            AccessDeniedException e) {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try (Writer writer = httpServletResponse.getWriter()) {
            writer.write("Access denied");
            writer.flush();
        } catch (IOException ignored) {
            log.error("Failed to write response msg");
        }
    }
}
