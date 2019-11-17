import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IEvent } from 'app/shared/model/event.model';
import { EventService } from './event.service';
import { Member } from 'app/shared/model/member.model';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

@Component({
  selector: 'jhi-event',
  templateUrl: './event.component.html'
})
export class EventComponent implements OnInit, OnDestroy {
  events: IEvent[];
  eventSubscriber: Subscription;
  account: Account;
  member: Member;

  constructor(protected eventService: EventService, protected eventManager: JhiEventManager, private accountService: AccountService) {}

  loadAll() {
    this.eventService.query().subscribe((res: HttpResponse<IEvent[]>) => {
      this.events = res.body;
    });
  }

  ngOnInit() {
    this.accountService.identity().subscribe((account: Account) => {
      this.account = account;
    });
    this.loadAll();
    this.registerChangeInEvents();
    console.log(this.account);
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEvent) {
    return item.id;
  }

  registerChangeInEvents() {
    this.eventSubscriber = this.eventManager.subscribe('eventListModification', () => this.loadAll());
  }
}
