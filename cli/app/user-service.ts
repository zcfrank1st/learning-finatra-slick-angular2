import {Http, Response, RequestOptions, Headers, HTTP_PROVIDERS} from 'angular2/http';
import {Inject, Component} from "angular2/core";
import 'rxjs/add/operator/map';
import {Observable} from 'rxjs/Observable';

export class UserService {
    posts = [];
    apiUrl = "http://localhost:9954";

    constructor(@Inject(Http) private http: Http) {
        // TODO: Use official Angular2 CORS support when merged (https://github.com/angular/angular/issues/4231).
        let _build = (<any> http)._backend._browserXHR.build;
        (<any> http)._backend._browserXHR.build = () => {
            let _xhr = _build();
            _xhr.withCredentials = true;
            return _xhr;
        };
    }

    login(username:string, password:string) {
        const headers = new Headers();
        headers.append("Authorization", UserService.encodeCredentials(username, password));

        this.http.post(`${this.apiUrl}/login`, '', {headers: headers})
            .map(res => res.json())
            .subscribe(_ => _);

        this.http.get(`${this.apiUrl}/post`)
            .map(res => res.json())
            .subscribe(people => console.log(people));
    }

    static encodeCredentials(username:string, password:string):string {
        const base64 = btoa(`${username}:${password}`);
        return `Basic ${base64}`;
    }
}
