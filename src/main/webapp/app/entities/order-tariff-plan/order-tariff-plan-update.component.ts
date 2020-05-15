import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOrderTariffPlan, OrderTariffPlan } from 'app/shared/model/order-tariff-plan.model';
import { OrderTariffPlanService } from './order-tariff-plan.service';
import { IMobileUser } from 'app/shared/model/mobile-user.model';
import { MobileUserService } from 'app/entities/mobile-user/mobile-user.service';

@Component({
  selector: 'jhi-order-tariff-plan-update',
  templateUrl: './order-tariff-plan-update.component.html'
})
export class OrderTariffPlanUpdateComponent implements OnInit {
  isSaving = false;
  mobileusers: IMobileUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    price: [],
    whoOrdereds: []
  });

  constructor(
    protected orderTariffPlanService: OrderTariffPlanService,
    protected mobileUserService: MobileUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderTariffPlan }) => {
      this.updateForm(orderTariffPlan);

      this.mobileUserService.query().subscribe((res: HttpResponse<IMobileUser[]>) => (this.mobileusers = res.body || []));
    });
  }

  updateForm(orderTariffPlan: IOrderTariffPlan): void {
    this.editForm.patchValue({
      id: orderTariffPlan.id,
      name: orderTariffPlan.name,
      price: orderTariffPlan.price,
      whoOrdereds: orderTariffPlan.whoOrdereds
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderTariffPlan = this.createFromForm();
    if (orderTariffPlan.id !== undefined) {
      this.subscribeToSaveResponse(this.orderTariffPlanService.update(orderTariffPlan));
    } else {
      this.subscribeToSaveResponse(this.orderTariffPlanService.create(orderTariffPlan));
    }
  }

  private createFromForm(): IOrderTariffPlan {
    return {
      ...new OrderTariffPlan(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      price: this.editForm.get(['price'])!.value,
      whoOrdereds: this.editForm.get(['whoOrdereds'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderTariffPlan>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IMobileUser): any {
    return item.id;
  }

  getSelected(selectedVals: IMobileUser[], option: IMobileUser): IMobileUser {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
