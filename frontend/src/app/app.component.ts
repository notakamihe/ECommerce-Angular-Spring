import { Component } from '@angular/core';
import { Router } from '@angular/router';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
    title = 'frontend';
    public term : string
    public isTokenThere : boolean

    constructor(private router: Router) {
        console.log("Token:  " + localStorage.getItem('token'));
        this.isTokenThere = localStorage.getItem('token') != null
    }

    search () {
        this.router.navigate(["/shop", this.term]).then(() => window.location.reload())
    }
}
