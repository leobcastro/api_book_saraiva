package br.com.itcraft.book.exception;

public class BookServiceException extends Exception{

	private static final long serialVersionUID = 7352743054928198247L;
	
	public BookServiceException(){
		super();
	}
	
	public BookServiceException(String message){
		super(message);
	}
	
	public BookServiceException(String message,Throwable cause){
		super(message, cause);
	}
	
	public BookServiceException(Throwable cause){
		super(cause);
	}

	
}
