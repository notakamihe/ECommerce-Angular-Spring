import { Component, OnInit } from '@angular/core';
import { faCaretUp, faCaretDown } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
    caretUp = faCaretUp;
    caretDown = faCaretDown;

    constructor() { }

    ngOnInit(): void {
    }
}
