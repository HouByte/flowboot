package cn.flowboot.common.utils;

import cn.flowboot.common.exception.ParamsException;

/**
 * 校验类
 *
 * 乐字节：专注线上IT培训
 * 答疑老师微信：lezijie
 */
public class AssertUtil {


    /**
     * 判断条件是否满足
     *  如果条件满足，则抛出参数异常
     *
     *
     * 乐字节：专注线上IT培训
     * 答疑老师微信：lezijie
     * @param flag
     * @param msg
     * @return void
     */
    public  static void isTrue(Boolean flag, String msg){
        if(flag){
            throw  new ParamsException(msg);
        }
    }

}
