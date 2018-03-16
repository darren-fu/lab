package testReactor.flux;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Created by darrenfu on 17-11-15.
 */
@SuppressWarnings("Duplicates")
public class FluxTest {

    @Test
    public void testFlux1() {
        Duration duration = Duration.ofDays(30);
        System.out.println(duration.getSeconds());
        System.out.println(Duration.of(10, ChronoUnit.MINUTES).getSeconds());

        Flux.interval(Duration.of(1, ChronoUnit.MILLIS)).subscribe(System.out::println);

        Flux.just("aaa", "bbb").subscribe(System.out::println);
        Flux.fromArray(new Integer[]{-1, -2, -3}).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        Flux.range(1, 5).subscribe(System.out::println);

    }


    @Test
    public void testFlux2() {
        Flux.generate(v -> {
            v.next("test");
            v.complete();
        }).subscribe(System.out::println);
    }

    @Test
    public void testFlux3(){
        final Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink) -> {
            int value = random.nextInt(100);
            System.out.println("###");

            list.add(value);
            sink.next(value);
            if (list.size() == 10) {
                sink.complete();
            }
            return list;
        }).subscribe(System.out::println);
    }

    @Test
    public void testFlux4() {
        Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                sink.next(i);
            }
            sink.complete();
        }).subscribe(System.out::println);
    }



}
