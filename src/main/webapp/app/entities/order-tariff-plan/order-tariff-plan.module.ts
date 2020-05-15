import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TMobileSharedModule } from 'app/shared/shared.module';
import { OrderTariffPlanComponent } from './order-tariff-plan.component';
import { OrderTariffPlanDetailComponent } from './order-tariff-plan-detail.component';
import { OrderTariffPlanUpdateComponent } from './order-tariff-plan-update.component';
import { OrderTariffPlanDeleteDialogComponent } from './order-tariff-plan-delete-dialog.component';
import { orderTariffPlanRoute } from './order-tariff-plan.route';

@NgModule({
  imports: [TMobileSharedModule, RouterModule.forChild(orderTariffPlanRoute)],
  declarations: [
    OrderTariffPlanComponent,
    OrderTariffPlanDetailComponent,
    OrderTariffPlanUpdateComponent,
    OrderTariffPlanDeleteDialogComponent
  ],
  entryComponents: [OrderTariffPlanDeleteDialogComponent]
})
export class TMobileOrderTariffPlanModule {}
