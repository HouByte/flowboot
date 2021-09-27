package cn.flowboot.common.croe.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <h1>前端树状数据</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreeSelect {
    private Long id;
    private String label;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;
}
