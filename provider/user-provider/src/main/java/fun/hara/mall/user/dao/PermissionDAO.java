package fun.hara.mall.user.dao;

import fun.hara.mall.user.domain.Permission;
import fun.hara.mall.user.domain.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PermissionDAO extends Mapper<Permission> {
    List<Permission> listPermissionByUserId(Long id);
}
