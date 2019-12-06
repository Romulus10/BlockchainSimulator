import { Component, OnInit } from '@angular/core';
import { PopoverController } from '@ionic/angular';
import { TransactionProposalComponent } from '../components/transaction-proposal/transaction-proposal.component';
import { OverlayEventDetail } from '@ionic/core';
import { TransactionProposal } from 'src/models/transactionProposal';
import { TransactionService } from '../services/transaction.service';
import { ToastController } from '@ionic/angular';
import { Observable } from 'rxjs';
import { Transaction } from 'src/models/transaction';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.page.html',
  styleUrls: ['./transactions.page.scss'],
})
export class TransactionsPage implements OnInit {
  public transactions$: Observable<Transaction[]>;

  constructor(
    protected popoverCtrl: PopoverController,
    protected transactionService: TransactionService,
    protected toastCtrl: ToastController,
    public navCtrl: NavController,
  ) {
    this.transactions$ = transactionService.listTransactions();
  }

  ngOnInit() {
  }

  async onCreateNewTransactionButtonClick() {
    const popover = await this.popoverCtrl.create({
      component: TransactionProposalComponent
    });
    popover.present();
    popover.onDidDismiss().then((detail: OverlayEventDetail<TransactionProposal>) => {
      console.log(detail);
      this.transactionService.createTransaction(detail.data).subscribe();
      this.transactions$ = this.transactionService.listTransactions();
      this.openToast("Transaction sent. Refresh the page.");
    })
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
