import {Inject, Component} from 'angular2/core';
import {Login} from "./login-form";
import {RouteConfig, Location, ROUTER_DIRECTIVES, ROUTER_PROVIDERS} from "angular2/router";
import {Home} from "./home";
import {Users} from "./user";
import {UserService} from "./service/user-service";

@Component({
    selector: 'my-app',
    directives: [Login, ROUTER_DIRECTIVES],
    template: `
        <h1>Angular2 App</h1>

        <nav>
            <a [routerLink]="['Home']">Home</a>
            <a [routerLink]="['Login']">Login</a>
            <a [routerLink]="['Users']">Users</a>
            <a (click)="logout()">Logout</a>
        </nav>
        <router-outlet></router-outlet>
    `
})
@RouteConfig([
    {path: '/home',  name: 'Home',  component: Home},
    {path: '/login', name: 'Login', component: Login},
    {path: '/users', name: 'Users', component: Users},
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
