import { IMember } from 'app/shared/model/member.model';
import { IEvent } from 'app/shared/model/event.model';

export interface IWish {
  id?: number;
  name?: string;
  url?: string;
  member?: IMember;
  event?: IEvent;
}

export class Wish implements IWish {
  constructor(public id?: number, public name?: string, public url?: string, public member?: IMember, public event?: IEvent) {}
}
