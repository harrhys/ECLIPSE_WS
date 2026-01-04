package com.myeclipseide.examples.myblog.struts.action;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.myeclipseide.examples.myblog.strutshibernate.Post;

public class PostAction extends DispatchAction {
	private Log logger = LogFactory.getLog(this.getClass());

	private static PostService postService = new PostService();

	public ActionForward getPosts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("getPosts");
		populatePosts(request);
		return mapping.findForward("success");
	}

	public ActionForward setUpForInsertOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("setUpForInsertOrUpdate");
		PostForm postForm = (PostForm) form;
		if (isUpdate(request, postForm)) {
			Integer id = postForm.getId();
			Post post = postService.getPost(id);
			copyProperties(postForm, post);
		} else {
			postForm.setPosttime(new Date().toString());
		}
		return mapping.findForward("success");
	}

	private void copyProperties(PostForm postForm, Post post) {
		postForm.setId(post.getId());
		postForm.setTitle(post.getTitle());
		postForm.setContent(post.getContent());
		postForm.setPosttime(post.getPosttime().toString());
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("delete");
		PostForm postForm = (PostForm) form;
		Integer id = postForm.getId();
		Post post = postService.getPost(id);
		postService.deletePost(post);
		populatePosts(request);
		return mapping.findForward("success");
	}

	public ActionForward insertOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("insertOrUpdate");
		PostForm postForm = (PostForm) form;
		if (validationSuccessful(request, postForm)) {
			Post post = new Post();
			copyProperties(post, postForm);
			if (isUpdate(request, postForm)) {
				logger.debug("update");
				postService.updatePost(post);
			} else {
				logger.debug("insert");
				// REMOVED, the PostForm should be setting it's own default date
				// if (post.getPosttime() == null) {
				// post.setPosttime(new Date());
				// }
				postService.insertPost(post);
			}
			populatePosts(request);
			return mapping.findForward("success");
		}

		return mapping.findForward("failure");
	}

	private void copyProperties(Post post, PostForm postForm) {
		post.setId(postForm.getId());
		post.setTitle(postForm.getTitle());
		post.setContent(postForm.getContent());
		try {
			post.setPosttime(PostForm.simpleDateFormat.parse(postForm
					.getPosttime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void populatePosts(HttpServletRequest request) {
		List posts = postService.getAllPosts();
		request.setAttribute("posts", posts);
	}

	private boolean isUpdate(HttpServletRequest request, PostForm postForm) {
		boolean updateFlag = true;
		/*
		 * if ID is null or 0 we know we are doing an insert. You could check
		 * other things to decide, like a dispatch param It's annoying that
		 * BeanUtils will convert nulls to 0 so have to do 0 check also, or you
		 * could register a converter, which is the preferred way to handle it,
		 * but goes beyond this demo
		 */
		Integer id = postForm.getId();
		// Integer id = 100;
		if (id == null || id.intValue() == 0) {
			updateFlag = false;
		} else if (request.getParameter("postId") != null
				&& !request.getParameter("postId").equals("")) {
			postForm.setId(new Integer(request.getParameter("postId")));
		}
		request.setAttribute("updateFlag", Boolean.valueOf(updateFlag));
		return updateFlag;
	}

	private boolean validationSuccessful(HttpServletRequest request,
			PostForm form) {
		/*
		 * if you really like using the validation framework stuff, you can just
		 * call ActionErrors errors = form.validate( mapping, request ); in this
		 * method and check for errors being empty, if not save them and you're
		 * done. I end up finding the validation framework a bit annoying to
		 * work with, so I do it old-Skool way. Inevitably in a more complex app
		 * you end up having to perform more complex validation than the
		 * validation framework provides, so I just assume keep it all here in
		 * one place, versus having some handled by xml configuration and some
		 * hardcoded.
		 */
		boolean isOk = true;
		ActionMessages errors = new ActionMessages();
		if (form.getId() == null)
			errors.add("id", new ActionMessage("errors.required", "ID"));

		if (form.getTitle() == null || form.getTitle().trim().length() == 0)
			errors.add("postTitle", new ActionMessage("errors.required",
					"Post Title"));

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			isOk = false;
		}

		return isOk;
	}
}
