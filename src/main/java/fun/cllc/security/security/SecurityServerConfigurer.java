package fun.cllc.security.security;

import fun.cllc.security.security.filter.CustomAuthenticationFilter;
import fun.cllc.security.security.filter.JwtTokenFilter;
import fun.cllc.security.security.handler.LoginFailureHandler;
import fun.cllc.security.security.handler.LoginSuccessHandler;
import fun.cllc.security.security.handler.NoAccessHandler;
import fun.cllc.security.security.handler.NotLoginHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


/**
 * @author chenlei
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class SecurityServerConfigurer extends WebSecurityConfigurerAdapter {

    private final JwtTokenFilter jwtTokenFilter;

    @Autowired
    public SecurityServerConfigurer(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable().logout().disable().rememberMe().disable();
        http.authorizeRequests().mvcMatchers("/actuator/**").permitAll().requestMatchers(CorsUtils::isPreFlightRequest)
                .permitAll().anyRequest().authenticated();
        http.cors();
        http.csrf().disable();
        http.httpBasic().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().authenticationEntryPoint(new NotLoginHandler())
                .accessDeniedHandler(new NoAccessHandler());
        // 如果框架里有ControllerAdvice + ExceptionHandler，并且捕获了Exception，那么这里设置accessDeniedHandler不会生效。
        // 因为accessDeniedHandler方法，表示用户已经登陆了，只是权限不对，已经进入了Controller，
        // 此时抛异常会被ExceptionHandler接住，不会到这里配置的handling里面去。
    }

    private CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        filter.setAuthenticationFailureHandler(new LoginFailureHandler());

        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("Location");
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
