import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TMobileSharedModule } from 'app/shared/shared.module';
import { TariffPlanComponent } from './tariff-plan.component';
import { TariffPlanDetailComponent } from './tariff-plan-detail.component';
import { TariffPlanUpdateComponent } from './tariff-plan-update.component';
import { TariffPlanDeleteDialogComponent } from './tariff-plan-delete-dialog.component';
import { tariffPlanRoute } from './tariff-plan.route';

@NgModule({
  imports: [TMobileSharedModule, RouterModule.forChild(tariffPlanRoute)],
  declarations: [TariffPlanComponent, TariffPlanDetailComponent, TariffPlanUpdateComponent, TariffPlanDeleteDialogComponent],
  entryComponents: [TariffPlanDeleteDialogComponent]
})
export class TMobileTariffPlanModule {}
