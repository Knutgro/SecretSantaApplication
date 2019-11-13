import { IMember } from 'app/shared/model/member.model';
import { IEvent } from 'app/shared/model/event.model';

export interface IGift {
  id?: number;
  giftedGift?: IMember;
  receivedGift?: IMember;
  event?: IEvent;
}

export class Gift implements IGift {
  constructor(public id?: number, public giftedGift?: IMember, public receivedGift?: IMember, public event?: IEvent) {}
}
