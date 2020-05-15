import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TMobileTestModule } from '../../../test.module';
import { TariffPlanDetailComponent } from 'app/entities/tariff-plan/tariff-plan-detail.component';
import { TariffPlan } from 'app/shared/model/tariff-plan.model';

describe('Component Tests', () => {
  describe('TariffPlan Management Detail Component', () => {
    let comp: TariffPlanDetailComponent;
    let fixture: ComponentFixture<TariffPlanDetailComponent>;
    const route = ({ data: of({ tariffPlan: new TariffPlan(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TMobileTestModule],
        declarations: [TariffPlanDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TariffPlanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TariffPlanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tariffPlan on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tariffPlan).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
