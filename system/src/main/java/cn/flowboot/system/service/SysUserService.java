package cn.flowboot.system.service;

import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.system.domain.dto.UserDto;
import cn.flowboot.system.domain.entity.SysUser;
import cn.flowboot.system.domain.vo.UserBaseVo;
import cn.flowboot.system.domain.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getOneByUsername(String username);

    LoginUser getLoginUserByUsername(String username);

    List<UserVo> queryList(String keyword);

    /**
     *
     * @param update true 为更新
     */
    void saveOrUpdate(boolean update, UserDto userDto);

    UserDto queryOneById(Long id);

    void updateStatus(Long id, Boolean status);
}
