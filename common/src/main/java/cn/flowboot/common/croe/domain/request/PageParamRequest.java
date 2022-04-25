package cn.flowboot.common.croe.domain.request;

import cn.flowboot.common.constant.Constants;

import lombok.Data;

/**
 * <h1>分页请求参数</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/27
 */
@Data
public class PageParamRequest {

   // @ApiModelProperty(value = "页码", example= Constants.DEFAULT_PAGE + "")
    private int page = Constants.DEFAULT_PAGE;

   // @ApiModelProperty(value = "每页数量", example = Constants.DEFAULT_LIMIT + "")
    private int limit = Constants.DEFAULT_LIMIT;

}
