import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-list',
  templateUrl: 'list.page.html',
  styleUrls: ['list.page.scss']
})
export class ListPage implements OnInit {
  private selectedItem: any;
  public accounts$: Observable<Account[]>;

  constructor(
    protected accountService: AccountService
  ) {
    this.accounts$ = accountService.listAccounts();
  }

  ngOnInit() {
  }
}
