import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { TransactionProposal } from 'src/models/transactionProposal';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private readonly baseUrl: string = environment.baseUrl;

  constructor(
    protected http: HttpClient,
  ) { }

  createTransaction(proposal: TransactionProposal) {
    const url = `${this.baseUrl}/client/transaction/create`;
    return this.http.post(url, proposal);
  }
}
