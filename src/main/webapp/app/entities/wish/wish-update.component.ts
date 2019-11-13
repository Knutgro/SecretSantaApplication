import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IWish, Wish } from 'app/shared/model/wish.model';
import { WishService } from './wish.service';
import { IMember } from 'app/shared/model/member.model';
import { MemberService } from 'app/entities/member/member.service';
import { IEvent } from 'app/shared/model/event.model';
import { EventService } from 'app/entities/event/event.service';

@Component({
  selector: 'jhi-wish-update',
  templateUrl: './wish-update.component.html'
})
export class WishUpdateComponent implements OnInit {
  isSaving: boolean;

  members: IMember[];

  events: IEvent[];

  editForm = this.fb.group({
    id: [],
    name: [],
    url: [],
    member: [],
    event: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected wishService: WishService,
    protected memberService: MemberService,
    protected eventService: EventService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ wish }) => {
      this.updateForm(wish);
    });
    this.memberService
      .query()
      .subscribe((res: HttpResponse<IMember[]>) => (this.members = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.eventService
      .query()
      .subscribe((res: HttpResponse<IEvent[]>) => (this.events = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(wish: IWish) {
    this.editForm.patchValue({
      id: wish.id,
      name: wish.name,
      url: wish.url,
      member: wish.member,
      event: wish.event
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const wish = this.createFromForm();
    if (wish.id !== undefined) {
      this.subscribeToSaveResponse(this.wishService.update(wish));
    } else {
      this.subscribeToSaveResponse(this.wishService.create(wish));
    }
  }

  private createFromForm(): IWish {
    return {
      ...new Wish(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      url: this.editForm.get(['url']).value,
      member: this.editForm.get(['member']).value,
      event: this.editForm.get(['event']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWish>>) {
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
