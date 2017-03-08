package sailpoint.plugin.log4jadmin.rest;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sailpoint.object.Attributes;
import sailpoint.plugin.common.PluginRegistry;
import sailpoint.plugin.log4jadmin.Log4jAdminDTO;
import sailpoint.plugin.rest.jaxrs.AllowAll;
import sailpoint.rest.BaseResource;
import sailpoint.tools.GeneralException;
import sailpoint.plugin.rest.AbstractPluginRestResource;
import sailpoint.plugin.rest.jaxrs.SPRightsRequired;
//import sailpoint.plugin.rest.jaxrs.AllowAll;
import sailpoint.web.plugin.config.Plugin;


@SPRightsRequired(value={"LogItPluginRestServiceAllow"})
@Path("log4jadmin")
public class LogitResource extends AbstractPluginRestResource {

    private static int _testCounter = 0;

    public LogitResource() {
    }

    // Testing @AllowAll override.   This method will allow anyone (assuming the get past the login/csrf filters, etc)
    @AllowAll

    /*
    You could also specify rights here.   Method annotations always take precedence over the parent (class) annotation
    @SPRightsRequired(value={"someRightHere"})
     */

    /**
     * Returns the log4j Admin message
     */
    @GET
    @Path("getMessage")
    @Produces(MediaType.APPLICATION_JSON)

    public Log4jAdminDTO getTest() throws Exception {

        _testCounter++;

        String message = "No message set!";
        Log4jAdminDTO logItDTO = new Log4jAdminDTO();

        Plugin plugin = PluginRegistry.get("Log4jAdmin");
        if (plugin != null) {
            Attributes settingsAttrs = plugin.getConfigurableSettings();
            if (settingsAttrs.containsKey("Message")) {
                message = (String)settingsAttrs.get("Message");
            }

            message += " (" + _testCounter + ")";
        }

        logItDTO.set_message(message);

        return logItDTO;
    }
}

