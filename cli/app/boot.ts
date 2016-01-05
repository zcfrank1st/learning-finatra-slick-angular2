import {bootstrap}    from 'angular2/platform/browser'
import {AppComponent} from './app-component'
import {UserService}  from "./user-service";
import {HTTP_PROVIDERS} from "angular2/http";

bootstrap(AppComponent, [UserService, HTTP_PROVIDERS]);