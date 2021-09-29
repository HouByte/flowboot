package cn.flowboot.system.domain.query;

import cn.flowboot.common.croe.domain.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/26
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfigQuery extends BaseQuery {
    private String configName;
    private String configKey;
    private String configValue;
}
