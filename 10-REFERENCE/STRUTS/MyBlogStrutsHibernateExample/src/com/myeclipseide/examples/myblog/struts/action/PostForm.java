package com.myeclipseide.examples.myblog.struts.action;

import java.text.SimpleDateFormat;

import org.apache.struts.action.ActionForm;

/**
 * Post entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class PostForm extends ActionForm {
	private static final long serialVersionUID = 1L;

	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"EEE MMM dd HH:mm:ss zzz yyyy");

	// Fields

	private Integer id;

	private String title;

	private String content;

	private String posttime;

	// Constructors

	public PostForm() {
		/* default constructor */
	}

	/* minimal constructor */
	public PostForm(Integer id, String title) {
		this.id = id;
		this.title = title;
	}

	/* full constructor */
	public PostForm(Integer id, String title, String content, String posttime) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.posttime = posttime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPosttime() {
		return this.posttime;
	}

	public void setPosttime(String posttime) {
		this.posttime = posttime;
		// try {
		// // this.posttime = DateFormat.getInstance().parse(posttime);
		// this.posttime = simpleDateFormat.parse(posttime);
		//			
		// System.out.println("Date Parsed: [" + getPosttime() + "]");
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
	}

	// public void setPosttime(Date posttime) {
	// this.posttime = posttime;
	// }

}
