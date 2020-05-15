import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TMobileTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { OrderTariffPlanDeleteDialogComponent } from 'app/entities/order-tariff-plan/order-tariff-plan-delete-dialog.component';
import { OrderTariffPlanService } from 'app/entities/order-tariff-plan/order-tariff-plan.service';

describe('Component Tests', () => {
  describe('OrderTariffPlan Management Delete Component', () => {
    let comp: OrderTariffPlanDeleteDialogComponent;
    let fixture: ComponentFixture<OrderTariffPlanDeleteDialogComponent>;
    let service: OrderTariffPlanService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TMobileTestModule],
        declarations: [OrderTariffPlanDeleteDialogComponent]
      })
        .overrideTemplate(OrderTariffPlanDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderTariffPlanDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderTariffPlanService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
