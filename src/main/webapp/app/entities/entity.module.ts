import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'mobile-user',
        loadChildren: () => import('./mobile-user/mobile-user.module').then(m => m.TMobileMobileUserModule)
      },
      {
        path: 'order-tariff-plan',
        loadChildren: () => import('./order-tariff-plan/order-tariff-plan.module').then(m => m.TMobileOrderTariffPlanModule)
      },
      {
        path: 'payment',
        loadChildren: () => import('./payment/payment.module').then(m => m.TMobilePaymentModule)
      },
      {
        path: 'admin',
        loadChildren: () => import('./admin/admin.module').then(m => m.TMobileAdminModule)
      },
      {
        path: 'tariff-plan',
        loadChildren: () => import('./tariff-plan/tariff-plan.module').then(m => m.TMobileTariffPlanModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class TMobileEntityModule {}
