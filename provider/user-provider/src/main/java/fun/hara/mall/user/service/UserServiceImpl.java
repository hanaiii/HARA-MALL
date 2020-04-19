package fun.hara.mall.user.service;

import fun.hara.mall.user.api.UserService;
import fun.hara.mall.user.dao.UserDAO;
import fun.hara.mall.user.domain.User;
import org.apache.dubbo.config.annotation.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDAO userDAO;
    @Override
    public User getByUsername(String username) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        return userDAO.selectOneByExample(example);
    }
}
