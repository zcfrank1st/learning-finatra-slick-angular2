System.register(["angular2/core", "./user-service"], function(exports_1) {
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
    var core_1, user_service_1;
    var FieldForm, LoginFormModel;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (user_service_1_1) {
                user_service_1 = user_service_1_1;
            }],
        execute: function() {
            FieldForm = (function () {
                function FieldForm(userService) {
                    this.model = new LoginFormModel();
                    this.userService = userService;
                }
                FieldForm.prototype.onSubmit = function () {
                    this.userService.login(this.model.username, this.model.password);
                };
                FieldForm = __decorate([
                    core_1.Component({
                        selector: 'field-form',
                        templateUrl: 'app/login-form.component.html'
                    }),
                    __param(0, core_1.Inject(user_service_1.UserService)), 
                    __metadata('design:paramtypes', [Object])
                ], FieldForm);
                return FieldForm;
            })();
            exports_1("FieldForm", FieldForm);
            LoginFormModel = (function () {
                function LoginFormModel(username, password) {
                    this.username = username;
                    this.password = password;
                }
                return LoginFormModel;
            })();
        }
    }
});
//# sourceMappingURL=fieldForm.js.map