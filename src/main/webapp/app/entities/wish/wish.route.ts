import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Wish } from 'app/shared/model/wish.model';
import { WishService } from './wish.service';
import { WishComponent } from './wish.component';
import { WishDetailComponent } from './wish-detail.component';
import { WishUpdateComponent } from './wish-update.component';
import { WishDeletePopupComponent } from './wish-delete-dialog.component';
import { IWish } from 'app/shared/model/wish.model';

@Injectable({ providedIn: 'root' })
export class WishResolve implements Resolve<IWish> {
  constructor(private service: WishService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWish> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((wish: HttpResponse<Wish>) => wish.body));
    }
    return of(new Wish());
  }
}

export const wishRoute: Routes = [
  {
    path: '',
    component: WishComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'secretSantaApp.wish.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: WishDetailComponent,
    resolve: {
      wish: WishResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'secretSantaApp.wish.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: WishUpdateComponent,
    resolve: {
      wish: WishResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'secretSantaApp.wish.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: WishUpdateComponent,
    resolve: {
      wish: WishResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'secretSantaApp.wish.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const wishPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: WishDeletePopupComponent,
    resolve: {
      wish: WishResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'secretSantaApp.wish.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
