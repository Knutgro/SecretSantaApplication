import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IEvent, Event } from 'app/shared/model/event.model';
import { EventService } from './event.service';
import { IMember } from 'app/shared/model/member.model';
import { MemberService } from 'app/entities/member/member.service';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-event-update',
  templateUrl: './event-update.component.html'
})
export class EventUpdateComponent implements OnInit {
  isSaving: boolean;
  account: Account;
  members: IMember[];

  editForm = this.fb.group({
    id: [],
    name: [],
    maxLimit: [],
    minLimit: [],
    dateCreated: [],
    dateExpired: [],
    members: [],
    owned: []
  });

  constructor(
    private accountService: AccountService,
    protected jhiAlertService: JhiAlertService,
    protected eventService: EventService,
    protected memberService: MemberService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.accountService.identity().subscribe((account: Account) => {
      this.account = account;
    });

    this.activatedRoute.data.subscribe(({ event }) => {
      this.updateForm(event);
    });
    this.memberService
      .query()
      .subscribe((res: HttpResponse<IMember[]>) => (this.members = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(event: IEvent) {
    this.editForm.patchValue({
      id: event.id,
      name: event.name,
      maxLimit: event.maxLimit,
      minLimit: event.minLimit,
      dateCreated: event.dateCreated != null ? event.dateCreated.format(DATE_TIME_FORMAT) : null,
      dateExpired: event.dateExpired != null ? event.dateExpired.format(DATE_TIME_FORMAT) : null,
      members: event.members,
      owned: this.account
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const event = this.createFromForm();
    if (event.id !== undefined) {
      this.subscribeToSaveResponse(this.eventService.update(event));
    } else {
      this.subscribeToSaveResponse(this.eventService.create(event));
    }
  }

  private createFromForm(): IEvent {
    return {
      ...new Event(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      maxLimit: this.editForm.get(['maxLimit']).value,
      minLimit: this.editForm.get(['minLimit']).value,
      dateCreated: moment(),
      dateExpired:
        this.editForm.get(['dateExpired']).value != null ? moment(this.editForm.get(['dateExpired']).value, DATE_TIME_FORMAT) : undefined,
      members: this.editForm.get(['members']).value,
      owned: this.account
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvent>>) {
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
    return item.user.email;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
