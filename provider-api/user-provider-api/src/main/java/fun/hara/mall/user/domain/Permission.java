package fun.hara.mall.user.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**   
 * 权限实体
 * @Author: hanaii 
 */
@Getter@Setter
@Table(name="tb_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = -47219096389623123L;
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 父权限
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 权限名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 权限英文名称
     */
    @Column(name = "ename")
    private String enname;

    /**
     * 授权路径
     */
    @Column(name = "url")
    private String url;

    /**
     * 备注
     */
    @Column(name = "description")
    private String description;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

}