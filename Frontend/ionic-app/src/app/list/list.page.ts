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
  public stakeProposal: Map<string, number> = new Map<string, number>();

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
    const proposedStake: number = this.stakeProposal[account.address];
    if (proposedStake > account.balance) {
      this.openToast('Cannot enter a higher amount than your current balance!');
      return;
    }
    this.stakes[account.address] = proposedStake;
    this.accountService.stake(account, this.stakes[account.address]).subscribe(_ => {
      this.openToast('Stake successful!');
      return;
    });
  }

  ngOnInit() {
  }

  async openToast(msg: string) {
    const toast = await this.toastCtrl.create({
      position: 'top',
      message: msg,
    })
    await toast.present();
  }
}
