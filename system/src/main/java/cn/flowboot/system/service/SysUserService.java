package cn.flowboot.system.service;

import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.system.domain.entity.SysUser;
import cn.flowboot.system.domain.vo.UserBaseVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getOneByUsername(String username);

    LoginUser getLoginUserByUsername(String username);

}
