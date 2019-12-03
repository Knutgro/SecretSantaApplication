import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IWish } from 'app/shared/model/wish.model';
import { WishService } from './wish.service';

@Component({
  selector: 'jhi-wish',
  templateUrl: './wish.component.html'
})
export class WishComponent implements OnInit, OnDestroy {
  wishes: IWish[];
  eventSubscriber: Subscription;
  displayedColumns: string[] = ['name', 'event'];

  constructor(protected wishService: WishService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.wishService.query().subscribe((res: HttpResponse<IWish[]>) => {
      this.wishes = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInWishes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IWish) {
    return item.id;
  }

  registerChangeInWishes() {
    this.eventSubscriber = this.eventManager.subscribe('wishListModification', () => this.loadAll());
  }
}
