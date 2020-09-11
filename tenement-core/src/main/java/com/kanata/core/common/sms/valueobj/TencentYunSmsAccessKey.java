package com.kanata.core.common.sms.valueobj;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chenyao
 * date 2020-09-03
 */
@Data
@Component
@ConfigurationProperties(prefix = "tencent-yun.access")
public class TencentYunSmsAccessKey {

    private String secretId;

    private String secretKey;

}
