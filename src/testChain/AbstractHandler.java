package testChain;

import java.util.ArrayList;
import java.util.List;

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
 * @date 2016/10/13
 */
public class AbstractHandler {

    List<Processer> processers = new ArrayList<>();

    void handler(Processer processer) {
        this.handler(0, processer);
    }

    void handler(int idx, Processer processer) {
    }
}
