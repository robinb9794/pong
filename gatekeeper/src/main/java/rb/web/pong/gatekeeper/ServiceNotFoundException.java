package rb.web.pong.gatekeeper;

public class ServiceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ServiceNotFoundException() {
		super();
	}
	
	public ServiceNotFoundException(String message) {
		super(message);
	}
}
