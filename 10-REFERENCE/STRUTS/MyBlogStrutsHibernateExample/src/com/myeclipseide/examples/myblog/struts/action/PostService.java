package com.myeclipseide.examples.myblog.struts.action;

import java.util.List;

import org.hibernate.Transaction;

import com.myeclipseide.examples.myblog.strutshibernate.HibernateSessionFactory;
import com.myeclipseide.examples.myblog.strutshibernate.Post;
import com.myeclipseide.examples.myblog.strutshibernate.PostDAO;

public class PostService {
	private PostDAO dao;

	public PostService() {
		this.dao = new PostDAO();
	}

	public List getAllPosts() {
		return dao.findAll();
	}

	public void updatePost(Post post) {
		Transaction tx = HibernateSessionFactory.getSession()
				.beginTransaction();
		dao.merge(post);
		tx.commit();
	}

	public void deletePost(Post post) {
		Transaction tx = HibernateSessionFactory.getSession()
				.beginTransaction();
		dao.delete(post);
		tx.commit();
	}

	public Post getPost(Integer id) {
		return dao.findById(id);
	}

	public void insertPost(Post post) {
		Transaction tx = HibernateSessionFactory.getSession()
				.beginTransaction();
		dao.save(post);
		tx.commit();
	}
}
