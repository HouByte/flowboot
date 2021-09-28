package cn.flowboot.common.croe.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class SelectOption {
    private Long value;
    private String label;
}
