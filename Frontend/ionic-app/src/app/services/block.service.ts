import { Injectable } from '@angular/core';
import { Block } from 'src/models/block';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { tap, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { NavController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class BlockService {
  private readonly baseUrl: string = environment.baseUrl;

  constructor(
    protected http: HttpClient,
    public navCtrl: NavController,
  ) { }

  public getAllBlocks(): Observable<Block[]> {
    const url = `${this.baseUrl}/client/block/list`;

    return this.http.get<string>(url).pipe(
      map(data => JSON.parse(data)),
      tap(parsedData => console.log('parsed data', parsedData))
    );
  }
}
