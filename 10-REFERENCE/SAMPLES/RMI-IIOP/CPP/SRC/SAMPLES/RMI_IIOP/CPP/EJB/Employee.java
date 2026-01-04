package samples.rmi_iiop.cpp.ejb;


public class Employee implements java.io.Serializable
{
    private int salary;
    private String employee;

    public void setSalary(int salary_param)
    {
	salary = salary_param;
    }

    public int getSalary()
    {
	return salary;
    }
    public void setEmployee(String employee_param)
    {
	employee = employee_param;
    }

    public String getEmployee()
    {
	return employee;
    }

}
