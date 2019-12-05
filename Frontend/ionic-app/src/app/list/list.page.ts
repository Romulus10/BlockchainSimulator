import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AccountService } from '../services/account.service';
import { ToastController } from '@ionic/angular';
import { NodeAccount } from 'src/models/account';

@Component({
  selector: 'app-list',
  templateUrl: 'list.page.html',
  styleUrls: ['list.page.scss']
})
export class ListPage implements OnInit {
  private selectedItem: any;
  public accounts$: Observable<NodeAccount[]>;
  public stakes: Map<string, number> = new Map<string, number>();

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

  async onStakeButtonClick(account: NodeAccount) {
    console.log(this.stakes)
    this.accountService.stake(account, this.stakes[account.address]).subscribe();
  }

  ngOnInit() {
  }
}
