package cn.flowboot.core.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/08/06
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (!StrUtil.hasEmpty(value)){
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null){
            return values;
        }
        filter(values);

        return values;
    }

    private void filter(String[] values) {
        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            if (!StrUtil.hasEmpty(value)){
                value = HtmlUtil.filter(value);
            }
            values[i] = value;
        }
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameters = super.getParameterMap();
        Map<String, String[]> map = new LinkedHashMap<>();
        if (parameters == null){
            return parameters;
        }
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            filter(values);
            map.put(key,values);
        }
        return map;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (!StrUtil.hasEmpty(value)){
            value = HtmlUtil.filter(value);
        }

        return value;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        InputStream in = super.getInputStream();
        StringBuffer body = new StringBuffer();
        InputStreamReader reader = new InputStreamReader(in, Charset.forName("utf-8"));
        BufferedReader buffer = new BufferedReader(reader);
        String line =null;
        while ((line = buffer.readLine()) != null ){
            body.append(line);
        }
        buffer.close();
        reader.close();
        in.close();
        if (StringUtils.isBlank(body)){
            body.append("{}");
        }
        Map<String,Object> map = JSONUtil.parseObj(body.toString());
        Map<String,Object> result = new LinkedHashMap<>();
        for (String key : map.keySet()) {
            Object val = map.get(key);
            if (val instanceof String){
                if (!StrUtil.hasEmpty(val.toString())){
                    result.put(key,HtmlUtil.filter(val.toString()));
                }
            } else {
                result.put(key,val);
            }
        }
        String json = JSONUtil.toJsonStr(result);
        ByteArrayInputStream bain = new ByteArrayInputStream(json.getBytes());

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bain.read();
            }
        };
    }
}
