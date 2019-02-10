package jvm;

import java.util.HashSet;
import java.util.UUID;

public class Static implements Leaker{

	private static HashSet<Person> set = new HashSet<Person>();

	public void leak() {
		Person p = new Person(UUID.randomUUID().toString());
		set.add(p);
		// change hashcode
		p.id = "xxx";
		// false
		set.remove(p);
	}

	private class Person {
		private String id;

		public Person(String id) {
			this.id = id;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 31 * result + id.hashCode();
			return result;

		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Person) {
				Person p = (Person) o;
				return p.id != null && p.id.equals(this.id);
			}
			return false;
		}
	}
}
