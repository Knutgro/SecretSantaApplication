import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IMember } from 'app/shared/model/member.model';
import { MemberService } from './member.service';

@Component({
  selector: 'jhi-member',
  templateUrl: './member.component.html'
})
export class MemberComponent implements OnInit, OnDestroy {
  members: IMember[];
  eventSubscriber: Subscription;

  constructor(protected memberService: MemberService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.memberService.query().subscribe((res: HttpResponse<IMember[]>) => {
      this.members = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInMembers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMember) {
    return item.id;
  }

  registerChangeInMembers() {
    this.eventSubscriber = this.eventManager.subscribe('memberListModification', () => this.loadAll());
  }
}
