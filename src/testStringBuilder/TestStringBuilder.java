package testStringBuilder;

/**
 * author: fuliang
 * date: 2017/7/28
 */
public class TestStringBuilder {

    public static final TestStringBuilder DEFAULT = new TestStringBuilder();

    private final ThreadLocal<StringBuilder> pool = ThreadLocal.withInitial(() -> new StringBuilder(512));

    /**
     * BEWARE: MUSN'T APPEND TO ITSELF!
     *
     * @return a pooled StringBuilder
     */
    public StringBuilder stringBuilder() {
        StringBuilder sb = pool.get();
        sb.setLength(0);
        return sb;
    }

    public static void main(String[] args) {

        StringBuilder stringBuilder = TestStringBuilder.DEFAULT.stringBuilder();
        stringBuilder.append("123").append("#").append("456");
        System.out.println(stringBuilder.toString());

        StringBuilder stringBuilder2 = TestStringBuilder.DEFAULT.stringBuilder();

        stringBuilder2.append("AA");
        System.out.println(stringBuilder2.toString());
        System.out.println(DEFAULT.hashCode());
        System.out.println(System.identityHashCode(DEFAULT));
        System.out.println(DEFAULT.toString());
        TestStringBuilder test = new TestStringBuilder();
        System.out.println(test.hashCode());
        System.out.println(System.identityHashCode(test));

        TestStringBuilder test2 = new TestStringBuilder();
        System.out.println(test2.hashCode());
        System.out.println(System.identityHashCode(test2));

    }

}
