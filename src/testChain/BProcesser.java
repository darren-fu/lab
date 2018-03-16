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
public class BProcesser implements Processer {
    @Override
    public boolean isTarget() {
        return true;
    }

    @Override
    public void process() {
        System.out.println("BProcesser");
    }
}
