package testBean;

import lombok.Getter;
import lombok.ToString;
import net.sf.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean 工具类
 * Created by darrenfu on 17-2-18.
 */
@SuppressWarnings("unused")
public class BeanTools {
    /**
     * bean copier 缓存
     */
    private static ConcurrentHashMap<BeanCopierNode, BeanCopier> beanCopierCacheMap = new ConcurrentHashMap<>(32);


    /**
     * 实例化一个bean，懒得new再换行
     *
     * @param <T>         bean type
     * @param targetClass target bean
     * @return bean instance
     */
    @SuppressWarnings("unused")
    public static <T> T instance(Class<T> targetClass) {
        try {
            return targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * copy properties to other bean，方便一点是一点
     * 使用cglib beanCopier 性能较spring 的beanUtils 高5倍以上
     *
     * @param <T>         bean type
     * @param source      source bean
     * @param targetClass target class
     * @return target bean instance
     */
    public static <T> T copyBean(Object source, Class<T> targetClass) {

        BeanCopier beanCopier = getBeanCopier(source.getClass(), targetClass);
        T target = instance(targetClass);
        // copy bean
        beanCopier.copy(source, target, null);
        return target;
    }


    /**
     * copy properties and generate list, 方便两点是两点
     *
     * @param <T>           the type parameter
     * @param <E>           the type parameter
     * @param sourceObjList the source obj list
     * @param targetClass   the target class
     * @return the list
     */
    public static <T, E> List<T> copyBeans(List<E> sourceObjList, Class<T> targetClass) {
        if (sourceObjList == null || sourceObjList.size() < 1) {
            return Collections.EMPTY_LIST;
        }

        List<T> targetObjList = new ArrayList<>(sourceObjList.size());
        // 生成copier 循环中复用
        BeanCopier beanCopier = getBeanCopier(sourceObjList.get(0).getClass(), targetClass);

        for (E source : sourceObjList) {
            T target = instance(targetClass);
            // copy bean
            beanCopier.copy(source, target, null);
            targetObjList.add(copyBean(source, targetClass));
        }
        return targetObjList;
    }


    /**
     * 从缓存中获取 beanCopier
     *
     * @param sourceClz the source clz
     * @param targetClz the target clz
     * @return the bean copier
     */
    public static BeanCopier getBeanCopier(Class sourceClz, Class targetClz) {
        BeanCopier beanCopier = beanCopierCacheMap.get(new BeanCopierNode(sourceClz, targetClz));

        if (beanCopier == null) {
            beanCopier = BeanCopier.create(sourceClz, targetClz, false);
            beanCopierCacheMap.putIfAbsent(new BeanCopierNode(sourceClz, targetClz), beanCopier);
        }
        return beanCopier;
    }


    /**
     * The type Bean copier node.
     */
    @ToString
    static class BeanCopierNode {

        @Getter
        private Class sourceClass;

        @Getter
        private Class targetClass;


        /**
         * Instantiates a new Bean copier node.
         *
         * @param sourceClass the source class
         * @param targetClass the target class
         */
        BeanCopierNode(Class sourceClass, Class targetClass) {
            this.sourceClass = sourceClass;
            this.targetClass = targetClass;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BeanCopierNode that = (BeanCopierNode) o;
            return Objects.equals(sourceClass, that.sourceClass) &&
                    Objects.equals(targetClass, that.targetClass);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sourceClass, targetClass);
        }
    }


}
