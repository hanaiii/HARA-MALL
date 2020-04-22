## 主要项目结构

- dependencies：全局依赖管理
- common

  - common-entity
  - common-util
- provider：基于dubbo，作为生产者

  - product-provider-service
  - order-provider-service（基于sentinel的熔断与限流降级）

  - user-provider-service
- gateway：基于 Spring Cloud Gateway 的网关

- provider-api：接口与实体类

  - product-provider-api
  - order-provider-api
  - user-provider-api
- business：作为Dubbo中的consumer，同时向外提供REST服务

  - seckill-business：秒杀服务。定时加载秒杀商品，及用户订单消息的生产者
  - order-business：订单服务。用户订单消息的消费者。完成抢单与超时订单回滚功能
  - pay-business：支付服务。模拟支付秒杀订单功能。
