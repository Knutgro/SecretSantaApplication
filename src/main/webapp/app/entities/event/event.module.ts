import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecretSantaAppSharedModule } from 'app/shared/shared.module';
import { EventComponent } from './event.component';
import { EventDetailComponent } from './event-detail.component';
import { EventUpdateComponent } from './event-update.component';
import { EventDeletePopupComponent, EventDeleteDialogComponent } from './event-delete-dialog.component';
import { eventRoute, eventPopupRoute } from './event.route';
import { MatCardModule } from '@angular/material/card';
import { FlexModule } from '@angular/flex-layout';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatListModule } from '@angular/material/list';

const ENTITY_STATES = [...eventRoute, ...eventPopupRoute];

@NgModule({
  imports: [
    SecretSantaAppSharedModule,
    RouterModule.forChild(ENTITY_STATES),
    MatCardModule,
    FlexModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatListModule
  ],
  declarations: [EventComponent, EventDetailComponent, EventUpdateComponent, EventDeleteDialogComponent, EventDeletePopupComponent],
  entryComponents: [EventDeleteDialogComponent]
})
export class SecretSantaAppEventModule {}
