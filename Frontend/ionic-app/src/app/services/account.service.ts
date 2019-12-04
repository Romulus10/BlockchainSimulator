import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private readonly baseUrl: string = environment.baseUrl;

  constructor(
    protected http: HttpClient,
  ) { }

  createAccount(): Observable<any> {
    const url = `${this.baseUrl}/client/account_create/create`;
    return this.http.get(url).pipe(
      tap(res => console.log('account created', res))
    )
  }

  listAccounts(): Observable<Account[]> {
    const url = `${this.baseUrl}/client/account/list`;
    return this.http.get<Account[]>(url).pipe(
      tap(res => console.log('account list', res))
    )

  }
}
