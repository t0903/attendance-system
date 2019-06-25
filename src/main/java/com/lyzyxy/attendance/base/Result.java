package com.lyzyxy.attendance.base;

import java.io.Serializable;

/**
 * 前端返回对象
 * @author SJY
 *
 */
public class Result<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final int SUCCESS = 200;
    public static final int ERROR = 500;

    private String msg;
    private Integer code;
	private T data;
    
    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    public static Result success(){
    	return new Result(SUCCESS,"",null);
    }
    
    public static <T> Result success(T data){
    	return new Result(SUCCESS,"",data);
    }
    
    public static <T> Result success(String msg,T data){
    	return new Result(SUCCESS,msg,data);
    }

	public static <T> Result success(String msg){
		return new Result(SUCCESS,msg,null);
	}
    
    public static Result error(){
    	return new Result(ERROR,"",null);
    }
    
    public static <T> Result error(T data){
    	return new Result(ERROR,"",data);
    }
    
    public static <T> Result error(String msg,T data){
    	return new Result(ERROR,msg,data);
    }

	public static <T> Result error(String msg){
		return new Result(ERROR,msg,null);
	}

    public Result code(int code){
    	this.code = code;
    	return this;
	}

	public Result data(T data){
    	this.data = data;
    	return this;
	}

	public Result msg(String msg){
    	this.msg = msg;
    	return this;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
