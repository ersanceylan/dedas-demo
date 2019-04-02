package demo.contextProvider;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.plugin.webfragment.contextproviders.AbstractJiraContextProvider;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.SessionKeys;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class IssueDueDateCalculator extends AbstractJiraContextProvider {

    @Override
    public Map getContextMap(ApplicationUser applicationUser, JiraHelper jiraHelper) {
        Issue currentIssue = (Issue) jiraHelper.getContextParams().get("issue");

        boolean isPassed = currentIssue.getDueDate()
                .toLocalDateTime().isBefore(LocalDateTime.now());

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("isPassed", isPassed);

        return parameters;
    }

}