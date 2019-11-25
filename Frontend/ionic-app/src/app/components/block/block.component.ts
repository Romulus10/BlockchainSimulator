import { Component, OnInit, Input } from '@angular/core';
import { BlockService } from 'src/app/services/block.service';
import { Block } from 'src/models/block';

@Component({
  selector: 'app-block',
  templateUrl: './block.component.html',
  styleUrls: ['./block.component.scss'],
})
export class BlockComponent implements OnInit {
  @Input() block: Block;

  constructor(
  ) { }

  ngOnInit() {
  }

}
