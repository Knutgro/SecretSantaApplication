import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IEvent } from 'app/shared/model/event.model';
import { EventService } from './event.service';

@Component({
  selector: 'jhi-event',
  templateUrl: './event.component.html'
})
export class EventComponent implements OnInit, OnDestroy {
  events: IEvent[];
  eventSubscriber: Subscription;

  constructor(protected eventService: EventService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.eventService.query().subscribe((res: HttpResponse<IEvent[]>) => {
      this.events = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInEvents();
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
