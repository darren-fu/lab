package util.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 响应VO基类
 * Created by erhu on 2017/1/23.
 *
 * @param <T> the type parameter
 */
@SuppressWarnings("unused")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T> implements Serializable {
    @Getter
    private String code;

    @Getter
    private String msg;

    @Getter
    private T info;

    public BaseResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    /**
     * 快速创建成功的response， info = null
     *
     * @param <T> info type
     * @return success response, code=0,msg=OK
     */
    public static <T> BaseResponse<T> buildSuccessResponse() {
        return new BaseResponse<>("OK", "SUCCESS");
    }

    /**
     * 快速创建成功的response， info = T info
     *
     * @param <T> info type
     * @return success response, code=0,msg=OK
     */
    public static <T> BaseResponse<T> buildSuccessResponseWithInfo(T info) {
        return new BaseResponse("OK", "SUCCESS").setInfo(info);
    }

    /**
     * 快速构建成功的result reponse，储存列表数据 info = Result<T>
     *
     * @param <T>        data type
     * @param data       list<E>
     * @param totalCount 总数 meta.count
     * @return BaseResponse<Result                               <                               T>>
     */
    public static <T> BaseResponse<Result<T>> buildSuccessResponseWithResult(List<T> data, int totalCount) {
        return new BaseResponse<Result<T>>("OK", "SUCCESS")
                .setInfo(Result.buildResult(data, totalCount));
    }


    /**
     * 判断response是否是success的
     *
     * @return code ==0  返回true，否则返回false
     */
    @JsonIgnore
    public boolean isSuccessResponse() {
        return "OK".equals(this.code);
    }


    /**
     * Sets code.
     *
     * @param code the code
     * @return the code
     */
    public BaseResponse setCode(String code) {
        this.code = code;
        return this;
    }


    /**
     * Sets msg.
     *
     * @param msg the msg
     * @return the msg
     */
    public BaseResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }


    /**
     * Sets info.
     *
     * @param info the info
     * @return the info
     */
    public BaseResponse setInfo(T info) {
        this.info = info;
        return this;
    }

    /**
     * 定义返回体为空的success response。
     * 对此response的set操作会抛出UnsupportedOperationException异常！
     */
    private static class SuccessEmptyResponse extends BaseResponse {
        private static final long serialVersionUID = -7722537724801482736L;

        /**
         * Instantiates a new Success empty response.
         *
         * @param code the code
         * @param msg  the msg
         */
        SuccessEmptyResponse(String code, String msg) {
            super.code = code;
            super.msg = msg;
        }

        /**
         * 判断response是否是success的
         *
         * @return always be true
         */
        @JsonIgnore
        public boolean isSuccessResponse() {
            return true;
        }

        /**
         * 不支持此操作
         *
         * @param code the info
         * @return BaseResponse
         */
        @Override
        public BaseResponse setCode(String code) {
            throw new UnsupportedOperationException();
        }

        /**
         * 不支持此操作
         *
         * @param msg the info
         * @return the info
         */
        @Override
        public BaseResponse setMsg(String msg) {
            throw new UnsupportedOperationException();

        }

        /**
         * 不支持此操作
         *
         * @param info the info
         * @return the info
         */

        @Override
        public BaseResponse setInfo(Object info) {
            throw new UnsupportedOperationException();
        }


    }

}
