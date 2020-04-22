package fun.hara.mall.oauth2.service;
import com.google.common.collect.Lists;
import fun.hara.mall.user.api.PermissionService;
import fun.hara.mall.user.api.UserService;
import fun.hara.mall.user.domain.Permission;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
/**   
 * UserDetailsService 实现
 * @Author: hanaii 
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Reference
    private UserService userService;
    @Reference
    private PermissionService permissionService;

    public static void main(String[] args) {
        String encode = new BCryptPasswordEncoder().encode("123456");
        System.out.println(encode);
    }
    /**   
     * 根据用户名加载用户信息
     * @Author: hanaii 
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        fun.hara.mall.user.domain.User user = userService.getByUsername(s);
        if (user == null) {
            return null;
        }
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        List<Permission> permissions = permissionService.listByUserId(user.getId());
        permissions.forEach(tbPermission -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(tbPermission.getEnname());
            grantedAuthorities.add(grantedAuthority);
        });
        // 由框架完成认证工作
        return new User(user.getId().toString(), user.getPassword(), grantedAuthorities);
    }
}
