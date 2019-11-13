import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Member } from 'app/shared/model/member.model';
import { MemberService } from './member.service';
import { MemberComponent } from './member.component';
import { MemberDetailComponent } from './member-detail.component';
import { MemberUpdateComponent } from './member-update.component';
import { MemberDeletePopupComponent } from './member-delete-dialog.component';
import { IMember } from 'app/shared/model/member.model';

@Injectable({ providedIn: 'root' })
export class MemberResolve implements Resolve<IMember> {
  constructor(private service: MemberService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMember> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((member: HttpResponse<Member>) => member.body));
    }
    return of(new Member());
  }
}

export const memberRoute: Routes = [
  {
    path: '',
    component: MemberComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'secretSantaApp.member.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MemberDetailComponent,
    resolve: {
      member: MemberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'secretSantaApp.member.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MemberUpdateComponent,
    resolve: {
      member: MemberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'secretSantaApp.member.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MemberUpdateComponent,
    resolve: {
      member: MemberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'secretSantaApp.member.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const memberPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MemberDeletePopupComponent,
    resolve: {
      member: MemberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'secretSantaApp.member.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
