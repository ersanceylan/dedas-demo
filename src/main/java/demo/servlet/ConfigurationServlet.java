package demo.servlet;

import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.templaterenderer.TemplateRenderer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Named
public class ConfigurationServlet extends HttpServlet {

    @ComponentImport
    private final PluginSettingsFactory pluginSettingsFactory;

    @ComponentImport
    private final TemplateRenderer templateRenderer;

    @Inject
    public ConfigurationServlet(PluginSettingsFactory pluginSettingsFactory, TemplateRenderer templateRenderer) {
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.templateRenderer = templateRenderer;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        templateRenderer.render("/templates/servlet/configuration.vm", resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String infoCount = req.getParameter("due-date-info-count");
        String warningCount = req.getParameter("due-date-warning-count");
        String alertCount = req.getParameter("due-date-alert-count");



        resp.sendRedirect("/jira/plugins/servlet/configuration");
    }
}
