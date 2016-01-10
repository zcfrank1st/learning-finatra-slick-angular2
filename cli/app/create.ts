
import {Component} from "angular2/core";
import {PostService} from "./service/post-service";
import {Inject} from "angular2/core";

@Component({
    template: `
    <form (ngSubmit)="onSubmit()" #createPostForm="ngForm">
        <div class="form-group">
            <label for="titleField">Title</label>
            <input type="text" class="form-control" required
                   [(ngModel)]="model.title"
                   ngControl="titleField"  #titleField="ngForm">
            <div [hidden]="titleField.valid" class="text-danger">
                Title is required
            </div>
        </div>

        <button type="submit" class="btn btn-default"
                [disabled]="!createPostForm.form.valid">Create</button>
    </form>
    `
})
export class Create {
    model = new FormModel();
    private postService:PostService;

    constructor(@Inject(PostService) postService:PostService) {
        this.postService = postService;

    }

    onSubmit() {

    }
}

class FormModel {
    constructor(public title?:string) {

    }
}