package demo.api;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.jql.builder.JqlQueryBuilder;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.atlassian.query.Query;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import demo.DemoSettings;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/demo")
@Named
public class DemoApi {

    @ComponentImport
    private final PluginSettingsFactory pluginSettingsFactory;

    @Inject
    public DemoApi(PluginSettingsFactory pluginSettingsFactory) {
        this.pluginSettingsFactory = pluginSettingsFactory;
    }

    @GET
    @Path("/status")
    @AnonymousAllowed
    public Response status() {
        return Response.ok().build();
    }

    @GET
    @Path("/authenticated")
    public Response statusCheckForAuthenticatedUser() {
        return Response.ok("authenticated").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/plugin-settings")
    public Response getPluginSettings() {

        PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("infoCount", pluginSettings.get(DemoSettings.DUE_DATE_INFO_COUNT));
        parameters.put("warningCount", pluginSettings.get(DemoSettings.DUE_DATE_WARNING_COUNT));
        parameters.put("alertCount", pluginSettings.get(DemoSettings.DUE_DATE_ALERT_COUNT));

        return Response.ok(parameters).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("users-with-assigned-issues")
    public Response getUsersWithAssignedIssues() {
        List<Issue> issues = getAllIssues();
        return Response.ok(issues.stream().map(Issue::getAssigneeId).collect(Collectors.toList())).build();
    }

    private List<Issue> getAllIssues() {
        try {
            SearchService searchService = ComponentAccessor.getComponent(SearchService.class);
            ApplicationUser applicationUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
            Query query = JqlQueryBuilder.newBuilder().buildQuery();

            SearchResults searchResults = searchService.search(applicationUser, query, PagerFilter.getUnlimitedFilter());
            return searchResults.getIssues();
        } catch (SearchException e) {
            throw new RuntimeException();
        }
    }

}




