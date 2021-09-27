package cn.flowboot.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/27
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MenuTree {
    private Long id;
    private String label;
    /**
     * 父菜单ID
     */
    @JsonIgnore
    private Long pid;
    @TableField(exist = false)
    private List<MenuTree> children;
}
