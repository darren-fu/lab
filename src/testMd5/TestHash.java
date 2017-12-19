package testMd5;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.hash.Hashing;

import java.util.Map;

/**
 * author: fuliang
 * date: 2017/12/19
 */
public class TestHash {
    public static void main(String[] args) {
        int bucket = 50;
        {
            Multimap map = ArrayListMultimap.create();
            for (int i = 0; i < 2000; i++) {
                map.put(Hashing.consistentHash(i, bucket), i);
            }

            map.keySet().forEach(v-> System.out.println(v + "-----" + map.get(v).size()));
            System.out.println(bucket + ":" + map);
        }
        bucket = 3;
        {
            Multimap map = ArrayListMultimap.create();
            for (int i = 0; i < 20; i++) {
                map.put(Hashing.consistentHash(i, bucket), i);
            }
            System.out.println(bucket + ":" + map);
        }
        bucket = 4;
        {
            Multimap map = ArrayListMultimap.create();
            for (int i = 0; i < 20; i++) {
                map.put(Hashing.consistentHash(i, bucket), i);
            }
            System.out.println(bucket + ":" + map);
        }
    }
}
