package com.test.menu;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MenuItem {
	
	private int id;
	/*private int parentMenuId;
	private int sequence;*/
	private String menuCode;
	
	/*private String menuLabel;
	private String menuUrl;
	private String status;
	private String command;
	private String param;
	private String msgtemplate;
	*/
	
	private List<MenuItem> menuItem;

	
	public MenuItem() {
	}
	
	public MenuItem(int id,  String menuCode, List menuItem) {
		super();
		this.id = id;
		this.menuCode = menuCode;
		this.menuItem = menuItem;
	
	}

	/*public MenuItem(int id, int parentMenuId, int sequence, String menuCode,
			String menuLabel, String menuUrl, String status, String command,
			String param, String msgtemplate, List<MenuItem> menuItems) {
		super();
		this.id = id;
		this.parentMenuId = parentMenuId;
		this.sequence = sequence;
		this.menuCode = menuCode;
		this.menuLabel = menuLabel;
		this.menuUrl = menuUrl;
		this.status = status;
		this.command = command;
		this.param = param;
		this.msgtemplate = msgtemplate;
		this.menuItems = menuItems;
	}*/

	@XmlElement  
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	/*@XmlElement  
	public int getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(int parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	@XmlElement  
	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}*/
	@XmlElement  
	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	/*@XmlElement  
	public String getMenuLabel() {
		return menuLabel;
	}

	public void setMenuLabel(String menuLabel) {
		this.menuLabel = menuLabel;
	}
	@XmlElement  
	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	@XmlElement  
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@XmlElement  
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	@XmlElement  
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	@XmlElement  
	public String getMsgtemplate() {
		return msgtemplate;
	}

	public void setMsgtemplate(String msgtemplate) {
		this.msgtemplate = msgtemplate;
	}
	
*/
	
	@XmlElement  
	public List<MenuItem> getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(List<MenuItem> menuItems) {
		this.menuItem = menuItems;
	}
	
	public String toString()
	{
		return id+"."+menuCode;
	}
	
	

}
