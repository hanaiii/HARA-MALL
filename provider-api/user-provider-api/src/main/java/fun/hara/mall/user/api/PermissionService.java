package fun.hara.mall.user.api;

import fun.hara.mall.user.domain.Permission;

import java.util.List;

/**   
 * 权限服务
 * @Author: hanaii 
 */
public interface PermissionService {
    /**
     * 根据用户id查询权限列表
     * @param id
     * @return
     */
    public List<Permission> listByUserId(Long id);

}
