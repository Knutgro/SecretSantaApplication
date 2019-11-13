import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecretSantaAppTestModule } from '../../../test.module';
import { MemberComponent } from 'app/entities/member/member.component';
import { MemberService } from 'app/entities/member/member.service';
import { Member } from 'app/shared/model/member.model';

describe('Component Tests', () => {
  describe('Member Management Component', () => {
    let comp: MemberComponent;
    let fixture: ComponentFixture<MemberComponent>;
    let service: MemberService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecretSantaAppTestModule],
        declarations: [MemberComponent],
        providers: []
      })
        .overrideTemplate(MemberComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MemberComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MemberService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Member(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.members[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
