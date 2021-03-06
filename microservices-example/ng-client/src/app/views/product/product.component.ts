import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {

  result: Object;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.http
      .get('http://localhost:8080/api/v1/products')
      .subscribe(
      data => {
        console.log(data);
        this.result = data;
      },
      err => {
        console.log(err);
        // TODO send message to alert messages on view
      });
  }

}
