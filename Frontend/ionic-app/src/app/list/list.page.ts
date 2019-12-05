import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AccountService } from '../services/account.service';
import { ToastController } from '@ionic/angular';

@Component({
  selector: 'app-list',
  templateUrl: 'list.page.html',
  styleUrls: ['list.page.scss']
})
export class ListPage implements OnInit {
  private selectedItem: any;
  public accounts$: Observable<Account[]>;

  constructor(
    protected accountService: AccountService,
    protected toastCtrl: ToastController,
  ) {
    this.accounts$ = accountService.listAccounts();
  }

  async onCreateAccountClick() {
    this.accountService.createAccount().subscribe();
    const toast = await this.toastCtrl.create({
      message: 'Account created, refresh the page to see all accounts!',
      duration: 1500,
      position: 'top',
    })
    toast.present();
  }

  ngOnInit() {
  }
}
