package person;

import java.util.ArrayList;
import java.util.List;


public class Test {

	/*public static void swap(Person a, Person b){
		Person temp;
		temp = a;
		a = b;
		b = temp;
	}*/
	
	public static void addPerson(List<Person> list){
		Person b = new Person("B");
		list.add(b);
	}
	
	public static void main(String[] args) {
		Person a = new Person("A");
		List<Person> list = new ArrayList<Person>();
		list.add(a);
		System.out.println(list);
		System.out.println("after addPerson b");
		addPerson(list);
		System.out.println(list);

	}

}
