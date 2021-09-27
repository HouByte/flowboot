package cn.flowboot.common.croe.controller;


import cn.flowboot.common.croe.domain.AjaxResult;
import cn.flowboot.common.utils.CopyUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {


    @ModelAttribute
    public void preHandler(HttpServletRequest request){
        request.setAttribute("ctx", request.getContextPath());
    }



    public <T> AjaxResult page(List<T> list){
        PageInfo<T> pageInfo = new PageInfo<>(list);

        Map<String,Object> data = new HashMap<>();
        data.put("rows",pageInfo.getList());
        data.put("total",pageInfo.getTotal());

        return AjaxResult.success(data);
    }

    public <T> AjaxResult page(List<T> list,Class<?> clazz){
        return page(CopyUtil.copyList(list,clazz));
    }

    public <T> AjaxResult success(T data,Class<?> clazz){
        return AjaxResult.success(CopyUtil.copy(data,clazz));
    }

    public <T> AjaxResult success(T data){
        return AjaxResult.success(data);
    }

    public <T> AjaxResult success(){
        return AjaxResult.success();
    }
}
