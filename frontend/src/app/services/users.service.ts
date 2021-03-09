import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Token } from '../models/Token';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})

export class UsersService {
    constructor(private http : HttpClient) { }

    register (username : string, password : string, email : string, name : string, address : string, 
        phone : string) : Observable<Token> {
        return this.http.post<Token>(`${environment.API_URL}/register`, {
            username,
            password,
            email,
            name,
            address,
            phone
        })
    }

    login (username : string, password : string) : Observable<Token> {
        return this.http.post<Token>(`${environment.API_URL}/login`, {
            username,
            password,
        })
    }

    createToken (username : string) : Observable<Token> {
        return this.http.post<Token>(`${environment.API_URL}/api/create-token`, {
            username
        });
    }

    getUsers () : Observable<User[]> {
        return this.http.get<User[]>(`${environment.API_URL}/api/users`);
    }

    getUser (id : string) : Observable<User> {
        return this.http.get<User>(`${environment.API_URL}/api/users/${id}`);
    }

    getUserByToken () : Observable<User> {
        return this.http.get<User>(`${environment.API_URL}/user`, {
            headers: new HttpHeaders({
              'Authorization': 'Bearer ' + localStorage.getItem('token'),
              'Content-Type': 'application/json'
            })
        });
    }

    updateUser (id : string, username : string, password : string, email : string, name : string, address : string, phone : string) : Observable<User> {
        return this.http.put<User>(`${environment.API_URL}/api/users/${id}`, {
            username,
            password,
            email,
            name,
            address,
            phone 
        })
    }

    deleteUser (id : string) : Observable<any> {
        return this.http.delete(`${environment.API_URL}/api/users/${id}`);
    }
}
