package testMiaosha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

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
 * @date 2016/11/30
 */
public class TestMiaoSha {

    private static Logger logger = LoggerFactory.getLogger(TestMiaoSha.class);
    public static String PRODUCT_ID = "A001";
    public static String STOCK_CAPACITY_KEY = "STOCK_CAPACITY";
    public static String STOCK_LOCK_KEY = "STOCK_LOCK";

    public static String COLON = ":";


    public static String PRODUCT_STOCK_CAPACITY_KEY = PRODUCT_ID + COLON + STOCK_CAPACITY_KEY;
    public static String PRODUCT_STOCK_LOCK_KEY = PRODUCT_ID + COLON + STOCK_LOCK_KEY;

    public static CountDownLatch finishLatch;


    public static void init(int stockCapacaty, int buyerNumber) {
        if (stockCapacaty < 1) {
            throw new IllegalArgumentException("不给东西怎么抢？？");
        }
        finishLatch = new CountDownLatch(buyerNumber);
        JedisUtil.set(PRODUCT_STOCK_CAPACITY_KEY, String.valueOf(stockCapacaty));
    }


    static class Buyer implements Runnable {
        String name;
        String key;

        Buyer(String name) {
            this.name = name;
            this.key = PRODUCT_ID + COLON + name;
        }

        private String getName() {
            return this.name;
        }


        private boolean isAccessOverflow(int maxAccessCountInOneSecond) {

            Long accessCount = JedisUtil.incr(key);
            if (accessCount == 1) {
                JedisUtil.pexpire(key, 1000L);
            }
            // 一秒内超过访问次数
            if (accessCount > maxAccessCountInOneSecond) {
                return true;
            }
            return false;
        }


        @Override
        public void run() {
            Integer capacity;
            Random random = new Random();
            try {
                for (; ; ) {
                    System.out.println(getName() + "开始去抢！");

//                    if (isAccessOverflow(2)) {
//                        System.out.println("################" + getName() + "超过次数限制！惩罚一定时间内不能抢了！");
//                        Thread.sleep(3000);
//                        continue;
//                    }

                    //还有库存
                    if (JedisUtil.get(PRODUCT_STOCK_CAPACITY_KEY) != null && (capacity = Integer.parseInt(JedisUtil.get(PRODUCT_STOCK_CAPACITY_KEY))) != null && capacity > 0) {
                        String key = PRODUCT_STOCK_LOCK_KEY + COLON + capacity;
                        if (JedisUtil.setNx(key, "抢购人:" + getName() + ",库存序号:" + capacity)) {
                            // 间隔一段时间 释放一个库存， 避免过快抢完
                            Thread.sleep(100);

                            Long newCapacity = JedisUtil.decr(PRODUCT_STOCK_CAPACITY_KEY);
                            System.out.println("OK, " + getName() + "抢到一个, 剩余库存:" + newCapacity);
                        } else {
                            System.out.println(getName() + " 没有抢到，休息一下再抢");
                        }

//                        Thread.sleep(random.nextInt(3000));
                        Thread.sleep(300);

                    } else {
                        System.out.println(getName() + " -_-! 没库存了，不抢了,当前库存:" + JedisUtil.get(PRODUCT_STOCK_CAPACITY_KEY));
                        break;
                    }
                }
                finishLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        int buyerNum = 1000;
        TestMiaoSha.init(99, buyerNum);

        Thread[] buyers = new Thread[buyerNum];

        for (int i = 0; i < buyers.length; i++) {
            buyers[i] = new Thread(new Buyer("抢购者-" + (i + 1)));
        }
        Long start = System.currentTimeMillis();
        for (Thread buyer : buyers) {
            buyer.start();
        }


        TestMiaoSha.finishLatch.await();
        Long end = System.currentTimeMillis();
        System.out.println("############################################################################");
        Set<String> buyersSet = JedisUtil.keys(PRODUCT_STOCK_LOCK_KEY + "*");
        System.out.println("耗时:" + (end - start) + "ms, 被抢购的商品数量：" + buyersSet.size());

        for (String buyerKey : buyersSet) {
            System.out.println(JedisUtil.get(buyerKey));
        }

    }
}
