import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TMobileTestModule } from '../../../test.module';
import { TariffPlanUpdateComponent } from 'app/entities/tariff-plan/tariff-plan-update.component';
import { TariffPlanService } from 'app/entities/tariff-plan/tariff-plan.service';
import { TariffPlan } from 'app/shared/model/tariff-plan.model';

describe('Component Tests', () => {
  describe('TariffPlan Management Update Component', () => {
    let comp: TariffPlanUpdateComponent;
    let fixture: ComponentFixture<TariffPlanUpdateComponent>;
    let service: TariffPlanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TMobileTestModule],
        declarations: [TariffPlanUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TariffPlanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TariffPlanUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TariffPlanService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TariffPlan(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TariffPlan();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
