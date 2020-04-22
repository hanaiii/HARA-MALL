package fun.hara.mall.common.exception;
/**   
 * 自定义的业务逻辑异常
 * @Author: hanaii 
 */
public class BusinessException extends RuntimeException{
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}
