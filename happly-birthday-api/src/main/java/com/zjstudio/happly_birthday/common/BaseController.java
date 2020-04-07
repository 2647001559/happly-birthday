package com.zjstudio.happly_birthday.common;

import com.zjstudio.happly_birthday.tools.common.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author 添柴灬少年
 * @version 1.0
 * @date 2020/4/7 13:56
 **/
public class BaseController {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取基础路径
     *
     * @return
     */
    protected String getBasePath() {
        HttpServletRequest request = getRequest();
        return request.getScheme() + "://"
                + request.getServerName()
                + ":" + request.getServerPort();
    }

    /**
     * 获取访问者IP
     *
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     *
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @return
     */
    protected String getAddr() throws Exception {
        HttpServletRequest request = getRequest();

        String ip = request.getHeader("X-Real-IP");
        if (!CommonUtil.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!CommonUtil.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    /**
     * 得到request对象
     */
    protected HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 得到response对象
     * @return
     */
    protected HttpServletResponse getResponse(){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }
}
