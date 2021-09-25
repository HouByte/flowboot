package cn.flowboot.common.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/25
 */
public class RequestBodyUtils {

    public static Map<String,Object> getBody(HttpServletRequest request){
        // 直接从HttpServletRequest的Reader流中获取RequestBody
        try {
            BufferedReader reader = request.getReader();
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();
            while(line != null){
                builder.append(line);
                line = reader.readLine();
            }
            reader.close();

            String reqBody = builder.toString();
            JSONObject json = JSONObject.parseObject(reqBody);
            return JSONObject.parseObject(json.toJSONString(),Map.class);
        }catch (Exception e){
            return new HashMap<>();
        }

    }
}
