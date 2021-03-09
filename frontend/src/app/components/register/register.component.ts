import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Token } from 'src/app/models/Token';
import { User } from 'src/app/models/User';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
    public username : string;
    public password : string;
    public passwordConfirm : string;
    public email : string;
    public address : string;
    public phone : string;
    public name : string;

    public error : string

    constructor(private usersService : UsersService, private router : Router) { }

    ngOnInit(): void {
        if (localStorage.getItem('token')) {
            this.router.navigateByUrl('/account')
        }
    }

    register () {
        const emailRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*/

        this.error = ''

        if (!this.email || !this.email.match(emailRegex)) {
            this.error = "Email is not valid"
            return
        }

        if (this.password != this.passwordConfirm) {
            this.error = "Passwords do not match"
            return
        }

        this.usersService.register(
            this.username, this.password, this.email, this.email, this.address, this.phone).subscribe((token : Token) => {
                localStorage.setItem('token', token.token);
                this.router.navigateByUrl('/account').then(() => window.location.reload())
            }, (error : ErrorEvent) => {
                this.error = error.error
            })
    }
}
