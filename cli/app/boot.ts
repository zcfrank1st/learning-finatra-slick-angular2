import {bootstrap}    from 'angular2/platform/browser'
import {AppComponent} from './app'
import {HTTP_PROVIDERS} from "angular2/http";
import {ROUTER_PROVIDERS, ROUTER_DIRECTIVES} from 'angular2/router';
import {PostService} from "./service/post-service";
import {UserService}  from "./service/user-service";
import {provide} from "angular2/core";

bootstrap(AppComponent, [
    UserService,
    PostService,
    HTTP_PROVIDERS,
    ROUTER_PROVIDERS,
    ROUTER_DIRECTIVES
]);
