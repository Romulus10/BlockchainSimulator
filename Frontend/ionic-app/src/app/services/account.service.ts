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
    const url = `${this.baseUrl}/client/account/create`;
    return this.http.post(url, {}).pipe(
      tap(res => console.log('account created', res))
    )
  }
}
