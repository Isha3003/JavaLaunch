package com.javalaunch.dto;

public class DeploymentRequest {

    private String projectName;
    private String githubUrl;

    public DeploymentRequest() {
    }

    public DeploymentRequest(String projectName, String githubUrl) {
        this.projectName = projectName;
        this.githubUrl = githubUrl;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }
}