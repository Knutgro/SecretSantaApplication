import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvent } from 'app/shared/model/event.model';
import { Gift } from 'app/shared/model/gift.model';
import { Member } from 'app/shared/model/member.model';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-event-detail',
  templateUrl: './event-detail.component.html'
})
export class EventDetailComponent implements OnInit {
  event: IEvent;
  gift: Gift;
  member: Member;
  account: Account;

  constructor(protected activatedRoute: ActivatedRoute, private accountService: AccountService) {}

  ngOnInit() {
    this.accountService.identity().subscribe((account: Account) => {
      this.account = account;
    });

    this.activatedRoute.data.subscribe(({ event }) => {
      this.event = event;
    });

    console.log(this.event);

    this.event.members.forEach(member => {
      if (member.user.email === this.account.email) {
        this.member = member;
      }
    });

    this.event.gifts.forEach(gift => {
      if (gift.giftedGift.id === this.member.id) {
        this.gift = gift;
      }
    });
  }

  previousState() {
    window.history.back();
  }
}
