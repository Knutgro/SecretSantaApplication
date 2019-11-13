import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'wish',
        loadChildren: () => import('./wish/wish.module').then(m => m.SecretSantaAppWishModule)
      },
      {
        path: 'member',
        loadChildren: () => import('./member/member.module').then(m => m.SecretSantaAppMemberModule)
      },
      {
        path: 'event',
        loadChildren: () => import('./event/event.module').then(m => m.SecretSantaAppEventModule)
      },
      {
        path: 'gift',
        loadChildren: () => import('./gift/gift.module').then(m => m.SecretSantaAppGiftModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class SecretSantaAppEntityModule {}
