package cn.flowboot.common.croe.service;

/**
 * <h1>对外拓展授权方式</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/11/17
 */
public interface EmpowerService {

    /**
     * 通过权限字符授权
     */
    Boolean empowerRole(Long uid,String roleKey);
}
