package com.tibbers.exception;

public  class TibbersException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public TibbersException(String msg) {
		this(msg,null);
	}
	
	public TibbersException(String msg,Throwable throwable) {
		super(msg,throwable);
	}
	
	public TibbersException(String msg,Object target,Throwable throwable) {
		super(msg,throwable);
	}
	
	

}
