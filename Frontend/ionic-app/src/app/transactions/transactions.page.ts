import { Component, OnInit } from '@angular/core';
import { PopoverController } from '@ionic/angular';
import { TransactionProposalComponent } from '../components/transaction-proposal/transaction-proposal.component';
import { OverlayEventDetail } from '@ionic/core';
import { TransactionProposal } from 'src/models/transactionProposal';
import { TransactionService } from '../services/transaction.service';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.page.html',
  styleUrls: ['./transactions.page.scss'],
})
export class TransactionsPage implements OnInit {

  constructor(
    protected popoverCtrl: PopoverController,
    protected transactionService: TransactionService,
  ) { }

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
    })
  }

}
