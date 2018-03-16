package com.company.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company:
 * <p/>
 *
 * @author darrenfu
 * @version 1.0.0
 * @date 2016/5/23
 */
public class LogByslf4j {
    private Logger log = LoggerFactory.getLogger(LogByslf4j.class);

    private void doLog() {
        log.debug("123213 {} {}", 2, 3);
    }

    public static void main(String[] args) {
        LogByslf4j logTest = new LogByslf4j();
        logTest.doLog();
    }
}
