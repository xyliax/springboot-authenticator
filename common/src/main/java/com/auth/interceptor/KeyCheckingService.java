package com.auth.interceptor;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.auth.dao.MongoRepository;
import com.auth.defenum.Role;
import com.auth.model.User;
import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.ECPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

@Service
@RestController
public class KeyCheckingService {
    @Resource
    private MongoRepository mongoRepository;

    public ECPublicKey getPublicKey(String keyJson) throws JoseException {
        JSONObject jsonObject = JSONUtil.parseObj(keyJson);
        EllipticCurveJsonWebKey jsonWebKey = new EllipticCurveJsonWebKey(jsonObject);
        ECPublicKey publicKey = jsonWebKey.getECPublicKey();
        return publicKey;
    }

    public Role getRole(String remote, String passcode) {
        if (remote == null || passcode == null)
            return Role.UNKNOWN;

        return null;
    }

    public boolean verify(byte[] sign, byte[] string, ECPublicKey publicKey) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PublicKey dsaPublicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Signature signature = Signature.getInstance("SHA384withECDSA");
        signature.initVerify(dsaPublicKey);
        signature.update(string);
        System.out.println(signature);
        return signature.verify(sign);
    }


    @PostMapping(path = "/key")
    public boolean getKey(
            @RequestParam("user") String userId,
            @RequestBody Map<String, byte[]> body) throws Exception {
        User user = mongoRepository.readUserById(userId);
        ECPublicKey publicKey = getPublicKey(user.getPublicKy());
        byte[] sign = body.get("sign");
        byte[] string = body.get("string");
        return verify(sign, string, publicKey);
    }
}
