var settingsApp = angular.module("settingsApp", []);

settingsApp.controller("SettingsAppCtrl", function($scope) {
	$scope.globalProperties = [
		{"name": "some global property", "value": "value"},
		{"name": "some other global property", "value": "value"}
	]
});