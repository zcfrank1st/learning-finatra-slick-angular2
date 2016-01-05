import {Component, Inject} from "angular2/core";
import {UserService} from "./user-service";

@Component({
    selector: 'field-form',
    templateUrl: 'app/login-form.component.html'
})
export class FieldForm {
    userService:UserService;
    model = new LoginFormModel();

    constructor(@Inject(UserService) userService) {
        this.userService = userService;
    }

    onSubmit() {
        this.userService.login(this.model.username, this.model.password)
    }
}

class LoginFormModel {
    constructor(public username?:string,
                public password?:string) {
    }
}
