package testNetty.exam.exchange;

import java.util.Map;

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
 * @date 2016/12/1
 */
public interface Request extends Message{

    String getRequestHeader();

    void setRequestHeader(String header);

    Object getRequestBody();

    void setRequestBody(Object body);


    Map<String, byte[]> getRequestAttrsMap();

    void setRequestAttrsMap(Map<String, byte[]> requestAttrsMap);


}
