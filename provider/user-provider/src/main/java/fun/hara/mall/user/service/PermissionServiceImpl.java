package fun.hara.mall.user.service;

import fun.hara.mall.user.api.PermissionService;
import fun.hara.mall.user.dao.PermissionDAO;
import fun.hara.mall.user.domain.Permission;
import org.apache.dubbo.config.annotation.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionDAO permissionDAO;


    @Override
    public List<Permission> listByUserId(Long id) {
        return permissionDAO.listPermissionByUserId(id);
    }
}
