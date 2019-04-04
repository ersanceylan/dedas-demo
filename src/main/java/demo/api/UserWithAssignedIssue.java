package demo.api;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.user.ApplicationUser;

import java.util.List;

public class UserWithAssignedIssue {

    private ApplicationUser applicationUser;

    private List<Issue> issueList;

    public UserWithAssignedIssue() {
    }

    public UserWithAssignedIssue(ApplicationUser applicationUser, List<Issue> issueList) {
        this.applicationUser = applicationUser;
        this.issueList = issueList;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public List<Issue> getIssueList() {
        return issueList;
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }

    public void addIssueToList(Issue issue) {
        this.issueList.add(issue);
    }
}
