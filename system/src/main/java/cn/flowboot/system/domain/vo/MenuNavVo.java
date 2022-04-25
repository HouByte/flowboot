package cn.flowboot.system.domain.vo;

import cn.flowboot.common.croe.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表
 * @TableName sys_menu
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MenuNavVo implements Serializable {

    private Long id;
    /**
     * 菜单名称
     */
    private String title;

    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 是否为外链（1是 0否）
     */
    private Boolean isFrame;

    /**
     * 是否缓存（1缓存 0不缓存）
     */
    private Boolean isCache;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     * 菜单状态（1显示 0隐藏）
     */
    private Boolean visible;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    private List<MenuNavVo> children = new ArrayList<>();



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
