package person;

public class Person{

	private String name;
	
	public Person(String name){
		this.name = name;
	}

	public String toString(){
		return "Person: name = " + name;
	}
	
	protected String tryProtected(){
		return "tryProtected";
	}
	
	public void tryClone() throws CloneNotSupportedException{
		Person pp = (Person) clone();
	}
}
