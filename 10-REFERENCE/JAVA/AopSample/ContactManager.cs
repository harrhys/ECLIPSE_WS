/* Simple AOP sample using proxies.
 * Fabrice MARGUERIE
 * http://dotnetweblogs.com/fmarguerie/
 */
namespace AopSample
{
  using System;

  public class Contact : IValidate
  {
    private string _Name;

    public string Name
    {
      get { return _Name; }
      set { _Name = value; }
    }

    /// <summary>
    /// Method validating that the Contact instance is well defined.
    /// </summary>
    void IValidate.Validate()
    {
      // Let's say for example that a contact is not valid if no name is provided.
      // I think this is a realistic validation rule.
      if ((Name == null) || (Name.Length < 1))
        throw new ValidationException("Name not provided.");
    }
  }

  public class ContactManager : MarshalByRefObject
  {
    /// <summary>
    /// Sample method. Note the [Valid] attribute! This is were it becomes interesting.
    /// </summary>
    /// <param name="contact"></param>
    public void Add([Valid] Contact contact)
    {
      // Typically the contact should be added to the contact list,
      // but the purpose of this sample is not to build a running ContactManager, right?
    }
  }
}