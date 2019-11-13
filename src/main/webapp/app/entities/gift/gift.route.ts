import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Gift } from 'app/shared/model/gift.model';
import { GiftService } from './gift.service';
import { GiftComponent } from './gift.component';
import { GiftDetailComponent } from './gift-detail.component';
import { GiftUpdateComponent } from './gift-update.component';
import { GiftDeletePopupComponent } from './gift-delete-dialog.component';
import { IGift } from 'app/shared/model/gift.model';

@Injectable({ providedIn: 'root' })
export class GiftResolve implements Resolve<IGift> {
  constructor(private service: GiftService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGift> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((gift: HttpResponse<Gift>) => gift.body));
    }
    return of(new Gift());
  }
}

export const giftRoute: Routes = [
  {
    path: '',
    component: GiftComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'secretSantaApp.gift.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GiftDetailComponent,
    resolve: {
      gift: GiftResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'secretSantaApp.gift.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GiftUpdateComponent,
    resolve: {
      gift: GiftResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'secretSantaApp.gift.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GiftUpdateComponent,
    resolve: {
      gift: GiftResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'secretSantaApp.gift.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const giftPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: GiftDeletePopupComponent,
    resolve: {
      gift: GiftResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'secretSantaApp.gift.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
