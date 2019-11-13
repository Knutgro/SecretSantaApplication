import { IUser } from 'app/core/user/user.model';
import { IWish } from 'app/shared/model/wish.model';
import { IEvent } from 'app/shared/model/event.model';
import { IGift } from 'app/shared/model/gift.model';

export interface IMember {
  id?: number;
  firstName?: string;
  lastName?: string;
  user?: IUser;
  wishes?: IWish[];
  owners?: IEvent[];
  gifters?: IGift[];
  receivers?: IGift[];
  events?: IEvent[];
}

export class Member implements IMember {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public user?: IUser,
    public wishes?: IWish[],
    public owners?: IEvent[],
    public gifters?: IGift[],
    public receivers?: IGift[],
    public events?: IEvent[]
  ) {}
}
