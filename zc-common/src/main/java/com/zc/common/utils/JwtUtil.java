package com.zc.common.utils;

import com.zc.common.model.po.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * JWT工具类
 * https://blog.csdn.net/weixin_42109071/article/details/102509076
 * @since: 2020/4/14
 */
public class JwtUtil {

    @Value("${secret_key}")
    private static String SECRET_KEY;

    /**
     * 身份认证
     * @param jwt 令牌
     * @return 成功状态返回200，其它均为失败
     */
    public static User validationToken(String jwt) {
        //解析JWT字符串中的数据，并进行最基础的验证
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwt)
                .getBody();
        User user = new User();
        user.setUserName(claims.get("userName").toString());
        user.setNickName(claims.get("nickName").toString());
        user.setId(Integer.parseInt(claims.get("id").toString()));
        return user;
    }

    /**
     * 构建JWT对象
     * @param user
     * @return
     */
    public static String buildJwt(User user) {
        String jwt = Jwts.builder()
                // 使用HS256加密算法
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                // 过期时间
                .setExpiration(new Date(System.currentTimeMillis() + 30*1000))
                .claim("userName",user.getUserName())
                .claim("nickName",user.getNickName())
                .claim("id",user.getId())
                .compact();
        return jwt;
    }

}
