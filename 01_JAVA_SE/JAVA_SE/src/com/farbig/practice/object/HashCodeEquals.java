package com.farbig.practice.object;

/**
 * @author HARRHY
 *
 */
public class HashCodeEquals {

	public static void main(String[] args) {

		Employee emp1 = new Employee(1);
		
		Employee emp2 = new Employee(2);
		
		System.out.println(""+emp1.equals(emp2)+ emp1.hashCode()+ emp2.hashCode());
	}

}
class Employee
{
	int ID;
	
	String name;
	
	public Employee(int iD) {
		super();
		ID = iD;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (ID != other.ID)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
