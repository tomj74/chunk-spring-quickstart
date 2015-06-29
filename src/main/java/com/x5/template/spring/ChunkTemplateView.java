package com.x5.template.spring;

import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.io.Resource;

import com.x5.template.Theme;
import com.x5.template.ThemeConfig;
import com.x5.template.Chunk;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.io.PrintWriter;

public class ChunkTemplateView extends InternalResourceView
{
    private static Theme theme = null;

    @Override
    protected void renderMergedOutputModel(
        Map<String, Object> model,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception
    {
        Resource templateFile = getApplicationContext().getResource(getUrl());

        String rcKey = getRequestContextAttribute();
        RequestContext rc = (RequestContext)model.get(rcKey);

        Theme theme = getTheme(templateFile.getFile().getParent());
        Chunk chunk = theme.makeChunk(getBeanName());
        chunk.setLocale(rc.getLocale());
        chunk.setMultiple(model);
        chunk.set(rcKey, mapifyRequestContext(rc, request));

        PrintWriter writer = response.getWriter();
        chunk.render(writer);
        writer.flush();
        writer.close();
    }

    private Map<String,String> mapifyRequestContext(RequestContext rc, HttpServletRequest request)
    {
        Map<String,String> rcMap = new HashMap<String,String>();

        // expose some potentially useful info to the template via the {$rc} tag
        rcMap.put("uri", rc.getRequestUri());
        rcMap.put("context_path", rc.getContextPath());
        rcMap.put("servlet_path", rc.getPathToServlet());
        rcMap.put("scheme", request.getScheme());
        rcMap.put("method", request.getMethod());
        rcMap.put("server_name", request.getServerName());
        rcMap.put("remote_addr", request.getRemoteAddr());
        rcMap.put("remote_host", request.getRemoteHost());
        rcMap.put("remote_user", request.getRemoteUser());

        return rcMap;
    }

    private Theme getTheme(String path)
    {
        if (theme == null) {
            Map<String,String> params = new HashMap<String,String>();
            // If no theme path (for include/exec references) is specified
            // in the config, default to the path of the invoked template file.
            params.put(ThemeConfig.THEME_PATH, path);

            Map<String,String> configParams = getConfigParams();
            if (configParams != null) {
                for (String key : configParams.keySet()) {
                    String paramName = key;
                    String paramValue = configParams.get(key);
                    // blank values are considered not-provided
                    if (paramValue != null && paramValue.trim().length() > 0) {
                        params.put(paramName, paramValue);
                    }
                }
            }
            ThemeConfig config = new ThemeConfig(params);
            theme = new Theme(config);
        }

        return theme;
    }

    @SuppressWarnings("unchecked")
    private Map<String,String> getConfigParams()
    {
        try {
            Object config = getApplicationContext().getBean("chunkTemplatesConfig");
            return (Map<String,String>)config;
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }
}
