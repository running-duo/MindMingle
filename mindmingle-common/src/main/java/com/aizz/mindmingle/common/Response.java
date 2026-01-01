package com.aizz.mindmingle.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Data
public class Response<T> {

    /**
     * 响应码
     */
    public int code;

    /**
     * 描述
     */
    private String msg;

    /**
     * 响应结构
     */
    public T data;

    private Response(int code, T data){
        this.code = code;
        this.data = data;
    }

    private Response(int code, T data, String msg){
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Response(ResponseCode responseCode){
        this.code = responseCode.getCode();
        this.msg = responseCode.getDesc();
    }

    public Response(ResponseCode responseCode, T data){
        this.code = responseCode.getCode();
        this.msg = responseCode.getDesc();
        this.data = data;
    }

    @Override
    public String toString() {
        return "{code=" + code + ", msg=" + msg + ", data=" + data + "}";
    }

    public static <V> Response<V> success(){
        return new Response<>(ResponseCode.OK);
    }

    public static <V> Response<V> success(V data){
        return new Response<>(ResponseCode.OK, data);
    }

    public static <V> Response<V> success(String msg, V data){
        Response<V> response = new Response<>(ResponseCode.OK, data);
        response.msg = msg;
        return response;
    }

    public static <V> Response<V> error(){
        return new Response<>(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    public static <V> Response<V> error(ResponseCode responseCode){
        return new Response<>(responseCode);
    }

    public static <V> Response<V> error(String msg){
        Response<V> response = new Response<>(ResponseCode.INTERNAL_SERVER_ERROR);
        response.msg = msg;
        return response;
    }

    public static <V> Response<V> error(int code, String msg){
        return new Response<>(code, null, msg);
    }

    public static <V> Response<V> error(ResponseCode responseCode, String msg){
        Response<V> response = new Response<>(responseCode);
        response.msg = msg;
        return response;
    }

    public static <E> Response<Page<E>> page(List<E> list){
        Response<Page<E>> response = new Response<>(ResponseCode.OK);
        if(list == null){
            list = new ArrayList<>();
        }

        if(list instanceof com.github.pagehelper.Page<E> page){
            response.data = new Page<>(page, page.getTotal());
        }else {
            response.data = new Page<>(list, list.size());
        }
        return response;
    }

    public static class Page<E> {

        /** 总数 */
        private int total;

        /** 列表数据 */
        private Collection<E> list;

        public Page(){

        }

        public Page(Collection<E> list, int total){
            this.list = list;
            this.total = total;
        }

        public Page(Collection<E> list, long total){
            this.list = list;
            this.total = (int)total;
        }

        public int getTotal() {
            return total;
        }

        public void setTotalRows(int total) {
            this.total = total;
        }

        public Collection<E> getList() {
            return list;
        }

        public void setList(Collection<E> list) {
            this.list = list;
        }

        @Override
        public String toString() {
            return "{total=" + total + ", list=" + list + "}";
        }
    }
}
