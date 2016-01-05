System.register(['angular2/platform/browser', './app-component', "./user-service", "angular2/http"], function(exports_1) {
    var browser_1, app_component_1, user_service_1, http_1;
    return {
        setters:[
            function (browser_1_1) {
                browser_1 = browser_1_1;
            },
            function (app_component_1_1) {
                app_component_1 = app_component_1_1;
            },
            function (user_service_1_1) {
                user_service_1 = user_service_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            }],
        execute: function() {
            browser_1.bootstrap(app_component_1.AppComponent, [user_service_1.UserService, http_1.HTTP_PROVIDERS]);
        }
    }
});
//# sourceMappingURL=boot.js.map