package fun.cllc.security.security.model;

import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author chenlei
 * @date 2019-12-19
 */
@ToString
public class UserDTO implements UserDetails {
    private String username;
    private String password;
    private Boolean enable;
    private List<GrantedAuthority> authorities;

    public UserDTO(String username, String password, Boolean enable) {
        this.username = username;
        this.password = password;
        this.enable = enable;
        this.authorities = new ArrayList<>();
    }

    public void addAuthorities(GrantedAuthority authority) {
        this.authorities.add(authority);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}
