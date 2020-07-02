package com.zc.common.utils;

import com.zc.common.model.po.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT工具类
 * https://blog.csdn.net/weixin_42109071/article/details/102509076
 * @author zichen
 * @date 2020年7月2日
 */
public class JwtUtil {

    /**
     * 身份认证
     * @param jwt 令牌
     * @return 成功状态返回200，其它均为失败
     */
    public static User validationToken(String jwt, String secret_key) {
        //解析JWT字符串中的数据，并进行最基础的验证
        Claims claims = Jwts.parser()
                .setSigningKey(secret_key)
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
    public static String buildJwt(User user, String secret_key) {
        String jwt = Jwts.builder()
                // 使用HS256加密算法
                .signWith(SignatureAlgorithm.HS256, secret_key)
                // 过期时间
                .setExpiration(new Date(System.currentTimeMillis() + 30*1000))
                .claim("userName",user.getUserName())
                .claim("nickName",user.getNickName())
                .claim("id",user.getId())
                .compact();
        return jwt;
    }

}
