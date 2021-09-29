package cn.flowboot.common.croe.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/27
 */
@Data
public class BaseDelete <T>{

    @NotNull(message = "id不能为空")
    private List<T> ids;
}
