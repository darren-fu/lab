package testChain;

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
