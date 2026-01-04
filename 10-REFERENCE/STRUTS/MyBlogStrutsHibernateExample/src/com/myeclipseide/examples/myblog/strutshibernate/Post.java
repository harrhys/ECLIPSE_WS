package com.myeclipseide.examples.myblog.strutshibernate;

import java.util.Date;

/**
 * Post entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Post implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	// Fields

	private Integer id;

	private String title;

	private String content;

	private Date posttime;

	// Constructors

	public Post() {
		/* default constructor */
	}

	/* minimal constructor */
	public Post(Integer id, String title) {
		this.id = id;
		this.title = title;
	}

	/* full constructor */
	public Post(Integer id, String title, String content, Date posttime) {
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

	public Date getPosttime() {
		return this.posttime;
	}

	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}
}
