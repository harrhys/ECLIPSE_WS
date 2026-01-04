/* Simple AOP sample using proxies.
 * Fabrice MARGUERIE
 * http://dotnetweblogs.com/fmarguerie/
 */
namespace AopSample
{
  using System;
  using Madgeek.Aop;

  public interface IValidate
  {
    void Validate();
  }

  public class ValidationException : ApplicationException
  {
    public ValidationException(string message) : base(message)
    {
    }
  }

  [AttributeUsage(AttributeTargets.Parameter)]
  public class ValidAttribute : Aspect
  {
    public override void Apply(object o)
    {
      if (!(o is IValidate))
        throw new InvalidOperationException("Object cannot be validated.");

      ((IValidate) o).Validate();
    }
  }
}