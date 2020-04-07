package com.zjstudio.happly_birthday.tools.common;

import com.zjstudio.happly_birthday.common.StatusCode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TokenUtil
 * @Description TODO token工具类
 * @Author 添柴灬少年
 * @DATE 2019/6/18 9:59
 * @Version 1.0
 **/
public class TokenUtil {
    private static Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    /**
     * 生成token
     * @param param
     * @return
     * @throws Exception
     */
    public static String createToken(Map<String,Object> param) throws Exception{
        return createJavaWebToken(param);
    }

    /**
     * 解析token
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String,Object> analysisToken(String token) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        if (CommonUtil.isEmpty(token)){
            map.put("status",StatusCode.UNAUTHORIZED.status());
            return map;
        }
        map = parserJavaWebToken(token);
        return map;
    }


    /**
     * 该方法使用HS256算法和Secret:bankgl生成signKey
     * @return
     * @throws Exception
     */
    private static Key getKeyInstance() throws Exception{
        //We will sign our JavaWebToken with our ApiKey secret
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("jinruixiang");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    /**
     * 使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
     * @param claims
     * @return
     * @throws Exception
     */
    private static String createJavaWebToken(Map<String, Object> claims) throws Exception {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(nowMillis + 1000*60*60*24*7))
                .setIssuedAt(now)
                .setNotBefore(now)
                .signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();
    }

    /**
     * 解析Token，同时也能验证Token，当验证失败返回null
     * @param jwt
     * @return
     * @throws Exception
     */
    private static Map<String, Object> parserJavaWebToken(String jwt) throws Exception {
        try {
            Map<String, Object> jwtClaims =
                    Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
            return jwtClaims;
        } catch (Exception e) {
            logger.error("json web token verify failed");
            throw e;
        }
    }

}
