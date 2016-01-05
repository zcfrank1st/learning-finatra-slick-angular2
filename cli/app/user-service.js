System.register(['angular2/http', "angular2/core", 'rxjs/add/operator/map'], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var __param = (this && this.__param) || function (paramIndex, decorator) {
        return function (target, key) { decorator(target, key, paramIndex); }
    };
    var http_1, core_1;
    var UserService;
    return {
        setters:[
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (_1) {}],
        execute: function() {
            UserService = (function () {
                function UserService(http) {
                    this.http = http;
                    this.posts = [];
                    this.apiUrl = "http://localhost:9954";
                    // TODO: Use official Angular2 CORS support when merged (https://github.com/angular/angular/issues/4231).
                    var _build = http._backend._browserXHR.build;
                    http._backend._browserXHR.build = function () {
                        var _xhr = _build();
                        _xhr.withCredentials = true;
                        return _xhr;
                    };
                }
                UserService.prototype.login = function (username, password) {
                    var headers = new http_1.Headers();
                    headers.append("Authorization", UserService.encodeCredentials(username, password));
                    this.http.post(this.apiUrl + "/login", '', { headers: headers })
                        .map(function (res) { return res.json(); })
                        .subscribe(function (_) { return _; });
                    this.http.get(this.apiUrl + "/post")
                        .map(function (res) { return res.json(); })
                        .subscribe(function (people) { return console.log(people); });
                };
                UserService.encodeCredentials = function (username, password) {
                    var base64 = btoa(username + ":" + password);
                    return "Basic " + base64;
                };
                UserService = __decorate([
                    __param(0, core_1.Inject(http_1.Http)), 
                    __metadata('design:paramtypes', [http_1.Http])
                ], UserService);
                return UserService;
            })();
            exports_1("UserService", UserService);
        }
    }
});
//# sourceMappingURL=user-service.js.map