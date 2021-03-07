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

    constructor(private router: Router) {}

    search () {
        this.router.navigate(["/shop", this.term]).then(() => window.location.reload())
    }
}
