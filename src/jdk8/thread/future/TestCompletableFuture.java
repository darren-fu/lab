package jdk8.thread.future;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
 * @date 2016/11/1
 */
public class TestCompletableFuture {

    //Async  executor 默认使用ForkJoinPool.commonPool()

    //因此，你可以根据方法的参数的类型来加速你的记忆。
    // Runnable 类型的参数会忽略计算的结果，
    // ConsumerTest 是纯消费计算结果，
    // BiConsumer 会组合另外一个CompletionStage纯消费，
    // Function 会对计算结果做转换，
    // BiFunction 会组合另外一个CompletionStage的计算结果做转换

    public static class FutureThread implements Runnable {
        private CompletableFuture<String> future;

        public FutureThread(CompletableFuture<String> future) {
            this.future = future;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            future.complete("complete");
            System.out.println("Future完成");

        }
    }

    public static class TestObject {

        public int num = 0;


        public void before() {
            num++;
            System.out.println("before:" + num);
        }

        public String after() {
            num++;
            System.out.println("after:" + num);
            return "" + num;
        }

    }

    @Test
    public void testConcurrentAction() {
        Runnable runnable = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " ---> START");
                Thread.sleep(1_000L);
                System.out.println(Thread.currentThread().getName() + " =====> END");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        ExecutorService service = Executors.newFixedThreadPool(2);
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(runnable, service);
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(runnable, service);
        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("1".substring(0, 19));
        }, service);
        CompletableFuture<Void> future4 = CompletableFuture.runAsync(runnable, service);
        CompletableFuture.allOf(future1, future2, future3, future4).exceptionally((e) -> {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }).join();
        System.out.println("全部执行完成");

    }


    @Test
    public void testCompleteMultiAction() {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("run..." + "-" + Thread.currentThread().getName());

            return "DOWN";
        }).whenComplete((res, ex) -> {
            System.out.println("complete:" + res + "-" + Thread.currentThread().getName());
        });

        future.whenComplete((res, ex) -> {
            System.out.println("res2:" + res + "-" + Thread.currentThread().getName());

            System.out.println(ex.getMessage());
        });

        future.join();

    }


    @Test
    public void testCompleteWithObject() {
        TestObject testObject = new TestObject();
        testObject.before();
        CompletableFuture.runAsync(() -> {
            System.out.println("启动:" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成");
        }).thenApply((v) -> testObject.after()).join();
    }


    @Test
    public void testCompleteBlock() throws ExecutionException, InterruptedException {
        Executor threadPool = Runnable::run;

//        ExecutorService threadPool = Executors.newCachedThreadPool();

        CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("启动:" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成");
            throw new RuntimeException("XX");
//            return "100";
        });
        CompletableFuture<String> future1 = future.thenApply((v) -> {
            System.out.println("handle:" + Thread.currentThread().getName());
            return "tt";
        });
//                .exceptionally((e) -> {
//                    System.out.println("exceptionally:" + e);
//                    return "ttxx";
//                })
        CompletableFuture<String> future2 = future1.whenComplete((v, e) -> {
            System.out.println("whenComplete:" + v + ",e:" + e);
        });
        future2.join();
        System.out.println(future2.get());

        Thread.currentThread();


    }


    public class CurrentThreadExecutor implements Executor {
        public void execute(Runnable r) {
            r.run();
        }
    }

    private static class MainExecutor implements Executor {
        @Override
        public void execute(Runnable command) {
//            Thread.currentThread().e;
        }
    }

    @Test
    public void testComplete() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        long start = System.currentTimeMillis();
        future.thenApply((result) -> {
            System.out.println("result:" + result + ",cost:" + (System.currentTimeMillis() - start) / 1000);
            return result;
        });
        System.out.println("启动");
        FutureThread futureThread = new FutureThread(future);
        new Thread(futureThread).start();
        System.out.println("等待结果");
//        String result = future.get();
//        System.out.println("结果：" + result);
        Thread.sleep(10_000L);
        System.out.println("done");
    }


    @Test
    public void testSupply() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("future启动");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future完成");

            return "42";
        }).thenAccept(v -> System.out.println("获取结果:" + v));

        System.out.println("启动");
        System.out.println("等待结果");
        System.out.println("结果：" + future.get());
    }

    /**
     * thenApply 对结果重新处理，可产生新的结果
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testThenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("启动");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成");
            return "100";
        }).thenApply(Integer::parseInt);
        System.out.printf("结果：" + future.get());
    }

    /**
     * thenAccept 对completableFuture结果 进行额外处理，没有返回值
     * 类似 thenAcceptBoth
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testThenAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<User> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("启动");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成");
            User user = new User();
            user.age = 12;
            user.name = "init";
            return user;
        })
                .thenApply((v) -> {
                    System.out.println("apply--->" + v);

                    v.age = 30;
                    v.name = "changed";
                    return v;
                });
        /**
         *  thenAccept执行不稳定，可能会在get后执行
         */
        future.thenAccept((v) -> {
            System.out.println("accept--->" + v);
            v.age = 50;
            v.name = "acceptChanged";
        });

//        Thread.sleep(1200);
        System.out.println("结果：" + future.get());
    }

    /**
     * exceptionally 处理异常
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testFutureException() throws ExecutionException, InterruptedException {
        CompletableFuture<User> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("启动");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("完成");
            User user = new User();
            user.age = 12;
            user.name = "init";
            int n = Integer.parseInt(user.name);
            return user;
        }).thenApply((v) -> {
            System.out.println("apply--->" + v);

            v.age = 30;
            v.name = "changed";
            return v;
        }).exceptionally((ex) -> {
            System.out.println("异常:" + ex.getMessage());
            User user = new User();

            return user;
        });
        System.out.println("结果：" + future.get());
    }

    /**
     * handle 同时处理正确结果和异常
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testFutureOkAndException() throws ExecutionException, InterruptedException {
        CompletableFuture<User> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("启动");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("完成");
            User user = new User();
            user.age = 12;
            user.name = "init";
            return user;
        }).thenApply((v) -> {
            System.out.println("apply--->" + v);
            int n = Integer.parseInt(v.name);
            v.age = 30;
            v.name = "changed";
            return v;
        }).handle((ok, ex) -> {
            if (ok != null) {
                ok.age += 1;
                return ok;
            } else {
                System.out.println("异常:" + ex.getMessage());
                User user = new User();
                user.name = "exception";
                return user;
            }

        });

        System.out.println("结果：" + future.get());
    }

//    public static CompletableFuture<Double> doCompute(User user) {
//        return user.age + 10;
//    }

    /**
     * 组合
     * 这一组方法接受一个Function作为参数，这个Function的输入是当前的CompletableFuture的计算值，
     * 返回结果将是一个新的CompletableFuture，
     * 这个新的CompletableFuture会组合原来的CompletableFuture和函数返回的CompletableFuture
     * A +--> B +---> C
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testThenCompose() throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> ageFuture = CompletableFuture.completedFuture(100);
        ageFuture.thenApply(v -> 200).handle((v, ex) -> 300);
        Function<Integer, Integer> times2 = e -> e * 2;

//        Function<User,CompletionStage<Integer>> ageFetch =(e) -> {
//            return e.age;
//        };

        CompletableFuture<User> userFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("启动,userFuture");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成,userFuture");
            User user = new User();
            user.age = 12;
            user.name = "init";
            return user;
        });
        CompletableFuture<Integer> finalFuture = userFuture.thenCompose((v) -> {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    System.out.println("挂起final计算:" + v);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return v.age + 100;
            });
        });


        System.out.println("结果userFuture：" + userFuture.get());
        System.out.println("结果finalFuture：" + finalFuture.get());

    }


    /**
     * thenCompose
     * 连接两个  CompletableFuture 并可以计算处理产生新的结果 (类似thenAcceptBoth 纯消费)
     * CompletableFuture<User>  + CompletableFuture<Integer> -> CompletableFuture<String>
     * 两个CompletionStage是并行执行的，它们之间并没有先后依赖顺序，
     * other并不会等待先前的CompletableFuture执行完毕后再执行。
     * A +
     * |
     * +------> C
     * +------^
     * B +
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testThenCombine() throws ExecutionException, InterruptedException {

        CompletableFuture<User> userCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始计算, userFuture");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成,userFuture");
            User user = new User();
            user.age = 12;
            user.name = "init";
            return user;
        });
        CompletableFuture<Integer> moneyFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始计算, moneyFuture");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成,moneyFuture");
            return 99;
        });

        CompletableFuture<User> userFinalFuture = userCompletableFuture.thenCombine(moneyFuture, (user, money) -> {
            user.age = money * user.age;
            return user;
        });

        CompletableFuture<Integer> moneyFinalFuture = userCompletableFuture.thenCombineAsync(moneyFuture, (user, money) -> {

            try {
                System.out.println("计算moneyFinalFuture...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("计算moneyFinalFuture.完成");

            return money * user.age;
        });
        CompletableFuture<String> otherFinalFuture = userCompletableFuture.thenCombine(moneyFuture, (user, money) -> {
            return user.toString() + ",money:" + money;
        });

        System.out.println("userFinalFuture 结果：" + userFinalFuture.get());
        System.out.println("moneyFinalFuture 结果：" + moneyFinalFuture.get());
        System.out.println("otherFinalFuture 结果：" + otherFinalFuture.get());
    }


    /**
     * 等待两个Future都完成，并进行处理
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testAcceptBoth() throws ExecutionException, InterruptedException {
        CompletableFuture<User> userCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始计算, userFuture");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成,userFuture");
            User user = new User();
            user.age = 12;
            user.name = "init";
            return user;
        });
        CompletableFuture<Integer> moneyFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始计算, moneyFuture");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成,moneyFuture");
            return 99;
        });


        userCompletableFuture.thenAcceptBothAsync(moneyFuture, (user, money) -> {
            try {
                System.out.println("开始thenAcceptBothAsync, ");
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成thenAcceptBothAsync");
        });
        System.out.println("userFinalFuture 结果：" + userCompletableFuture.get());
        System.out.println("moneyFinalFuture 结果：" + moneyFuture.get());
        Thread.sleep(2000);

    }


    /**
     * 接受任意一个，先完成的被接受 ，可进行处理， Consumer函数接口，无返回值
     */
    @Test
    public void testAcceptEither() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> moneyFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始计算, moneyFuture");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成,moneyFuture");
            return 10;
        });

        CompletableFuture<Integer> moneyFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始计算, moneyFuture2");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成,moneyFuture2");
            return 99;
        });

        moneyFuture.acceptEither(moneyFuture2, (money) -> {
            System.out.println("money:" + money);
        });

        System.out.println("结果：" + moneyFuture.get());
        System.out.println("结果：" + moneyFuture2.get());

    }


    /**
     * 获取最先返回的结果，可以处理，Function 函数借口，有返回值
     * 等同于fast.applyToEither(predictable,Function.identity()).thenApply(fn)
     */
    @Test
    public void testApplyToEither() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> moneyFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始计算, moneyFuture");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成,moneyFuture");
            return 10;
        });

        CompletableFuture<Integer> moneyFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始计算, moneyFuture2");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成,moneyFuture2");
            return 99;
        });

        CompletableFuture<String> resultFuture = moneyFuture.applyToEitherAsync(moneyFuture2, (money) -> {
            return "money变成字符串了--->" + money;
        });

        System.out.println("结果：" + resultFuture.get());

        CompletableFuture<Integer> resultFuture2 = moneyFuture.applyToEitherAsync(moneyFuture2, Function.identity());

        System.out.println("结果2：" + resultFuture2.get());

    }


    /**
     * anyOf方法是当任意一个CompletableFuture执行完后就会执行计算，计算的结果相同。
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testAnyOf() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> moneyFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始计算, moneyFuture");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成,moneyFuture");
            return 10;
        });

        CompletableFuture<Integer> moneyFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始计算, moneyFuture2");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成,moneyFuture2");
            return 99;
        });

        CompletableFuture<Object> resultFuture = CompletableFuture.anyOf(moneyFuture, moneyFuture2);
        System.out.println("结果：" + resultFuture.get());
        Thread.sleep(3000);
    }

    /**
     * allOf方法是当所有的CompletableFuture都执行完后执行计算。
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testAllOf() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> moneyFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始计算, moneyFuture");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成,moneyFuture");
            return 10;
        });

        CompletableFuture<Integer> moneyFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始计算, moneyFuture2");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成,moneyFuture2");
            return 99;
        });

        CompletableFuture<Void> resultFuture = CompletableFuture.allOf(moneyFuture, moneyFuture2).whenComplete((v, ex) -> {
            System.out.println("全部执行完了！！！");
        });
        System.out.println("结果：" + resultFuture.isDone());

        Thread.sleep(3000);
    }


    /**
     * 存储多个future的结果为list
     * CompletableFuture<List<java.lang.Object>>
     *
     * @param futures
     * @return
     */
    public static CompletableFuture<List<java.lang.Object>> sequence(CompletableFuture<?>... futures) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures);
        return allDoneFuture.thenApply(v -> Arrays.asList(futures).stream().map((res) -> {
            return res.join();
        }).collect(Collectors.<Object>toList()));
    }


    /**
     * Java Future转CompletableFuture
     *
     * @param future
     * @param executor
     * @param <T>
     * @return
     */
    public static <T> CompletableFuture<T> toCompletable(Future<T> future, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    @Test
    public void testExtendMethod() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> moneyFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始计算, moneyFuture");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成,moneyFuture");
            return 10;
        });

        CompletableFuture<Integer> moneyFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始计算, moneyFuture2");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成,moneyFuture2");
            return 99;
        });


        CompletableFuture<List<Object>> resultFuture = TestCompletableFuture.sequence(moneyFuture, moneyFuture2);

        System.out.println("结果:" + resultFuture.get().size());
        List<Object> list = resultFuture.join();
        for (Object o : list) {
            System.out.println(o);
        }
    }


}
