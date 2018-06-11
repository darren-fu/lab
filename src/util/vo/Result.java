package util.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 结果包装类
 * Created by erhu on 2017/1/23.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Result<T> implements Serializable {
    private List<T> data;
    private Integer count;

    public static <E> Result<E> buildResult(List<E> data, Integer count) {
        Result<E> result = new Result<E>();
        if (data == null) {
            // 保证返回列表不是null
            data = Collections.EMPTY_LIST;
        }
        result.setData(data);
        result.setCount(count);
        return (Result<E>) result;

    }


}
