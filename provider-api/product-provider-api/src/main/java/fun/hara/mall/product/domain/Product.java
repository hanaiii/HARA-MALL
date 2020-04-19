package fun.hara.mall.product.domain;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * 商品实体对象
 * @Author: hanaii 
 */
@Table(name = "tb_product")
@Getter@Setter
@ApiModel(value = "Product", description = "商品实体对象")
public class Product implements Serializable {

    private static final long serialVersionUID = 577082953940048317L;

    public static final int SECKILL = 2;
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 库存
     */
    @Column(name = "stock")
    private Long stock;

    /**
     * 状态(0：下架，1：普通，2：秒杀)
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 价格
     */
    @Column(name= "price")
    private BigDecimal price;

    /**
     * 秒杀开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 秒杀结束时间
     */
    @Column(name = "end_time")
    private Date endTime;
}
