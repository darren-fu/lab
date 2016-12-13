package testMiaosha;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.RedisConnection;

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
public interface RedisNotifyListener {


    void handleMessage(RedisConnection connection, Message message, byte[] pattern);

}
