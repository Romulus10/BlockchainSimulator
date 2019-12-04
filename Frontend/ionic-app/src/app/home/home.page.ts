import { Component } from '@angular/core';
import { BlockService } from '../services/block.service';
import { Observable } from 'rxjs';
import { Block } from 'src/models/block';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  blocks: Observable<Block[]>;

  constructor(
    protected blockService: BlockService,
    protected accountService: AccountService,
  ) {
    this.blocks = this.blockService.getAllBlocks();
  }

  onCreateAccountClick() {
    this.accountService.createAccount().subscribe();
  }

  onAddButtonClick() {

  }

}
