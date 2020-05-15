import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrderTariffPlan, OrderTariffPlan } from 'app/shared/model/order-tariff-plan.model';
import { OrderTariffPlanService } from './order-tariff-plan.service';
import { OrderTariffPlanComponent } from './order-tariff-plan.component';
import { OrderTariffPlanDetailComponent } from './order-tariff-plan-detail.component';
import { OrderTariffPlanUpdateComponent } from './order-tariff-plan-update.component';

@Injectable({ providedIn: 'root' })
export class OrderTariffPlanResolve implements Resolve<IOrderTariffPlan> {
  constructor(private service: OrderTariffPlanService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderTariffPlan> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((orderTariffPlan: HttpResponse<OrderTariffPlan>) => {
          if (orderTariffPlan.body) {
            return of(orderTariffPlan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderTariffPlan());
  }
}

export const orderTariffPlanRoute: Routes = [
  {
    path: '',
    component: OrderTariffPlanComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'OrderTariffPlans'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrderTariffPlanDetailComponent,
    resolve: {
      orderTariffPlan: OrderTariffPlanResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'OrderTariffPlans'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrderTariffPlanUpdateComponent,
    resolve: {
      orderTariffPlan: OrderTariffPlanResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'OrderTariffPlans'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrderTariffPlanUpdateComponent,
    resolve: {
      orderTariffPlan: OrderTariffPlanResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'OrderTariffPlans'
    },
    canActivate: [UserRouteAccessService]
  }
];
