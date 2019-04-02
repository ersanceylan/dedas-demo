package demo.servlet;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.templaterenderer.TemplateRenderer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Named
public class DemoServlet extends HttpServlet {

    @ComponentImport
    private final TemplateRenderer templateRenderer;

    @Inject
    public DemoServlet(TemplateRenderer templateRenderer) {
        this.templateRenderer = templateRenderer;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, Object> parameters = new HashMap<>();

        long issuecount = ComponentAccessor.getIssueManager().getIssueCount();
        parameters.put("issueCount", issuecount);

        templateRenderer.render("/templates/servlet/demo.vm",
                parameters, resp.getWriter());
    }
}
