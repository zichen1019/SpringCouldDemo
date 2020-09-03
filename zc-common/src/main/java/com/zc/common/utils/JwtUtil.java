package com.zc.common.utils;

import com.alibaba.fastjson.JSON;
import com.zc.common.config.enums.ResultCode;
import com.zc.common.exception.BusinessException;
import com.zc.common.model.po.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * JWT工具类
 *
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
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret_key)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.err.println("jwt token 验证已过期");
            throw new BusinessException(ResultCode.AUTH_EXPIRED);
        }
        return JSON.parseObject(claims.get("user").toString(), User.class);
    }

    /**
     * 构建JWT对象
     * @param user
     * @return
     */
    public static String buildJwt(User user, String secret_key) {
        return Jwts.builder()
                // 使用HS256加密算法
                .signWith(SignatureAlgorithm.HS256, secret_key)
                // 签发时间
                .setIssuedAt(new Date())
                // 过期时间
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 1000))
                .claim("user", JSON.toJSONString(user))
                .compact();
    }

    public static void main(String[] args) throws InterruptedException {
        User user = User.builder().userName("zichen").nickName("zc").build();
        user.setId(1);
        String eny = buildJwt(user, "123456");
        System.err.println(eny);
        Thread.sleep(10000);
        User user1 = validationToken(eny, "123456");
        System.err.println(user1.getNickName());
    }

}
