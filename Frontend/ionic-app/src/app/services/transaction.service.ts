import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { TransactionProposal } from 'src/models/transactionProposal';
import { tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { Transaction } from 'src/models/transaction';

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
    return this.http.post(url, proposal).pipe(
      tap(res => console.log('transaction created', res))
    )
  }

  listTransactions(): Observable<Transaction[]> {
    const url = `${this.baseUrl}/client/transaction/list`;
    return this.http.get<Transaction[]>(url).pipe(
      tap(res => console.log('transaction list', res))
    )
  }
}
