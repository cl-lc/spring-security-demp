package fun.cllc.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * @author chenlei
 * @date 2019-12-19
 */
public class JwtHelper {
    /**
     * create JWT
     *
     * @param username
     * @param secret
     * @param validDays
     * @return
     */
    public static String createJWT(String username, String secret, int validDays) {
        JwtBuilder builder = Jwts.builder();
        builder.setExpiration(DateUtils.addDays(new Date(), validDays));
        builder.setSubject(username);

        return builder.signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    /**
     * @param token
     * @param secret
     * @return
     */
    private static Claims parseClaims(String token, String secret) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception ignored) {
            return null;
        }

        return claims;
    }

    /**
     * @param token
     * @param secret
     * @return
     */
    public static String getUserName(String token, String secret) {
        Claims claims = parseClaims(token, secret);
        if (claims == null) {
            return null;
        }

        return claims.getSubject();
    }

    /**
     * @param token
     * @param secret
     * @return
     */
    public static Boolean isExpired(String token, String secret) {
        Claims claims = parseClaims(token, secret);
        if (claims == null) {
            return true;
        }

        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }
}
