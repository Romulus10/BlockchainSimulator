import { Injectable } from '@angular/core';
import { Block } from 'src/models/block';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';
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
    // TODO: change to rest API call
    const url = `${this.baseUrl}/client/block/list`;

    return this.http.get<Block[]>(url).pipe(
      tap(blocks => console.log('blocks', blocks))
    );
  }
}
