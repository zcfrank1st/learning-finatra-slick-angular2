import {Component} from 'angular2/core';
import {FieldForm} from "./fieldForm";

@Component({
    selector: 'my-app',
    directives: [FieldForm],
    template: `
        <h1>Angular2 App</h1>
        <field-form></field-form>
    `
})
export class AppComponent {

}
