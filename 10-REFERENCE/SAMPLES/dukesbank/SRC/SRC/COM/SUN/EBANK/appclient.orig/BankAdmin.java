/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package com.sun.ebank.appclient;

import java.util.Locale;
import java.util.ResourceBundle;
import java.text.NumberFormat;
import java.text.DateFormat;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class BankAdmin extends JFrame {
//Protected instance variables
  protected static BankAdmin frame;
  protected static EventHandle ehandle;
  protected String desc;
  protected JButton OK, cancel;
  protected JPanel p1, p2;
  protected JLabel fnamelab, lnamelab, milab, streetlab, 
      citylab, statelab, ziplab, phonelab, emaillab, 
       	    spacerlab1, spacerlab2;
  protected JLabel messlab, messlab2, messlab3, messlab4, 
      messlab5, messlab6, descriplab, typelab, balab, 
      creditlab, begbalab, customerlab, timelab;
  protected JMenuBar mb;
  protected JMenu custmenu, actmenu;
  protected JMenuItem createcust, viewcust, updatecust, 
      createact, viewact, addcust, remact, srchcust;
  protected JRadioButton checkingact, savingsact, creditact, mnymktact;
  protected ButtonGroup group;
  protected JPanel radioPanel;
  //Editable variables
  protected JTextField fname, lname, mi, street, city, state, account, 
      customer, zip, phone, e, descrip, type, bal, credit, begbal, 
      cust, time;
  //Internationalization
  private static Locale currentLocale=null;
  protected static ResourceBundle messages=null;

//Constructor
  public BankAdmin(Locale currentLocale) {
  //Internationalization setup
    messages = ResourceBundle.getBundle("AdminMessages", currentLocale);
  //Create initial UI (Panel 1)
    getContentPane().setLayout(new GridLayout(1,2));
    p1 = new JPanel();
    p1.setLayout(new GridLayout(20,1));
    p2 = new JPanel();
    p2.setLayout(new GridLayout(0, 2));
    p1.setBackground(Color.white);
    p2.setBackground(Color.gray);
    getContentPane().add(p1);
    getContentPane().add(p2);
  //Build menu bar
    mb = new JMenuBar();
    setJMenuBar(mb);
  //Build Customer menu
    custmenu = new JMenu(messages.getString("CustAdmin"), true);
    mb.add(custmenu);
    createcust = new JMenuItem(messages.getString("CreateCust"));
    viewcust = new JMenuItem(messages.getString("ViewCust"));
    updatecust = new JMenuItem(messages.getString("UpdateCust"));
    srchcust = new JMenuItem(messages.getString("SearchCust"));
    custmenu.add(createcust);
    custmenu.add(viewcust);
    custmenu.add(updatecust);
    custmenu.add(srchcust);
  //Build Account Menu
    actmenu = new JMenu(messages.getString("ActAdmin"), true);
    mb.add(actmenu);
    createact = new JMenuItem(messages.getString("CreateAct"));
    addcust = new JMenuItem(messages.getString("AddCust"));
    viewact = new JMenuItem(messages.getString("ViewAct"));
    remact = new JMenuItem(messages.getString("RemAct"));
    actmenu.add(createact);
    actmenu.add(addcust);
    actmenu.add(viewact);
    actmenu.add(remact);
  //Create Panel 2 OK and Cancel buttons
  //So EventHandle constructor can add as action listeners
    OK = new JButton(messages.getString("OKButton"));
    cancel = new JButton(messages.getString("CancelButton"));
  //Create message labels
    messlab = new JLabel();
    messlab2 = new JLabel();
    messlab3 = new JLabel();
    messlab4 = new JLabel();
    messlab5 = new JLabel();
    messlab6 = new JLabel();
  //Add components to panels
    p1.add(new JLabel());
    p1.add(new JLabel());
    p1.add(new JLabel());
    p1.add(new JLabel());
    p1.add(new JLabel());
    p1.add(new JLabel());
    p1.add(new JLabel());
    p1.add(new JLabel());
    p1.add(new JLabel());
    p1.add(new JLabel());
    p1.add(new JLabel());
    p1.add(new JLabel());
    p1.add(new JLabel());
    String wmess=messages.getString("WatchMess");
    p1.add(new JLabel(wmess + "     "));
    p1.add(messlab);
    p1.add(messlab2);
    p1.add(messlab3);
    p1.add(messlab4);
    p1.add(messlab5);
    p1.add(messlab6);
  //Create spacer labels
    spacerlab1 = new JLabel("_____________________________");
    spacerlab2 = new JLabel("_____________________________");
  //Add spacer labels to Panel 2 initial screen
    p2.add(spacerlab1);
    p2.add(spacerlab2);
  //Create description text field
    this.descrip = new JTextField();
  //Add functionality to close window
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent event) {
        System.exit(0);
      }
    });
  }

  protected void clearMessages(int value) {
    messlab.setText(null);
    messlab2.setText(null);
    messlab3.setText(null);
    messlab4.setText(null);
    messlab5.setText(null);
  }

  protected void clearMessages() {
    messlab.setText(null);
    messlab2.setText(null);
    messlab3.setText(null);
    messlab4.setText(null);
    messlab5.setText(null);
    messlab6.setText(null);
  }

  protected void resetPanelTwo() {
    clearMessages(1);
    p2.removeAll();
    p2.validate();
    p2.repaint();
  }

  protected void createPanelTwoActLabels() {
    descriplab = new JLabel(messages.getString("ActDescrip"));
    typelab = new JLabel(messages.getString("Type"));
    balab = new JLabel(messages.getString("Balance"));
    creditlab = new JLabel(messages.getString("Credit"));
    begbalab = new JLabel(messages.getString("BegBal"));
    customerlab = new JLabel(messages.getString("Customers"));
    timelab = new JLabel(messages.getString("Time"));
  }

  protected void createPanelTwoCustLabels() {
    fnamelab = new JLabel(messages.getString("FnameLab"));
    lnamelab = new JLabel(messages.getString("LnameLab"));
    milab = new JLabel(messages.getString("MiLab"));
    streetlab = new JLabel(messages.getString("StreetLab"));
    citylab = new JLabel(messages.getString("CityLab"));
    statelab = new JLabel(messages.getString("StateLab"));
    ziplab = new JLabel(messages.getString("ZipLab"));
    phonelab = new JLabel(messages.getString("PhoneLab"));
    emaillab = new JLabel(messages.getString("EmailLab"));
  }

  protected void setDescription(String text) {
    this.desc = text;
    if(text != null) {
      this.descrip.setText(this.desc);
    }
  }

  protected void addCustToActFields(String custID, String actID) {
    p2.removeAll();
    JLabel actnolab = new JLabel(messages.getString("ActNoLab"));
    JLabel custnolab = new JLabel(messages.getString("CustNoLab"));

    if(custID != null) {
      customer = new JTextField(custID);
    } else {
      customer = new JTextField();
    }

    if(actID != null) {
      account = new JTextField(actID);
    } else {
      account = new JTextField();
    }

    p2.add(actnolab);
    p2.add(account);
    p2.add(custnolab);
    p2.add(customer);
    p2.add(OK);
    p2.add(cancel);
    p2.validate();
    p2.repaint();
  }

  protected void createCustFields(boolean readonly, String first, 
  String last, String mid, String str, String cty, String st, 
  String zp, String tel, String mail){
    p2.removeAll();
    createPanelTwoCustLabels();
    fname = new JTextField(first);
    lname = new JTextField(last);
    mi = new JTextField(mid);
    street = new JTextField(str);
    city = new JTextField(cty);
    state = new JTextField(st);
    zip =   new JTextField(zp);
    phone = new JTextField(tel);
    e = new JTextField(mail);

    if(readonly == true) {
      fname.setEditable(false);
      lname.setEditable(false);
      mi.setEditable(false);
      street.setEditable(false);
      city.setEditable(false);
      state.setEditable(false);
      zip.setEditable(false);
      phone.setEditable(false);
      e.setEditable(false);
    }

    p2.add(fnamelab);
    p2.add(fname);
    p2.add(lnamelab);
    p2.add(lname);
    p2.add(milab);
    p2.add(mi);
    p2.add(streetlab);
    p2.add(street);
    p2.add(citylab);
    p2.add(city);
    p2.add(statelab);
    p2.add(state);
    p2.add(ziplab);
    p2.add(zip);
    p2.add(phonelab);
    p2.add(phone);
    p2.add(emaillab);
    p2.add(e);
    p2.add(OK);
    p2.add(cancel);
    p2.validate();
    p2.repaint();
  }

  protected void makeRadioButtons(String type) {
    //Radio Buttons to choose account type
    if(type != null) {
      if(type=="Savings") {
        savingsact.setSelected(true);
      }
      if(type=="Checking") {
        checkingact.setSelected(true);
      }
      if(type=="Credit") {
        creditact.setSelected(true);
      }
      if(type=="Money Market") {
         mnymktact.setSelected(true);
      }
    } else {
      this.savingsact = new JRadioButton(messages.getString("SavingsAct"));
      savingsact.setActionCommand("savingsstring");
      savingsact.setSelected(false);

      this.checkingact = new JRadioButton(messages.getString("CheckingAct"));
      checkingact.setActionCommand("checkingstring");
      checkingact.setSelected(false);

      this.creditact = new JRadioButton(messages.getString("CreditAct"));
      creditact.setActionCommand("creditstring");
      creditact.setSelected(false);

      this.mnymktact = new JRadioButton(messages.getString("MnyMktAct"));
      mnymktact.setActionCommand("mnymktstring");
      mnymktact.setSelected(false);

      this.group = new ButtonGroup();
    }

    savingsact.addActionListener(ehandle);
    checkingact.addActionListener(ehandle);
    creditact.addActionListener(ehandle);
    mnymktact.addActionListener(ehandle);

    group.add(savingsact);
    group.add(checkingact);
    group.add(creditact);
    group.add(mnymktact);

    radioPanel = new JPanel();
    radioPanel.setLayout(new GridLayout(0, 1));
    radioPanel.add(this.savingsact);
    radioPanel.add(this.checkingact);
    radioPanel.add(this.creditact);
    radioPanel.add(this.mnymktact);
  }

  protected void createActFields(boolean readonly, String type, 
  BigDecimal bal, BigDecimal creditline, BigDecimal begbalance, 
  ArrayList alist, Date timestamp){

    p2.removeAll();
    createPanelTwoActLabels();
    String custIDs=null;
    NumberFormat numFormat = NumberFormat.getNumberInstance(currentLocale);

    if(alist.size() != 0) {
      custIDs = alist.toString();
      this.cust = new JTextField(custIDs);
    } else {
      this.cust = new JTextField();
    }

    this.descrip = new JTextField(this.desc);
    this.type = new JTextField(type);
 
    //Internationalize date 
    String viewtime = DateFormat.getDateInstance().format(timestamp); 
    this.time = new JTextField(viewtime);

    //Internationalize numbers
    String balstring = numFormat.format(bal.doubleValue());
    this.bal = new JTextField(balstring);

    String creditstring = numFormat.format(creditline.doubleValue());
    this.credit = new JTextField(creditstring);

    String begbalstring = numFormat.format(begbalance.doubleValue());
    this.begbal = new JTextField(begbalstring);
    //Listen for Return action event
    this.begbal.addActionListener(ehandle);

    if(readonly == false) {
      makeRadioButtons(type);
      p2.add(this.typelab);
      p2.add(this.radioPanel);
      //Read-only part of display 
      this.bal.setEditable(false);
      this.time.setEditable(false);
    } else if(readonly == true) {
      this.type.setEditable(false);
      p2.add(this.typelab);
      p2.add(this.type);
      this.descrip.setEditable(false);
      this.type.setEditable(false);
      this.credit.setEditable(false);
      this.begbal.setEditable(false);
      this.bal.setEditable(false);
      this.cust.setEditable(false);
      this.time.setEditable(false);
    }

    p2.add(this.descriplab);
    p2.add(this.descrip);
    p2.add(this.timelab);
    p2.add(this.time);
    p2.add(this.balab);
    p2.add(this.bal);
    p2.add(this.creditlab);
    p2.add(this.credit);
    p2.add(this.begbalab);
    p2.add(this.begbal);
    p2.add(this.customerlab);
    p2.add(this.cust);
    p2.add(OK);
    p2.add(cancel);
    p2.validate();
    p2.repaint();
  }

  public static void main(String args[]) {
    String language, country;
    if(args.length == 1) {
      language = new String(args[0]);
      currentLocale = new Locale(language, "");
    } else if(args.length == 2) {
      language = new String(args[0]);
      country = new String(args[1]);
      currentLocale = new Locale(language, country);
    } else 
      currentLocale = Locale.getDefault();

    frame = new BankAdmin(currentLocale);
    frame.setTitle(messages.getString("CustAndAccountAdmin"));
    WindowListener l = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
  System.exit(0);
      }
    };
    frame.addWindowListener(l);
    frame.pack();
    frame.setVisible(true);
//Create event handling object
    ehandle = new EventHandle(frame, messages);

  }
}
