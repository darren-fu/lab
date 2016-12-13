package jdk.nio.mutithread.handler;

import jdk.nio.mutithread.event.EventAdapter;
import jdk.nio.mutithread.pojo.Request;
import jdk.nio.mutithread.pojo.Response;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company: 江苏千米网络科技有限公司
 * <p/>
 *
 * @author 付亮(OF2101)
 * @version 1.0.0
 * @date 2016/11/7
 */
public class TimeHandler extends EventAdapter {
    public void onWrite(Request request, Response response) throws Exception {
        String command = new String(request.getDataInput());
        String time = null;
        Date date = new Date();

        if (command.equals("GB")) {
            DateFormat cnDate = DateFormat.getDateTimeInstance(DateFormat.FULL,
                    DateFormat.FULL, Locale.CHINA);
            time = cnDate.format(date);
        } else {
            DateFormat enDate = DateFormat.getDateTimeInstance(DateFormat.FULL,
                    DateFormat.FULL, Locale.US);
            time = enDate.format(date);
        }

        response.send(time.getBytes());
    }
}
