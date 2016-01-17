import {Http, Response, RequestOptions, Headers, HTTP_PROVIDERS} from 'angular2/http';
import {Inject, Component} from "angular2/core";
import 'rxjs/add/operator/map';
import {Observable} from 'rxjs/Observable';
import {Post} from "./dto/post";

export class PostService {
    apiUrl = "http://localhost:9954";
    headers = {
        headers: new Headers({
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        })
    };

    constructor(@Inject(Http) private http:Http) {
        // TODO: Use official Angular2 CORS support when merged (https://github.com/angular/angular/issues/4231).
        // workaround
        let _build = (<any> http)._backend._browserXHR.build;
        (<any> http)._backend._browserXHR.build = () => {
            let _xhr = _build();
            _xhr.withCredentials = true;
            return _xhr;
        };
    }

    getAll():Observable<Response> {
        return this.http.get(`${this.apiUrl}/post?limit=100`);
    }

    create(post:Post):Observable<Response> {
        return this.http.post(`${this.apiUrl}/post`, JSON.stringify(post), this.headers);
    }
}