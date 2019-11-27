import { Injectable } from '@angular/core';
import { Block } from 'src/models/block';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BlockService {
  defaultBlocks: Block[] = [
    {
      hash: 'randomHashPopulatedFromAPI1',
      data: 'transactionData1'
    },

    {
      hash: 'randomHashPopulatedFromAPI2',
      data: 'transactionData2'
    },

    {
      hash: 'randomHashPopulatedFromAPI3',
      data: 'transactionData3'
    }
  ];

  constructor(
    protected http: HttpClient,
  ) { }

  public getAllBlocks(): Observable<Block[]> {
    // TODO: change to rest API call
    return of(this.defaultBlocks);
  }
}
