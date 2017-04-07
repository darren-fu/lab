package testRxJava.observerable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.ResourceSubscriber;
import org.junit.Test;

//import org.reactivestreams.*;

/**
 * author: fuliang
 * date: 2017/1/12
 */
public class TestObservable {


    private static void s(String msg) {
        System.out.println(msg);
    }

    @Test
    public void testCreateObserverable() {

        Observable<String> objectObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext("create1"); //发射一个"create1"的String
                observableEmitter.onNext("create2"); //发射一个"create2"的String
               // observableEmitter.onComplete();//发射完成,这种方法需要手动调用onCompleted，才会回调Observer的onCompleted方法
            }
        });


        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable disposable) {
                s("onSubscribe...");
            }

            @Override
            public void onNext(Object o) {
                s("onNext:" + o);

            }

            @Override
            public void onError(Throwable throwable) {
                s("onError:" + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                s("onComplete...");
            }
        };




        objectObservable.subscribe(observer);
        objectObservable.retry();

//        objectObservable.subscribe(observer2);


    }

}
