import { Component, OnInit } from '@angular/core';
import { faMapMarkerAlt, faPhoneAlt} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {
    marker = faMapMarkerAlt;
    phone = faPhoneAlt

    constructor() { }

    ngOnInit(): void {
    }
}
