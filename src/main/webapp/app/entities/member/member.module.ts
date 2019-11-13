import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecretSantaAppSharedModule } from 'app/shared/shared.module';
import { MemberComponent } from './member.component';
import { MemberDetailComponent } from './member-detail.component';
import { MemberUpdateComponent } from './member-update.component';
import { MemberDeletePopupComponent, MemberDeleteDialogComponent } from './member-delete-dialog.component';
import { memberRoute, memberPopupRoute } from './member.route';

const ENTITY_STATES = [...memberRoute, ...memberPopupRoute];

@NgModule({
  imports: [SecretSantaAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [MemberComponent, MemberDetailComponent, MemberUpdateComponent, MemberDeleteDialogComponent, MemberDeletePopupComponent],
  entryComponents: [MemberDeleteDialogComponent]
})
export class SecretSantaAppMemberModule {}
