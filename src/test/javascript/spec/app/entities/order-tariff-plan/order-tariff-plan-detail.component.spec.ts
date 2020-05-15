import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TMobileTestModule } from '../../../test.module';
import { OrderTariffPlanDetailComponent } from 'app/entities/order-tariff-plan/order-tariff-plan-detail.component';
import { OrderTariffPlan } from 'app/shared/model/order-tariff-plan.model';

describe('Component Tests', () => {
  describe('OrderTariffPlan Management Detail Component', () => {
    let comp: OrderTariffPlanDetailComponent;
    let fixture: ComponentFixture<OrderTariffPlanDetailComponent>;
    const route = ({ data: of({ orderTariffPlan: new OrderTariffPlan(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TMobileTestModule],
        declarations: [OrderTariffPlanDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrderTariffPlanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderTariffPlanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load orderTariffPlan on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderTariffPlan).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
