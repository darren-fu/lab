package testKryo;

import util.KryoUtil;

import java.io.*;

/**
 * Description:
 * User: darrenfu
 * Date: 2018-05-24
 * Time: 11:17
 */
public class TestKryo {


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User user = new User();
        user.setAge(12);
        user.setName("AAA");

        byte[] outputByJdk = outputByJdk(user);
        Object o = inputByJdk(outputByJdk);
        System.out.println(o);
        System.out.println("-------------------------------------");
        byte[] bytes = KryoUtil.writeObjectToByteArray(user);

        System.out.println(bytes.length);
        Object us = KryoUtil.readFromByteArray(bytes);
        System.out.println(us);

    }


    public static byte[] outputByJdk(Object val) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(val);
        oos.flush();
        byte[] bytes = bos.toByteArray();
        oos.close();
        return bytes;
    }

    public static <T> T inputByJdk(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(in);
        Object o = ois.readObject();
        ois.close();
        return (T) o;
    }


    public static class User implements Serializable {


        public User() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        private String name;
        private Integer age;
    }
}
