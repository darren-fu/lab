package jdk.base;

/**
 * author: fuliang
 * date: 2017/12/20
 */
public class ExtendTest {


    public static class Base {

        public void who() {

            System.out.println("i am base");
            System.out.println(this.getClass());
        }

        public void hello() {
            System.out.println(this.getClass());

            System.out.println("hello base");
            this.who();
        }
    }

    public static class BaseExtend extends Base {

        @Override
        public void who() {
            System.out.println("i am BaseExtend");
        }

        @Override
        public void hello() {
            super.hello();
        }
    }

    public static void main(String[] args) {
        Base baseExtend = new BaseExtend();
        baseExtend.hello();
    }


}
