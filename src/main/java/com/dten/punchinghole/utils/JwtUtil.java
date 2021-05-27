package com.dten.punchinghole.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
    private Long EXPIRATION_TIME;
    private String SECRET;
    private final String TOKEN_PREFIX = "Bearer";
    private final String HEADER_STRING = "Authorization";

    public JwtUtil(String secret, long expire) {
        this.EXPIRATION_TIME = expire;
        this.SECRET = secret;

        System.out.println("正在初始化Jwt，expire=" + expire);
    }

    public JSONObject createToken(Map<String, Object> claims) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.SECOND, EXPIRATION_TIME.intValue());
        Date d = c.getTime();
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setExpiration(d)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        JSONObject json = new JSONObject();
        json.put("token", TOKEN_PREFIX + " " + jwt);
        json.put("token-type", TOKEN_PREFIX);
        json.put("expire-time", new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(d) );
        return json;
    }

    public Map<String, Object> validateToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        System.out.println("token is:"+token);
        if (token == null) {
            return null;
        }
        Map<String, Object> body = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
        return body;
    }

    /**
     * 获取Token中注册信息
     *
     * @param token
     * @return
     */
    public Claims getBody(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    /**
     * 获取签发日期
     *
     * @param token
     * @return
     */
    public Date getIssuedAt(String token) {
        return getBody(token).getIssuedAt();
    }

    /**
     * 获取过期时间
     *
     * @param token
     * @return
     */
    public Date getExpiration(String token) {
        return getBody(token).getExpiration();
    }

    /**
     * 获取主题信息
     *
     * @param token
     * @return
     */
    public String getSubject(String token) {
        return getBody(token).getSubject();
    }
}
