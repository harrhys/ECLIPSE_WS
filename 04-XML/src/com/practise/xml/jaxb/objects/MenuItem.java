package com.practise.xml.jaxb.objects;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MenuItem {

	private int id;

	private String menuCode;

	private List<MenuItem> menuItem;

	public MenuItem() {
	}

	public MenuItem(int id, String menuCode, List<MenuItem> menuItem) {
		super();
		this.id = id;
		this.menuCode = menuCode;
		this.menuItem = menuItem;

	}

	@XmlElement
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement
	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	@XmlElement
	public List<MenuItem> getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(List<MenuItem> menuItems) {
		this.menuItem = menuItems;
	}

	public String toString() {

		StringBuilder s = new StringBuilder();

		s.append("RootMenu :-\nid : " + id + " menucode: " + menuCode + "\n");
		s.append("SubMenus :-\n");

		if(menuItem.size()>0)
		for (Iterator iterator = menuItem.iterator(); iterator.hasNext();) {
			MenuItem menuItem = (MenuItem) iterator.next();
			s.append("id : " + menuItem.getId() + " menucode: " + menuItem.getMenuCode() + "\n");

		}

		return s.toString();
	}

}
