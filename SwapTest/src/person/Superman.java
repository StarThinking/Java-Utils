package person;

public class Superman extends Person {

	public Superman(String name) {
		super(name);
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		Superman s = null;
		s.clone();
	}

}
