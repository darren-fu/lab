package testMiaosha.pojo;

import lombok.Data;

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
 * @date 2016/12/6
 */
@Data
public class CheckResult {
    private boolean success;
    private String errorMsg;
    private String errCode;

}
