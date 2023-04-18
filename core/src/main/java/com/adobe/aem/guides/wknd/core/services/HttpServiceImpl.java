package com.adobe.aem.guides.wknd.core.services;

import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = HttpService.class, immediate = true)
@Designate(ocd = HttpConfiguration.class)
public class HttpServiceImpl implements HttpService {

    private static final Logger log = LoggerFactory.getLogger(HttpServiceImpl.class);

    private HttpConfiguration configuration;

    @Activate
    protected void activate(HttpConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String makeHttpCall() throws JSONException {
        log.info("----------< Reading the config values >----------");
        String jsonString = "";
        try {
            boolean enable = configuration.enableConfig();
            String protocol = configuration.getProtocol();
            String server = configuration.getServer();
            String endpoint = configuration.getEndpoint();
            String url = protocol + "://" + server + "/" + endpoint;
            jsonString = new JSONObject().put("url", url).put("enable", enable).toString();
            log.info("----------< JSON response from the webservice is >----------");
            log.info(jsonString);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new JSONObject().put("error", e.getMessage()).toString();
        }
        return jsonString;
    }
}
