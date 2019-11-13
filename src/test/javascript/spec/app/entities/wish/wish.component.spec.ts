import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecretSantaAppTestModule } from '../../../test.module';
import { WishComponent } from 'app/entities/wish/wish.component';
import { WishService } from 'app/entities/wish/wish.service';
import { Wish } from 'app/shared/model/wish.model';

describe('Component Tests', () => {
  describe('Wish Management Component', () => {
    let comp: WishComponent;
    let fixture: ComponentFixture<WishComponent>;
    let service: WishService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecretSantaAppTestModule],
        declarations: [WishComponent],
        providers: []
      })
        .overrideTemplate(WishComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WishComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WishService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Wish(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.wishes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
