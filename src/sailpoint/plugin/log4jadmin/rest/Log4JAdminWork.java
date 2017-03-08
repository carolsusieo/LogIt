package sailpoint.plugin.log4jadmin.rest;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sailpoint.object.Attributes;
import sailpoint.plugin.common.PluginRegistry;
import sailpoint.plugin.rest.jaxrs.AllowAll;
import sailpoint.rest.BaseResource;
import sailpoint.tools.GeneralException;
import sailpoint.plugin.rest.AbstractPluginRestResource;
import sailpoint.plugin.rest.jaxrs.SPRightsRequired;
//import sailpoint.plugin.rest.jaxrs.AllowAll;
import sailpoint.web.plugin.config.Plugin;

import sailpoint.api.SailPointContext;
import sailpoint.api.CertificationContext;
import sailpoint.api.PersistenceManager;
import sailpoint.object.Server;
import sailpoint.tools.GeneralException;



import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import sailpoint.plugin.rest.jaxrs.SPRightsRequired;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;


import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import sailpoint.plugin.log4jadmin.Log4jAdminDTO;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;




@SPRightsRequired(value={"LogItPluginRestServiceAllow"})
@Path("log4jadminstuff")





public class Log4JAdminWork extends AbstractPluginRestResource{

    
    String containsFilter = "Contains";
    String beginsWithFilter = "Begins+With";
    
    String[] logLevels = { " debug ", " info  ", " warn  ", " error ", "  off  " };      

    StringBuilder targetOperation;//   = "";//value in "operation";
    StringBuilder targetLogger;//      = "";//value in "logger";
    StringBuilder targetLogLevel;//    = "";// value in"newLogLevel";
    StringBuilder logNameFilter;//     = "";//value in"logNameFilter";
    StringBuilder logNameFilterType;// = "";// value in "logNameFilterType";

	
    public Log4JAdminWork() {

    	   targetOperation = new StringBuilder("");
    	    targetLogger= new StringBuilder("");//      = "";//value in "logger";
    	    targetLogLevel = new StringBuilder("");//    = "";// value in"newLogLevel";
    	    logNameFilter = new StringBuilder("");//     = "";//value in"logNameFilter";
    	    logNameFilterType = new StringBuilder("logNameFilterType");// = "";// value in "logNameFilterType";

    }
    // Testing @AllowAll override.   This method will allow anyone (assuming the get past the login/csrf filters, etc)
    @AllowAll


    
    private void writeIt(JSONArray content)
    {

			System.out.println("Successfully Copied JSON Object");
			System.out.println("\nJSON Array: " + content);

    }

    @GET
    @Path("getDB")
    @Produces(MediaType.TEXT_PLAIN)
 
    public void getDB () throws Exception{
    	// want to do a database call to get all the server information


    	// I have a reference to the Sailpoint.context...  
    	// if I don't, I can get it easily enough.
   	 //   	List<Server> list = new ArrayList<Server>();
   	    	
    	
//    	    	System.out.println("plugin name: " + pman);

    	
    }

    
    @GET
    @Path("getLogStuff")
    @Produces(MediaType.TEXT_PLAIN)
 
    public String getLogStuff(
    	    @Context UriInfo uri,
    	    @DefaultValue("") @QueryParam("operation") String operation,
    	    @DefaultValue("") @QueryParam("logger") String logger2,
    	    @DefaultValue("") @QueryParam("newLogLevel") String newLogLevel, 
	   	   @DefaultValue("") @QueryParam("logNameFilter") String lnf,
	   	   @DefaultValue("") @QueryParam("logNameFilterType") String lnft
	   	   

		 ) throws Exception {
    	
  //System.out.println("1 " + operation + "2 " + logger2 + "3 " + newLogLevel + " 4 " + lnf + " 5 " + lnft);  	  
    	   JSONArray list = new JSONArray();
    	   
  if(targetOperation.length() != 0){
	  targetOperation.replace(0, targetOperation.length()-1, operation);
  }
  else {
	  targetOperation.insert(0,operation);
  }
  if(targetLogger.length() != 0){
	  targetLogger.replace(0,targetLogger.length()-1, logger2);
  }
  else
	  targetLogger.insert(0, logger2);
  if(targetLogLevel.length() != 0){
	  targetLogLevel.replace(0, targetLogLevel.length() -1, newLogLevel);	  
  }
  else
	  targetLogLevel.insert(0, newLogLevel);
  if(logNameFilter.length() != 0)
  {
	  logNameFilter.replace(0,logNameFilter.length()-1, lnf);
  }
  else
	  logNameFilter.insert(0, lnf);
  if(logNameFilterType.length() != 0)
  
	  logNameFilterType.replace(0,logNameFilterType.length()-1,lnft);
  else if (lnft.length() > 1)
	  logNameFilterType.insert(0, lnft);


  
  Enumeration loggers = LogManager.getCurrentLoggers();
  HashMap loggersMap = new HashMap(128);
  
  Logger rootLogger = LogManager.getRootLogger();
  
  if(!loggersMap.containsKey(rootLogger.getName()))
  {
       loggersMap.put(rootLogger.getName(), rootLogger);
  }

  while(loggers.hasMoreElements())
  {
    Logger logger = (Logger)loggers.nextElement();    


    if(logNameFilter == null || (logNameFilter.toString()).trim().length() == 0)
    {
        loggersMap.put(logger.getName(), logger);                                                
    }
    else if((logNameFilterType.toString()).contains(containsFilter.toString()))
    {

      if(logger.getName().toUpperCase().indexOf((logNameFilter.toString()).toUpperCase()) >= 0)
      {
    	  
        loggersMap.put(logger.getName(), logger);                                                                
      }
    }
    else
    {
          // Either was no filter in IF, contains filter in ELSE IF, or begins with in ELSE
          if(logger.getName().startsWith(logNameFilter.toString()))
          {
            loggersMap.put(logger.getName(), logger);                                                                
          }              
     }

  }
  // we've loadded the Map

                      
      Set loggerKeys = loggersMap.keySet();
      String[] keys = new String[loggerKeys.size()];
      keys = (String[])loggerKeys.toArray(keys);
      Arrays.sort(keys, String.CASE_INSENSITIVE_ORDER);
      
      
     
      for(int i=0; i< keys.length; i++)
      {
        Logger logger = (Logger)loggersMap.get(keys[i]);

        // MUST CHANGE THE LOG LEVEL ON LOGGER BEFORE GENERATING THE LINKS AND THE
        // CURRENT LOG LEVEL OR DISABLED LINK WON'T MATCH THE NEWLY CHANGED VALUES
        if("changeLogLevel".equals(targetOperation.toString()) && targetLogger.toString().equals(logger.getName()))
        {
          Logger selectedLogger = (Logger)loggersMap.get(targetLogger.toString());
          selectedLogger.setLevel(Level.toLevel(targetLogLevel.toString()));
        }  

        if(logger != null)
        {
	  	  JSONObject obj = new JSONObject();
          obj.put("logger", logger.getName());
          obj.put("parent", logger.getParent() != null ? logger.getParent().getName(): "");

          for(int cnt=0; cnt<logLevels.length; cnt++)
          {
           	StringBuilder holder = new StringBuilder("");
     
                               
            if(logger.getLevel() == Level.toLevel(logLevels[cnt].trim()) || 
            		logger.getEffectiveLevel() == Level.toLevel(logLevels[cnt].trim()))
            {
            	holder.append("[" + logLevels[cnt].toUpperCase() + "]");
            	
            }
            else
            {
               	// problem with starting this restful service is that
               	// there is no csrf stuff associated....
               	// need to send it to another xhtml page?  back to this xhtml page?
            	  //String url = "/plugin/Log4jAdmin/log4jadminstuff/getLogStuff" + 
               	//String url = "/plugins/Log4jAdmin/ui/text.xhtml#" +
               	String url = "?operation=changeLogLevel&logger=" + logger.getName() + 
                		"&newLogLevel=" + logLevels[cnt].trim() + 
                		(logNameFilter.toString() != null ? "&logNameFilter=" + 
                		logNameFilter.toString() + 
                		(logNameFilterType.toString() != null? "&logNameFilterType=" +
                		logNameFilterType.toString():""):"");  
               	
                 	// rather than a link, make it a button that call a javascript 
            	// function in the html and gives it information
            	//holder.append("<a href='" + url + "'>[" + logLevels[cnt] + ">]</a>&nbsp");
               	StringBuilder dquote = new StringBuilder("\"");
               	String squote = "'";
                holder.append(
                		"<INPUT type=" + squote + "button"  + squote +
                		        " class=levelBtn" + 
                				" value=" + squote + logLevels[cnt].trim() + squote +  
                				  " onclick=" + dquote +  "levelFunction(" 
                				+ squote + url +  squote + ")" + dquote + "/>"
                				);

                }
            StringBuilder level = new StringBuilder("level" + cnt);
            obj.put(level.toString(),holder.toString());
         }                                                                                
         list.put(obj);
        }
        
      } 
//     writeIt(list);
     return list.toString();
 }
    
    
}


