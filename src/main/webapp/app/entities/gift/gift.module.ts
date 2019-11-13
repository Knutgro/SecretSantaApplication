import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecretSantaAppSharedModule } from 'app/shared/shared.module';
import { GiftComponent } from './gift.component';
import { GiftDetailComponent } from './gift-detail.component';
import { GiftUpdateComponent } from './gift-update.component';
import { GiftDeletePopupComponent, GiftDeleteDialogComponent } from './gift-delete-dialog.component';
import { giftRoute, giftPopupRoute } from './gift.route';

const ENTITY_STATES = [...giftRoute, ...giftPopupRoute];

@NgModule({
  imports: [SecretSantaAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [GiftComponent, GiftDetailComponent, GiftUpdateComponent, GiftDeleteDialogComponent, GiftDeletePopupComponent],
  entryComponents: [GiftDeleteDialogComponent]
})
export class SecretSantaAppGiftModule {}
