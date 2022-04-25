package cn.flowboot.system.service.impl;

import cn.flowboot.common.croe.domain.user.User;
import cn.flowboot.common.croe.service.UserService;
import cn.flowboot.common.utils.CopyUtil;
import cn.flowboot.system.domain.entity.SysUser;
import cn.flowboot.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Maps;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/11/13
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final SysUserService sysUserService;

    @Override
    public Map<Long, User> getUserMapByAvailable() {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",true);
        List<SysUser> list = sysUserService.list(queryWrapper);
        if (list.size() == 0){
            return new HashMap<>();
        }
        List<User> users = CopyUtil.copyList(list, User.class);
        return users.stream().collect(Collectors.toMap(User::getUserId, Function.identity()));
    }

    /**
     * 通过id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public User getById(Long id) {
        SysUser sysUser = sysUserService.getById(id);
        if (sysUser == null){
            return null;
        }
        return CopyUtil.copy(sysUser,User.class);
    }

}
