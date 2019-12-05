import { Component, OnInit } from '@angular/core';
import { TransactionProposal } from 'src/models/transactionProposal';
import { AccountService } from 'src/app/services/account.service';
import { Observable } from 'rxjs';
import { NodeAccount } from 'src/models/account';
import { PopoverController } from '@ionic/angular';

@Component({
  selector: 'app-transaction-proposal',
  templateUrl: './transaction-proposal.component.html',
  styleUrls: ['./transaction-proposal.component.scss'],
})
export class TransactionProposalComponent implements OnInit {
  proposal: TransactionProposal = {
    fr: '',
    to: '',
    data: '',
  }

  allAccounts$: Observable<NodeAccount[]>

  constructor(
    protected accountService: AccountService,
    protected popoverCtrl: PopoverController,
  ) { 
    this.allAccounts$ = this.accountService.listAccounts();
  }

  ngOnInit() {}

  onSubmit() {
    this.popoverCtrl.dismiss(this.proposal);
  }

}
