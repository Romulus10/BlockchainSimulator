import { Injectable } from '@angular/core';
import { Block } from 'src/models/block';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { tap, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BlockService {
  private readonly baseUrl: string = environment.baseUrl;

  constructor(
    protected http: HttpClient,
  ) { }

  public getAllBlocks(): Observable<Block[]> {
    const url = `${this.baseUrl}/client/block/list`;

    return this.http.get<string>(url).pipe(
      map(data => JSON.parse(data)),
      tap(parsedData => console.log('parsed data', parsedData))
    );
  }

  public isValidBlock(i: number): boolean {
    const url = `${this.baseUrl}/client/block/get_if_valid`;

    const result = this.http.get<string>(url).pipe(
      map(data => JSON.parse(data)),
      tap(parsedData => console.log('parsed data', parsedData))
    )

    if (result['invalid_block'] == i) {
      return false;
    } else {
      return true;
    }
  }
}
