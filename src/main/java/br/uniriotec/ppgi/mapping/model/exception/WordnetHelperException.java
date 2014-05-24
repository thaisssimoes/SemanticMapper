package br.uniriotec.ppgi.mapping.model.exception;

public class WordnetHelperException extends Exception{
	private static final long serialVersionUID = -8391019112260185120L;

	public WordnetHelperException(String message) {
        super(message);
    }

    public WordnetHelperException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
