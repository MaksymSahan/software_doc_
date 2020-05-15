import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITariffPlan } from 'app/shared/model/tariff-plan.model';

@Component({
  selector: 'jhi-tariff-plan-detail',
  templateUrl: './tariff-plan-detail.component.html'
})
export class TariffPlanDetailComponent implements OnInit {
  tariffPlan: ITariffPlan | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tariffPlan }) => (this.tariffPlan = tariffPlan));
  }

  previousState(): void {
    window.history.back();
  }
}
