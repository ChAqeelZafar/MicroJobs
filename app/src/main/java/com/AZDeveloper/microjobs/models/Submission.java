package com.AZDeveloper.microjobs.models;

public class Submission {
    private String userId, taskId, proofTitle, proofDiscription;
    private int  submissionStatus;

    public Submission(String userId, String taskId, String proofTitle, String proofDiscription, int submissionStatus) {
        this.userId = userId;
        this.taskId = taskId;
        this.proofTitle = proofTitle;
        this.proofDiscription = proofDiscription;
        this.submissionStatus = submissionStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProofTitle() {
        return proofTitle;
    }

    public void setProofTitle(String proofTitle) {
        this.proofTitle = proofTitle;
    }

    public String getProofDiscription() {
        return proofDiscription;
    }

    public void setProofDiscription(String proofDiscription) {
        this.proofDiscription = proofDiscription;
    }

    public int getSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(int submissionStatus) {
        this.submissionStatus = submissionStatus;
    }
}
