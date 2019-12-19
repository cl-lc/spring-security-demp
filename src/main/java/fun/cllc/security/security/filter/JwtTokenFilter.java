package fun.cllc.security.security.filter;

import fun.cllc.security.security.constant.RoleConstant;
import fun.cllc.security.security.constant.SecurityConstant;
import fun.cllc.security.util.JwtHelper;
import fun.cllc.security.security.model.RoleDTO;
import fun.cllc.security.security.model.UserDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenlei
 * @date 2019-12-19
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        filterAuthentication(httpServletRequest);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void filterAuthentication(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(SecurityConstant.JWT_TOKEN_HEADER);
        if (StringUtils.isEmpty(token)) {
            return;
        }
        if (JwtHelper.isExpired(token, SecurityConstant.JWT_PRIVATE_KEY)) {
            return;
        }

        String username = JwtHelper.getUserName(token, SecurityConstant.JWT_PRIVATE_KEY);
        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }

        // ... select user info from db by username
        UserDTO user = new UserDTO("admin", "{noop}admin", true);
        user.addAuthorities(new RoleDTO(RoleConstant.A));

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
