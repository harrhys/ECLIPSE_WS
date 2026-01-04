<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MyBlog Struts/Hibernate CRUD</title>
	</head>
	<body>
		<h1> 
			MyBlog Struts/Hibernate CRUD Example Project 
		</h1>
		<div
			style="padding-left: 5px; padding-top: 10px; padding-bottom: 10px; background: #f0f0f0">
			<b><a href="postProcess.do?dispatch=getPosts">Click here</a> to begin testing this
				example</b>
		</div>
		<h2>
			Description
		</h2>

		MyBlogStrutsHibernateExample is a simple web blog application with a Struts 1.x web UI and
		Hibernate persistence.
		<br>
		<br> 
		This project demonstrates full CRUD support for a single blog database 
		table.&nbsp; 
		Also this project is preconfigured to run on the MyEclipse Server 
		Sandbox, 
		i.e., embedded Tomcat 6 and Derby database with a sample schema that 
		ships with 
		MyEclipse 6.0. 
		<br>
		&nbsp;
		<br>
		The project architecture consists of:
		<br>
		<ol>
			<li> 
				A Struts page for that displays all the rows of the database 
			</li>
			<li>A dispatch Action that manages all the actions for performing the 4 CRUD operations 
			</li>
			<li> 
				A Post Hibernate entity and a PostDAO&nbsp; for persistence</li><li>A HibernateSessionFactory helper class that manages access to Hibernate APIs<br></li>
			<li>
				The MYBLOG schema required by this example is included in the
				MyEclipse Server Sandbox
			</li>
		</ol> 
 
		To help you better understand the structure of the MYBLOG schema, an 
		entity-relation diagram of the schema is included the database folder 
		in this 
		<br> 
		project.&nbsp; Double-click the file MYBLOG.mer to view an ER Diagram 
		of the schema. 
		You can generate your own ER diagram by opening the MyEclipse Database 
		<br> 
		Explorer Perspective and right-clicking on the schema you wish to 
		generate the 
		ER Diagram for and selecting &quot;New ER Diagram&quot;, then 
		selecting where you want 
		the ER Diagram generated. 
		<br>
		<h2>
			Requirements
		</h2>
		<ul>
			<li>

				MyEclipse 6.0 or later
			</li>
			<li> 
				Java SE 1.4 or greater 
			</li>
		</ul>
		<h2>
			How to Run
		</h2> 
 
		You can right-click on this project and go to Debug As or Run As then 
		select 
		&quot;MyEclipse Server Application&quot;. MyEclipse 6.0 and later will 
		automatically deploy 
		the application to the MyEclipse Tomcat Server, then start it up and 
		open 
		a browser window for you to the index.jsp page. 
		<br>
		<br>
		NOTE: If you observe an HTTP ERROR 500 or exception on startup,
		namely:
		<br>
		&nbsp;&nbsp; &nbsp;
		<em>SEVERE: Servlet.service() for servlet jsp threw exception <br>
			&nbsp;&nbsp; &nbsp;java.net.ConnectException: Connection refused:
			connect </em>
		<br>
		<br> 
		Then chances are the MyEclipse Derby server did not automatically 
		start up. 
		To fix this issue, please switch to the Servers view in the MyEclipse 
		Perspective, start the MyEclipse Derby server manually and then 
		relaunch the 
		application. 
		<br>
		<h2>
			Related Links
		</h2>
		<ul>
			<li> 
 
				MyEclipse Struts Tutorial - <a href="http://www.myeclipseide.com/documentation/quickstarts/struts/">http://www.myeclipseide.com/documentation/quickstarts/struts/</a>
			</li>
			<li> 
				MyEclipse Hibernate Tutorial - <a href="http://www.myeclipseide.com/documentation/quickstarts/hibernate/">http://www.myeclipseide.com/documentation/quickstarts/hibernate/</a>
			</li>
			
		</ul>
		<h2>
			Feedback
		</h2> 
 
		We hope you found this example project helpful. If you ran into any 
		problems 
		while working with the example project, please feel free to post to 
		our Examples On-Demand	<br>
		Project Forum (
		<a href="http://www.myeclipseide.com/PNphpBB2-viewforum-f-54.html">http://www.myeclipseide.com/PNphpBB2-viewforum-f-54.html</a>) 
		and 
		let us know. Also if you had any suggestions for improvements, noticed 
		something 
		wrong or just wanted to ask questions we encourage you to post and let 
		us know! 
	</body>
</html>
