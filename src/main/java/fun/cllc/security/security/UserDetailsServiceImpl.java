package fun.cllc.security.security;

import fun.cllc.security.security.constant.RoleConstant;
import fun.cllc.security.security.model.RoleDTO;
import fun.cllc.security.security.model.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


/**
 * @author chenlei
 * @date 2019-12-19
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        // ... select user info from db
        UserDTO user = new UserDTO("admin", "{noop}admin", true);
        user.addAuthorities(new RoleDTO(RoleConstant.A));

        return user;
    }
}
