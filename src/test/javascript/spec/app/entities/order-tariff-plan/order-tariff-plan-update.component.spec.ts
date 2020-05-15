import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TMobileTestModule } from '../../../test.module';
import { OrderTariffPlanUpdateComponent } from 'app/entities/order-tariff-plan/order-tariff-plan-update.component';
import { OrderTariffPlanService } from 'app/entities/order-tariff-plan/order-tariff-plan.service';
import { OrderTariffPlan } from 'app/shared/model/order-tariff-plan.model';

describe('Component Tests', () => {
  describe('OrderTariffPlan Management Update Component', () => {
    let comp: OrderTariffPlanUpdateComponent;
    let fixture: ComponentFixture<OrderTariffPlanUpdateComponent>;
    let service: OrderTariffPlanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TMobileTestModule],
        declarations: [OrderTariffPlanUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OrderTariffPlanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderTariffPlanUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderTariffPlanService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderTariffPlan(123);
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
        const entity = new OrderTariffPlan();
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
