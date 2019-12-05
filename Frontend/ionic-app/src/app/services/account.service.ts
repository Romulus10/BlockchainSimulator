import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { NodeAccount } from 'src/models/account';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private readonly baseUrl: string = environment.baseUrl;

  constructor(
    protected http: HttpClient,
  ) { }

  createAccount(): Observable<any> {
    const url = `${this.baseUrl}/client/account/create`;
    return this.http.post(url, {}).pipe(
      tap(res => console.log('account created', res))
    )
  }

  stake(account: NodeAccount, amount: number) {
    const url = `${this.baseUrl}/client/account/stake/${account.address}/${amount}`;
    return this.http.get(url).pipe(
      tap(res => console.log('staked successful', res))
    )

  }

  listAccounts(): Observable<NodeAccount[]> {
    const url = `${this.baseUrl}/client/account/list`;
    return this.http.get<NodeAccount[]>(url).pipe(
      tap(res => console.log('account list', res))
    )

  }
}
