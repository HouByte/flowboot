package cn.flowboot.system.service;

import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.system.domain.dto.RegisterDto;
import cn.flowboot.system.domain.dto.UserDto;
import cn.flowboot.system.domain.entity.SysUser;
import cn.flowboot.system.domain.vo.UserBaseVo;
import cn.flowboot.system.domain.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户服务
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/27
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    SysUser getOneByUsername(String username);

    /**
     * 根据用户名查询登入信息
     * @param username
     * @return
     */
    LoginUser getLoginUserByUsername(String username);

    /**
     * 管理列表
     * @param keyword
     * @return
     */
    List<UserVo> queryList(String keyword);

    /**
     * 更新或新增
     * @param update true 为更新
     */
    void saveOrUpdate(boolean update, UserDto userDto);

    /**
     * 查询单个信息
     * @param id
     * @return
     */
    UserDto queryOneById(Long id);

    /**
     * 更新状态
     * @param id
     * @param status
     */
    void updateStatus(Long id, Boolean status);

    /**
     * 注册
     * @param registerDto
     */
    void register(RegisterDto registerDto);
}
