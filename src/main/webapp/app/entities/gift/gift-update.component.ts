import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IGift, Gift } from 'app/shared/model/gift.model';
import { GiftService } from './gift.service';
import { IMember } from 'app/shared/model/member.model';
import { MemberService } from 'app/entities/member/member.service';
import { IEvent } from 'app/shared/model/event.model';
import { EventService } from 'app/entities/event/event.service';

@Component({
  selector: 'jhi-gift-update',
  templateUrl: './gift-update.component.html'
})
export class GiftUpdateComponent implements OnInit {
  isSaving: boolean;

  members: IMember[];

  events: IEvent[];

  editForm = this.fb.group({
    id: [],
    giftedGift: [],
    receivedGift: [],
    event: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected giftService: GiftService,
    protected memberService: MemberService,
    protected eventService: EventService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ gift }) => {
      this.updateForm(gift);
    });
    this.memberService
      .query()
      .subscribe((res: HttpResponse<IMember[]>) => (this.members = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.eventService
      .query()
      .subscribe((res: HttpResponse<IEvent[]>) => (this.events = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(gift: IGift) {
    this.editForm.patchValue({
      id: gift.id,
      giftedGift: gift.giftedGift,
      receivedGift: gift.receivedGift,
      event: gift.event
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const gift = this.createFromForm();
    if (gift.id !== undefined) {
      this.subscribeToSaveResponse(this.giftService.update(gift));
    } else {
      this.subscribeToSaveResponse(this.giftService.create(gift));
    }
  }

  private createFromForm(): IGift {
    return {
      ...new Gift(),
      id: this.editForm.get(['id']).value,
      giftedGift: this.editForm.get(['giftedGift']).value,
      receivedGift: this.editForm.get(['receivedGift']).value,
      event: this.editForm.get(['event']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGift>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackMemberById(index: number, item: IMember) {
    return item.id;
  }

  trackEventById(index: number, item: IEvent) {
    return item.id;
  }
}
