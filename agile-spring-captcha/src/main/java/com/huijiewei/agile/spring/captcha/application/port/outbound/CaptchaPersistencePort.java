package com.huijiewei.agile.spring.captcha.application.port.outbound;

import com.huijiewei.agile.spring.captcha.domain.CaptchaEntity;

import java.util.Optional;

/**
 * @author huijiewei
 */

public interface CaptchaPersistencePort {
    Optional<CaptchaEntity> getByCode(String code, String uuid, String userAgent, String remoteAddr);

    void deleteById(Integer id);

    Integer save(CaptchaEntity captchaEntity);
}
