package fun.hara.mall.order.domain;

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

@Getter@Setter
@Table(name = "tb_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 5585762371411834139L;
    @Id
    @Column(name="id", insertable = false)
    private Long id;

    @Column(name="order_time")
    private Date orderTime;

    @Column(name="amount")
    private BigDecimal amount;

}
