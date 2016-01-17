import {Component, Inject} from "angular2/core";
import {UserService} from "./../../service/user-service";
import {Location, Router} from "angular2/router";
import {Response} from "angular2/http";
import {NgIf} from "angular2/common";

@Component({
    templateUrl: 'app/components/login/login-form.html',
    directives: [NgIf],
    styleUrls: ['app/components/login/login.css']
})
export class Login {
    private userService:UserService;
    private model = new LoginFormModel();
    private router:Router;
    private authError:boolean = false;

    constructor(userService:UserService, router:Router) {
        this.userService = userService;
        this.router = router;
    }

    logout() {
        this.userService.logout()
            .subscribe(
                data => this.router.navigate(['Home']),
                err => console.log(err));
    }

    login() {
        this.authError = false;
        this.userService.login(this.model.username, this.model.password)
            .subscribe(
                data => this.router.navigate(['Home']),
                err => {
                    if (err.status === 401) {
                        this.authError = true;
                    }
                }
            );
    }
}

class LoginFormModel {
    constructor(public username?:string, public password?:string) {}
}
