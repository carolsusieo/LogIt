
/*

Other available values to build paths, etc. that are obtained from the server and set in the PluginFramework JS object

PluginFramework.PluginBaseEndpointName = '#{pluginFramework.basePluginEndpointName}';
PluginFramework.PluginEndpointRoot = '#{pluginFramework.basePluginEndpointName}/#{pluginFramework.basePluginEndpointName}';
PluginFramework.PluginFolderName = '#{pluginFramework.pluginFolderName}';
PluginFramework.CurrentPluginUniqueName = '#{pluginFramework.uniqueName}';
PluginFramework.CsrfToken = Ext.util.Cookies.get('CSRF-TOKEN');



jQuery(document).ready(function(){
	var reg = |.*|; 
	var test = "debug";
	/*if(document.location.href.indexOf("debug") > -1){
	/*if(reg.test(document.location.href.val())){
	jQuery("ul.navbar-right li:first")
		.before(
				'<li class="dropdown">' +
				'		<a href="' + logItUrl + '" tabindex="0" role="menuitem" >' +
				'			<i role="presenation" class="fa fa-file-text-o fa-lg custom"></i>' +
				'		</a>' +
				'</li>'
		);
	}
	
});

 */



var logItUrl = SailPoint.CONTEXT_PATH + '/' + PluginFramework.PluginFolderName + '/Log4jAdmin/ui/js/log4jAdmin.jsp';

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
