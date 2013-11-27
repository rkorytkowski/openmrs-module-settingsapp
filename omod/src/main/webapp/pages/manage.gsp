<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeJavascript("uicommons", "datatables/jquery.dataTables.min.js")
	ui.includeJavascript("settingsapp", "angular.min.js")
	ui.includeJavascript("settingsapp", "controllers.js")
%>
<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("coreapps.app.activeVisits.label")}"}
    ];
</script>

<h3>${ ui.message("settingsapp.manage.label") }</h3>

<div ng-app="settingsApp">
	<div ng-controller="SettingsAppCtrl">
		<ul>
			<li ng-repeat="globalProperty in globalProperties">
				{{globalProperty.name}} - {{globalProperty.value}}
			</li>
		</ul>
	</div>
</div>
