import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecretSantaAppSharedModule } from 'app/shared/shared.module';
import { WishComponent } from './wish.component';
import { WishDetailComponent } from './wish-detail.component';
import { WishUpdateComponent } from './wish-update.component';
import { WishDeletePopupComponent, WishDeleteDialogComponent } from './wish-delete-dialog.component';
import { wishRoute, wishPopupRoute } from './wish.route';
import { MatTableModule } from '@angular/material/table';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatButtonModule } from '@angular/material/button';

const ENTITY_STATES = [...wishRoute, ...wishPopupRoute];

@NgModule({
  imports: [SecretSantaAppSharedModule, RouterModule.forChild(ENTITY_STATES), MatTableModule, MatButtonToggleModule, MatButtonModule],
  declarations: [WishComponent, WishDetailComponent, WishUpdateComponent, WishDeleteDialogComponent, WishDeletePopupComponent],
  entryComponents: [WishDeleteDialogComponent]
})
export class SecretSantaAppWishModule {}
