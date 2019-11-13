import { Moment } from 'moment';
import { IGift } from 'app/shared/model/gift.model';
import { IWish } from 'app/shared/model/wish.model';
import { IMember } from 'app/shared/model/member.model';

export interface IEvent {
  id?: number;
  name?: string;
  maxLimit?: number;
  minLimit?: number;
  dateCreated?: Moment;
  dateExpired?: Moment;
  owner?: number;
  gifts?: IGift[];
  wishes?: IWish[];
  members?: IMember[];
  owned?: IMember;
}

export class Event implements IEvent {
  constructor(
    public id?: number,
    public name?: string,
    public maxLimit?: number,
    public minLimit?: number,
    public dateCreated?: Moment,
    public dateExpired?: Moment,
    public owner?: number,
    public gifts?: IGift[],
    public wishes?: IWish[],
    public members?: IMember[],
    public owned?: IMember
  ) {}
}
