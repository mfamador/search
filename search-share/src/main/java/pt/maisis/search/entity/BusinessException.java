package pt.maisis.search.entity;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	public BusinessException(String msg, Exception e) {
		super(msg, e);
	}

        public BusinessException(String msg) {
		super(msg, null);
	}

	public BusinessException(Exception e) {
		super(e);
	}
}