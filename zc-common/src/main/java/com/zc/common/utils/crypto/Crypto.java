package com.zc.common.utils.crypto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zichen
 *
 * 加密信息传输对象
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Crypto {

    /**
     * 公钥
     */
    private String publickey;

    /**
     * 秘钥
     */
    private String privateKey;

    /**
     * 密文
     */
    private String ciphertext;

}
