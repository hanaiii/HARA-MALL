package fun.hara.mall.user.api;

import fun.hara.mall.user.domain.User;

/**   
 * 用户服务
 * @Author: hanaii 
 */
public interface UserService {
    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    public User getByUsername(String username);
}
