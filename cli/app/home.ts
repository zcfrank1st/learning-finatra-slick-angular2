import {Component, Inject} from "angular2/core";
import {PostService} from "./service/post-service";
import {Post} from "./service/dto/post";
import {Location, ROUTER_DIRECTIVES, ROUTER_PROVIDERS} from "angular2/router";


@Component({
    template: `<ul>
        <li *ngFor="#post of posts">
            {{ post.title }}
        </li>
    </ul>`
})
export class Home {
    private postService:PostService;
    posts:Post[] = [];
    private location:Location;

    constructor(@Inject(PostService) postService:PostService,
                @Inject(Location) location:Location) {
        this.postService = postService;
        this.location = location;

        this.postService.getAll()
            .map(response => response.json())
            .subscribe(
                posts => this.posts = posts,
                () => {},
                () => {}
            );
    }
}
