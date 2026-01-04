package samples.rmi_iiop.cpp.ejb;

public class InterfaceTestClass implements InterfaceTest
{
   public void setData(long data_)
   {

     data = data_;
   }
   public long getData()
   {
	return data;
   }
   private long data;
}
