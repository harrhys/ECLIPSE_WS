/* Simple AOP sample using proxies.
 * Fabrice MARGUERIE
 * http://dotnetweblogs.com/fmarguerie/
 */
using System;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace AopSample
{
  using Madgeek.Aop;

	/// <summary>
	/// Summary description for Form1.
	/// </summary>
	public class Form1 : System.Windows.Forms.Form
	{
    private System.Windows.Forms.Button btnWithoutValidation;
    private System.Windows.Forms.Button btnWithValidation;
    #region Components

		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;
    #endregion

    #region Constructor
		public Form1()
		{
			//
			// Required for Windows Form Designer support
			//
			InitializeComponent();
		}
    #endregion

    #region Dispose
		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if (components != null) 
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}
    #endregion

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
      this.btnWithoutValidation = new System.Windows.Forms.Button();
      this.btnWithValidation = new System.Windows.Forms.Button();
      this.SuspendLayout();
      // 
      // btnWithoutValidation
      // 
      this.btnWithoutValidation.Location = new System.Drawing.Point(8, 16);
      this.btnWithoutValidation.Name = "btnWithoutValidation";
      this.btnWithoutValidation.Size = new System.Drawing.Size(96, 23);
      this.btnWithoutValidation.TabIndex = 0;
      this.btnWithoutValidation.Text = "Without aspect";
      this.btnWithoutValidation.Click += new System.EventHandler(this.btnWithoutValidation_Click);
      // 
      // btnWithValidation
      // 
      this.btnWithValidation.Location = new System.Drawing.Point(8, 48);
      this.btnWithValidation.Name = "btnWithValidation";
      this.btnWithValidation.Size = new System.Drawing.Size(96, 23);
      this.btnWithValidation.TabIndex = 1;
      this.btnWithValidation.Text = "With validation";
      this.btnWithValidation.Click += new System.EventHandler(this.btnWithValidation_Click);
      // 
      // Form1
      // 
      this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
      this.ClientSize = new System.Drawing.Size(120, 86);
      this.Controls.AddRange(new System.Windows.Forms.Control[] {
                                                                  this.btnWithValidation,
                                                                  this.btnWithoutValidation});
      this.Name = "Form1";
      this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
      this.Text = "AOP";
      this.Load += new System.EventHandler(this.Form1_Load);
      this.ResumeLayout(false);

    }
		#endregion

		/// <summary>
		/// The main entry point for the application.
		/// </summary>
		[STAThread]
		static void Main() 
		{
			Application.Run(new Form1());
		}


    private void Test(ContactManager contactManager)
    {
      Contact contact;

      try
      {
        // Add a valid contact
        contact = new Contact();
        contact.Name = "Toto";
        contactManager.Add(contact);

        // Add an invalid contact
        contact = new Contact();
        contactManager.Add(contact);

        MessageBox.Show("Everything is fine.");
      }
      catch (Exception x)
      {
        MessageBox.Show("Something went wrong: "+x.Message);
      }
    }

    #region Events

    private void Form1_Load(object sender, System.EventArgs e)
    {
    }

    private void btnWithoutValidation_Click(object sender, System.EventArgs e)
    {
      ContactManager  contactManager;

      contactManager = new ContactManager();
      Test(contactManager);
    }

    private void btnWithValidation_Click(object sender, System.EventArgs e)
    {
      ContactManager            contactManager;
      Madgeek.Aop.AopRealProxy  proxy;

      proxy = new Madgeek.Aop.AopRealProxy(typeof(ContactManager));
      contactManager = (ContactManager) proxy.GetTransparentProxy();
      Test(contactManager);
    }

    #endregion
  }
}