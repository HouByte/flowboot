package cn.flowboot.core.service;

import cn.flowboot.common.croe.domain.AjaxResult;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/25
 */
public interface AuthenticationService {

    /**
     * 封装认证发货数据
     * @param authenticationToken
     * @return
     */
    AjaxResult getResult(AbstractAuthenticationToken authenticationToken);
}
