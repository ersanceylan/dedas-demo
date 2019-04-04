package demo.servlet;

import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.templaterenderer.TemplateRenderer;
import demo.DemoSettings;

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

        PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("infoCount", pluginSettings.get(DemoSettings.DUE_DATE_INFO_COUNT));
        parameters.put("warningCount", pluginSettings.get(DemoSettings.DUE_DATE_WARNING_COUNT));
        parameters.put("alertCount", pluginSettings.get(DemoSettings.DUE_DATE_ALERT_COUNT));

        templateRenderer.render("/templates/servlet/configuration.vm",
                parameters, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String infoCount = req.getParameter("due-date-info-count");
        String warningCount = req.getParameter("due-date-warning-count");
        String alertCount = req.getParameter("due-date-alert-count");

        PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
        pluginSettings.put(DemoSettings.DUE_DATE_INFO_COUNT, infoCount);
        pluginSettings.put(DemoSettings.DUE_DATE_WARNING_COUNT, warningCount);
        pluginSettings.put(DemoSettings.DUE_DATE_ALERT_COUNT, alertCount);

        resp.sendRedirect("/jira/plugins/servlet/configuration");
    }
}
