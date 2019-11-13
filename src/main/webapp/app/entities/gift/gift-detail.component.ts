import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGift } from 'app/shared/model/gift.model';

@Component({
  selector: 'jhi-gift-detail',
  templateUrl: './gift-detail.component.html'
})
export class GiftDetailComponent implements OnInit {
  gift: IGift;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ gift }) => {
      this.gift = gift;
    });
  }

  previousState() {
    window.history.back();
  }
}
