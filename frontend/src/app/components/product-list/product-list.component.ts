import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
    public term : string

    constructor(router : Router, route : ActivatedRoute) { 
        this.term = route.snapshot.paramMap.get('term') || ""
    }

    ngOnInit(): void {
    }
}
