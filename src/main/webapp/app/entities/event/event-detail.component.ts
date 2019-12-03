import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvent } from 'app/shared/model/event.model';
import { Gift } from 'app/shared/model/gift.model';
import { Member } from 'app/shared/model/member.model';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';

import { IWish } from 'app/shared/model/wish.model';

@Component({
  selector: 'jhi-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.scss']
})
export class EventDetailComponent implements OnInit {
  event: IEvent;
  gift: Gift;
  member: Member;
  account: Account;
  recWishes: IWish[] = [];
  isOwner: boolean;
  panelOpenState = false;

  constructor(protected activatedRoute: ActivatedRoute, private accountService: AccountService) {}

  ngOnInit() {
    this.isOwner = false;
    this.accountService.identity().subscribe((account: Account) => {
      this.account = account;
    });

    this.activatedRoute.data.subscribe(({ event }) => {
      this.event = event;
    });

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

    if (this.event.wishes) {
      this.event.wishes.forEach(wish => {
        if (wish.member.id === this.gift.receivedGift.id) {
          this.recWishes.push(wish);
        }
      });
    }
    if (this.event.owned.id === this.member.id) {
      this.isOwner = true;
    }
  }

  previousState() {
    window.history.back();
  }

  goToLink(url: string) {
    window.open(url, '_blank');
  }
}
