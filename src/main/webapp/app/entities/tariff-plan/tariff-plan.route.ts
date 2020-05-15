import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITariffPlan, TariffPlan } from 'app/shared/model/tariff-plan.model';
import { TariffPlanService } from './tariff-plan.service';
import { TariffPlanComponent } from './tariff-plan.component';
import { TariffPlanDetailComponent } from './tariff-plan-detail.component';
import { TariffPlanUpdateComponent } from './tariff-plan-update.component';

@Injectable({ providedIn: 'root' })
export class TariffPlanResolve implements Resolve<ITariffPlan> {
  constructor(private service: TariffPlanService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITariffPlan> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tariffPlan: HttpResponse<TariffPlan>) => {
          if (tariffPlan.body) {
            return of(tariffPlan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TariffPlan());
  }
}

export const tariffPlanRoute: Routes = [
  {
    path: '',
    component: TariffPlanComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TariffPlans'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TariffPlanDetailComponent,
    resolve: {
      tariffPlan: TariffPlanResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TariffPlans'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TariffPlanUpdateComponent,
    resolve: {
      tariffPlan: TariffPlanResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TariffPlans'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TariffPlanUpdateComponent,
    resolve: {
      tariffPlan: TariffPlanResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TariffPlans'
    },
    canActivate: [UserRouteAccessService]
  }
];
