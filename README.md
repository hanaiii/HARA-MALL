## 项目结构

- dependencies：全局依赖管理
- common

  - common-dto
- provider：基于dubbo，作为生产者

  - product-provider-service
  - order-provider-service（基于sentinel的熔断与限流降级）
- provider-api：接口与实体类

  - product-provider-api

  - order-provider-api
- business

  - seckill-business：作为consumer，向外提供REST服务

- gateway：基于 Spring Cloud Gateway 的网关