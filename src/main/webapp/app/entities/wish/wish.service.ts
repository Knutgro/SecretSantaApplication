import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWish } from 'app/shared/model/wish.model';
import { IEvent } from 'app/shared/model/event.model';
import { IMember } from 'app/shared/model/member.model';

type EntityResponseType = HttpResponse<IWish>;
type EntityArrayResponseType = HttpResponse<IWish[]>;

@Injectable({ providedIn: 'root' })
export class WishService {
  public resourceUrl = SERVER_API_URL + 'api/wishes';
  public resourceUrl2 = SERVER_API_URL + 'api/recipientWish';
  public resourceUrl3 = SERVER_API_URL + 'api/eventWishes';

  constructor(protected http: HttpClient) {}

  create(wish: IWish): Observable<EntityResponseType> {
    return this.http.post<IWish>(this.resourceUrl, wish, { observe: 'response' });
  }

  update(wish: IWish): Observable<EntityResponseType> {
    return this.http.put<IWish>(this.resourceUrl, wish, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWish>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findEvent(memberId: number, eventId: number): Observable<EntityArrayResponseType> {
    return this.http.get<IWish[]>(`${this.resourceUrl2}/${memberId}/${eventId}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWish[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
