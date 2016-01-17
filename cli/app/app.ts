import {Inject, Component} from 'angular2/core';
import {Login} from "./components/login/login-form";
import {RouteConfig, Router, ROUTER_DIRECTIVES, ROUTER_PROVIDERS} from "angular2/router";
import {Home} from "./components/home/home";
import {Profile} from "./components/profile/user";
import {UserService} from "./service/user-service";
import {Create} from "./create";

@Component({
    selector: 'my-app',
    directives: [Login, ROUTER_DIRECTIVES],
    template: `
        <h3>zinternetz</h3>
        <nav>
            <a [routerLink]="['Home']">Home</a>
            <a [routerLink]="['Profile']">Profile</a>
            <a [routerLink]="['Login']">Login</a>
            <a [routerLink]="['Create']">Create</a>
        </nav>
        <router-outlet></router-outlet>
    `
})
@RouteConfig([
    {path: '/home',  name: 'Home',  component: Home},
    {path: '/login', name: 'Login', component: Login},
    {path: '/profile', name: 'Profile', component: Profile},
    {path: '/create', name: 'Create', component: Create},
])
export class AppComponent {
    private userService:UserService;
    private router:Router;

    constructor(userService:UserService,
                router: Router) {
        this.userService = userService;
        this.router = router;
    }

    logout() {
        this.userService.logout()
            .subscribe(
                () => {},
                () => {},
                () => this.router.navigate(['Home']));
    }
}
