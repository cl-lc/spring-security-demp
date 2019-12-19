package fun.cllc.security;

import fun.cllc.security.security.constant.RoleConstant;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenlei
 * @date 2019-12-19
 */
@RestController
public class TestController {
    @Secured(RoleConstant.ROLE_A)
    @RequestMapping(value = "/a", method = RequestMethod.GET)
    public String a() {
        return "This is a";
    }

    @Secured(RoleConstant.ROLE_B)
    @RequestMapping(value = "/b", method = RequestMethod.GET)
    public String b() {
        return "This is b";
    }
}
