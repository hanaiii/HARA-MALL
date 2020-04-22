package fun.hara.mall.common.util;

import org.springframework.security.core.context.SecurityContextHolder;
/**   
 * Oauth2工具类
 * @Author: hanaii 
 */
public class Oauth2Util {
    /**
     * 获取当前用户id
     * @return
     */
    public static Long getUserId(){
        String id = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return Long.valueOf(id);
    }
}
