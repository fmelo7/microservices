import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss']
})
export class CustomerComponent implements OnInit {

  result: any;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.http
      .get('http://localhost:2222/api/customers/v1')
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
