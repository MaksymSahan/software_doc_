import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderTariffPlan } from 'app/shared/model/order-tariff-plan.model';
import { OrderTariffPlanService } from './order-tariff-plan.service';

@Component({
  templateUrl: './order-tariff-plan-delete-dialog.component.html'
})
export class OrderTariffPlanDeleteDialogComponent {
  orderTariffPlan?: IOrderTariffPlan;

  constructor(
    protected orderTariffPlanService: OrderTariffPlanService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderTariffPlanService.delete(id).subscribe(() => {
      this.eventManager.broadcast('orderTariffPlanListModification');
      this.activeModal.close();
    });
  }
}
