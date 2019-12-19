package fun.cllc.security.security.model;

import fun.cllc.security.security.constant.RoleConstant;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author chenlei
 * @date 2019-12-19
 */
@ToString
public class RoleDTO implements GrantedAuthority {
    private String name;

    public RoleDTO(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return RoleConstant.PREFIX + name;
    }
}
