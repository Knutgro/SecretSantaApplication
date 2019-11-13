import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecretSantaAppTestModule } from '../../../test.module';
import { GiftComponent } from 'app/entities/gift/gift.component';
import { GiftService } from 'app/entities/gift/gift.service';
import { Gift } from 'app/shared/model/gift.model';

describe('Component Tests', () => {
  describe('Gift Management Component', () => {
    let comp: GiftComponent;
    let fixture: ComponentFixture<GiftComponent>;
    let service: GiftService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecretSantaAppTestModule],
        declarations: [GiftComponent],
        providers: []
      })
        .overrideTemplate(GiftComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GiftComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GiftService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Gift(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.gifts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
