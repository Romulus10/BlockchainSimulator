import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { BlockComponent } from './block/block.component';
import { TransactionProposalComponent } from './transaction-proposal/transaction-proposal.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
  ],
  declarations: [
      BlockComponent,
      TransactionProposalComponent,
  ],
  entryComponents: [
    BlockComponent,
    TransactionProposalComponent,
  ],
  exports: [
      BlockComponent,
      TransactionProposalComponent,
  ]
})
export class ComponentsModule {}

