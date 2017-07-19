package com.rssoftware.upiint.schema;

/**
 * Created by Neeloy on 6/16/2016.
 */
public class SecurityQuestion {

    protected String questionId;
    protected String answer;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
