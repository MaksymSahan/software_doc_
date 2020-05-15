import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITariffPlan } from 'app/shared/model/tariff-plan.model';
import { TariffPlanService } from './tariff-plan.service';

@Component({
  templateUrl: './tariff-plan-delete-dialog.component.html'
})
export class TariffPlanDeleteDialogComponent {
  tariffPlan?: ITariffPlan;

  constructor(
    protected tariffPlanService: TariffPlanService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tariffPlanService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tariffPlanListModification');
      this.activeModal.close();
    });
  }
}
