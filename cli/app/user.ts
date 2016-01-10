
import {Component, Inject} from "angular2/core";
import {UserService} from "./service/user-service";

@Component({
    template:
     `<ul>
        <li *ngFor="#user of users">
            {{ user.username }}
        </li>
     </ul>`
})
export class Profile {
    private userService:UserService;
    users:User[] = [];

    constructor(@Inject(UserService) userService:UserService) {
        this.userService = userService;

        this.userService.getAll()
            .map(response => response.json())
            .subscribe(
                users => this.users = users,
                () => {},
                () => {}
            );
    }
}

export class User {
    constructor(username:string, password:string) {

    }
}