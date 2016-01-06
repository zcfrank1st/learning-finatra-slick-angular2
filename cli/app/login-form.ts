import {Component, Inject} from "angular2/core";
import {UserService} from "./service/user-service";

@Component({
    templateUrl: 'app/login-form.html'
})
export class Login {
    userService:UserService;
    model = new LoginFormModel();

    constructor(@Inject(UserService) userService) {
        this.userService = userService;
    }

    onSubmit() {
        this.userService.login(this.model.username, this.model.password);
    }
}

class LoginFormModel {
    constructor(public username?:string,
                public password?:string) {
    }
}
