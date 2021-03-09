import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/Product';
import { ProductsService } from 'src/app/services/products.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
    public term : string
    public products : Product[]

    constructor(router : Router, route : ActivatedRoute, private productsService : ProductsService) { 
        this.term = route.snapshot.paramMap.get('term') || ""
    }

    ngOnInit(): void {
        this.productsService.getProducts().subscribe((products : Product[]) => {
            this.products = products

            for (let product of this.products) {
                product.imageUrl = product.image ? 'data:image/jpeg;base64,' + product.image :
                "../../../assets/static/images/product-placeholder.png";
                
            }
        }, (error: ErrorEvent) => {
        })
    }
}
