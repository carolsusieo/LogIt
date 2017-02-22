(function() {
    'use strict';

    angular.module('sailpoint.home.desktop')
        .constant('PLUGIN_ROOT_FOLDER_URL', PluginFramework.PluginFolderName)
        .controller('Log4jAdminWidgetCtrl', function($scope, PLUGIN_ROOT_FOLDER_URL) {
            $scope.getWidgetMessage = function() {
              return "Log4jAdmin Widget!"
            };
        })
        .directive('spLog4jAdminWidget', function(PLUGIN_ROOT_FOLDER_URL) {
            return {
                restrict: 'E',
                scope: {
                    widget: '=spWidget'
                },
                controller: 'Log4jAdminWidgetCtrl',
                controllerAs: 'widgetCtrl',
                bindToController: true,
                templateUrl: PLUGIN_ROOT_FOLDER_URL + '/Log4jAdmin/ui/htmlTemplates/widget/log4jAdminWidget.jsf'
            };
        });
})();


