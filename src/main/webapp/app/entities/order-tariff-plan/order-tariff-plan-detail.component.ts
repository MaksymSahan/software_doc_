import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderTariffPlan } from 'app/shared/model/order-tariff-plan.model';

@Component({
  selector: 'jhi-order-tariff-plan-detail',
  templateUrl: './order-tariff-plan-detail.component.html'
})
export class OrderTariffPlanDetailComponent implements OnInit {
  orderTariffPlan: IOrderTariffPlan | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderTariffPlan }) => (this.orderTariffPlan = orderTariffPlan));
  }

  previousState(): void {
    window.history.back();
  }
}
