package testMiaosha.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company:
 * <p/>
 *
 * @author darren-fu
 * @version 1.0.0
 * @contact 13914793391
 * @date 2016/12/5
 */
@Data
@ToString
public class StockResult implements Serializable {


    private boolean success = false;

    private String stockId;

    private String userId;

//    private int number = 1;

    private String status;

    // 获取锁定的时间
    private Long lockTime;

    // 有效期还剩多少秒
    private Long liveTime;

}
