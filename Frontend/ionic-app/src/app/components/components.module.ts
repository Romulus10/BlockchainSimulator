import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { BlockComponent } from './block/block.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
  ],
  declarations: [
      BlockComponent,
  ],
  exports: [
      BlockComponent,
  ]
})
export class ComponentsModule {}

