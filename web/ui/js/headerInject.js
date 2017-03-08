
/*

Other available values to build paths, etc. that are obtained from the server and set in the PluginFramework JS object

PluginFramework.PluginBaseEndpointName = '#{pluginFramework.basePluginEndpointName}';
PluginFramework.PluginEndpointRoot = '#{pluginFramework.basePluginEndpointName}/#{pluginFramework.basePluginEndpointName}';
PluginFramework.PluginFolderName = '#{pluginFramework.pluginFolderName}';
PluginFramework.CurrentPluginUniqueName = '#{pluginFramework.uniqueName}';
PluginFramework.CsrfToken = Ext.util.Cookies.get('CSRF-TOKEN');


 */


// this just sets up the icon so it will start the restful call or launch the webpage at a later time 
var logItUrl = SailPoint.CONTEXT_PATH + '/pluginPage.jsf?pn=Log4jAdmin';

jQuery(document).ready(function(valuein){
    /* can't figure how to get plugin setting values here so I could change the regex and allow icon to show on different pages...*/	
	var reg = /.*?debug.*?/;
	if(reg.test(document.location.href)){
	jQuery("ul.navbar-right li:first")
		.before(
				'<li class="dropdown">' +
				'		<a href="' + logItUrl + '" tabindex="0" role="menuitem" data-snippet-debug="off">' +
				'			<i role="presenation" class="fa fa-file-text-o fa-lg custom"></i>' +
				'		</a>' +
				'</li>'
		);
	}
});
