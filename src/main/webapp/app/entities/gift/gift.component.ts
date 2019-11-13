import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IGift } from 'app/shared/model/gift.model';
import { GiftService } from './gift.service';

@Component({
  selector: 'jhi-gift',
  templateUrl: './gift.component.html'
})
export class GiftComponent implements OnInit, OnDestroy {
  gifts: IGift[];
  eventSubscriber: Subscription;

  constructor(protected giftService: GiftService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.giftService.query().subscribe((res: HttpResponse<IGift[]>) => {
      this.gifts = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInGifts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IGift) {
    return item.id;
  }

  registerChangeInGifts() {
    this.eventSubscriber = this.eventManager.subscribe('giftListModification', () => this.loadAll());
  }
}
