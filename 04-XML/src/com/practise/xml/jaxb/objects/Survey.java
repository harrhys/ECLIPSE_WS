package com.practise.xml.jaxb.objects;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Survey {

	private List<Survey> survey;
	private int id;
	private String question;
	private List<Answer> answers;

	public Survey() {
	}

	public Survey(List<Survey> surveys, int id, String question, List<Answer> answers) {
		super();
		this.survey = surveys;
		this.id = id;
		this.question = question;
		this.answers = answers;
	}

	@XmlElement
	public List<Survey> getSurvey() {
		return survey;
	}

	public void setSurvey(List<Survey> surveys) {
		this.survey = surveys;
	}

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@XmlElement
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public String toString() {

		System.out.println();

		StringBuilder s = new StringBuilder();
		s.append("Surveys: \n");
		s.append("-------- \n");

		if (survey != null && survey.size() > 0)
			for (Iterator iterator = survey.iterator(); iterator.hasNext();) {

				Survey survey = (Survey) iterator.next();

				s.append("Survey id : " + survey.getId() + "\n");
				s.append("Question : " + survey.getQuestion() + "\n");

				if (survey.getAnswers() != null && survey.getAnswers().size() > 0)
					for (Iterator iterator1 = survey.getAnswers().iterator(); iterator1.hasNext();) {
						Answer ans = (Answer) iterator1.next();
						s.append("id: " + ans.getId() + " posted by: " + ans.getPostedby() + " answer: "
								+ ans.getAnswer() + "\n");

					}
			}
		return s.toString();
	}

}

class Answer {

	private int id;
	private String answer;
	private String postedby;

	public Answer() {
		super();
	}

	public Answer(int id, String answer, String postedby) {
		super();
		this.id = id;
		this.answer = answer;
		this.postedby = postedby;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getPostedby() {
		return postedby;
	}

	public void setPostedby(String postedby) {
		this.postedby = postedby;
	}

}
