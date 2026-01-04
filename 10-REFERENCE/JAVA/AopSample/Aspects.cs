/* Simple AOP sample using proxies.
 * Fabrice MARGUERIE
 * http://dotnetweblogs.com/fmarguerie/
 */
namespace Madgeek.Aop
{
  using System;
  using System.Reflection;
  using System.Runtime.Remoting.Messaging;
  using System.Runtime.Remoting.Proxies;

  /// <summary>
  /// Interceptor class based on RealProxy.
  /// </summary>
  public class AopRealProxy : RealProxy
  {
    Object  _Object;

    public AopRealProxy(Type type) : base(type)
    {
      _Object = System.Activator.CreateInstance(type);
    }

    /// <summary>
    /// Method invoked whenever there is a method call on the proxy'ed object. 
    /// </summary>
    /// <param name="msg"></param>
    /// <returns></returns>
    public override IMessage Invoke(IMessage msg)
    {
      IMethodCallMessage  call;
      object              res;

      call = (IMethodCallMessage) msg;

      // Loop on parameters
      foreach (ParameterInfo parameterInfo in call.MethodBase.GetParameters())
      {
        object  arg;

        arg = call.InArgs[parameterInfo.Position];

        // Apply each aspect
        foreach (Aspect aspect in parameterInfo.GetCustomAttributes(typeof(Aspect), true))
          aspect.Apply(arg);
      }

      res = _Object.GetType().InvokeMember(call.MethodName, BindingFlags.InvokeMethod, null, _Object, call.InArgs);
      return new ReturnMessage(res, null, 0, null, call);
    }
  }

  /// <summary>
  /// Base class for aspect attributes.
  /// </summary>
  public class Aspect : Attribute
  {
    /// <summary>
    /// Executed when the aspect is to be applied.
    /// </summary>
    /// <param name="o"></param>
    public virtual void Apply(object o)
    {
    }
  }
}