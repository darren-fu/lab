package testChain;

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
public class AProcesser implements Processer {
    @Override
    public boolean isTarget() {
        return false;
    }

    @Override
    public void process() {
        System.out.println("AProcesser");
    }
}
