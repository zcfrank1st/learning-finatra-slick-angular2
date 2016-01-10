import {Inject, Component} from 'angular2/core';
import {Login} from "./login-form";
import {RouteConfig, Location, ROUTER_DIRECTIVES, ROUTER_PROVIDERS} from "angular2/router";
import {Home} from "./home";
import {Profile} from "./user";
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
    private location:Location;

    constructor(@Inject(UserService) userService:UserService,
                @Inject(Location) location:Location) {
        this.userService = userService;
        this.location = location;
    }

    logout() {
        this.userService.logout()
            .subscribe(
                () => {},
                () => {},
                () => {
                    this.location.go("/home");
                }
            );
    }
}
