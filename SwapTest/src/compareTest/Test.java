package compareTest;

import person.Person;
import person.Superman;

public class Test {

	public static void main(String[] args) {
		Person p = new Person("a");
		if(p instanceof Cloneable)
			System.out.println("Cloneable");
		//Person p1 = (Person) p.clone();
		Superman s = null;
	
		

	}

}
