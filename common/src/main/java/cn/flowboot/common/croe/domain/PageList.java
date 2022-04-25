package cn.flowboot.common.croe.domain;

import com.github.pagehelper.PageInfo;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/12/29
 */
@Data
@Builder
public class PageList {
    private Long total;
    private List<?> rows;

    public static PageList page(List<?> list,Long total){
       return PageList.builder().rows(list).total(total).build();
    }

    public static PageList empty(){
        return PageList.builder().rows(Collections.emptyList()).total(0L).build();
    }
}
