package com.huijiewei.agile.core.application.port.inbound;

import com.huijiewei.agile.core.consts.AccountType;
import com.huijiewei.agile.core.domain.AbstractIdentityEntity;
import com.huijiewei.agile.core.domain.AbstractIdentityLogEntity;
import com.huijiewei.agile.spring.captcha.application.request.CaptchaRequest;

import java.util.Optional;

/**
 * @author huijiewei
 */

public interface AccountUseCase<T extends AbstractIdentityEntity> {
    Optional<T> getByAccount(String account, AccountType accountType);

    Boolean verifyCaptcha(CaptchaRequest captcha, String userAgent, String remoteAttr);

    Integer getRetryTimes(String key);

    void setRetryTimes(String key, Integer times);

    <E extends AbstractIdentityLogEntity> E createLog(Integer identityId);

    <E extends AbstractIdentityLogEntity> void saveLog(E log);
}
