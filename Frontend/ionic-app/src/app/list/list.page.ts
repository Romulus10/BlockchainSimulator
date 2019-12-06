import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AccountService } from '../services/account.service';
import { ToastController } from '@ionic/angular';
import { NodeAccount } from 'src/models/account';
import { NavController } from '@ionic/angular';

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
    public navCtrl: NavController,
  ) {
    this.accounts$ = accountService.listAccounts();
  }

  async onCreateAccountClick() {
    this.accountService.createAccount().subscribe();
    const toast = await this.toastCtrl.create({
      message: 'Account created!',
      duration: 1500,
      position: 'top',
    })
    toast.present();
    this.accountService.listAccounts();
    location.reload();
  }

  async onStakeButtonClick(account: NodeAccount) {
    const proposedStake: number = this.stakeProposal[account.address];
    if (proposedStake > account.balance) {
      this.openToast('Cannot enter a higher amount than your current balance!');
      return;
    }
    if (proposedStake <= 0) {
      this.openToast('Stake amount must be > 0.');
      return;
    }
    this.stakes[account.address] = proposedStake;
    this.accountService.stake(account, this.stakes[account.address]).subscribe(_ => {
      this.openToast('Stake successful!');
      return;
    });
    this.accountService.listAccounts();
    location.reload();
  }

  ngOnInit() {
  }

  async openToast(msg: string) {
    const toast = await this.toastCtrl.create({
      position: 'top',
      message: msg,
      duration: 3000,
    })
    await toast.present();
  }
}
