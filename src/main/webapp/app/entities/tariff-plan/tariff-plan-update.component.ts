import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITariffPlan, TariffPlan } from 'app/shared/model/tariff-plan.model';
import { TariffPlanService } from './tariff-plan.service';
import { IAdmin } from 'app/shared/model/admin.model';
import { AdminService } from 'app/entities/admin/admin.service';

@Component({
  selector: 'jhi-tariff-plan-update',
  templateUrl: './tariff-plan-update.component.html'
})
export class TariffPlanUpdateComponent implements OnInit {
  isSaving = false;
  admins: IAdmin[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    description: [null, [Validators.maxLength(100)]],
    internet: [],
    calls: [],
    sms: [],
    price: [],
    updatedBy: []
  });

  constructor(
    protected tariffPlanService: TariffPlanService,
    protected adminService: AdminService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tariffPlan }) => {
      this.updateForm(tariffPlan);

      this.adminService.query().subscribe((res: HttpResponse<IAdmin[]>) => (this.admins = res.body || []));
    });
  }

  updateForm(tariffPlan: ITariffPlan): void {
    this.editForm.patchValue({
      id: tariffPlan.id,
      name: tariffPlan.name,
      description: tariffPlan.description,
      internet: tariffPlan.internet,
      calls: tariffPlan.calls,
      sms: tariffPlan.sms,
      price: tariffPlan.price,
      updatedBy: tariffPlan.updatedBy
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tariffPlan = this.createFromForm();
    if (tariffPlan.id !== undefined) {
      this.subscribeToSaveResponse(this.tariffPlanService.update(tariffPlan));
    } else {
      this.subscribeToSaveResponse(this.tariffPlanService.create(tariffPlan));
    }
  }

  private createFromForm(): ITariffPlan {
    return {
      ...new TariffPlan(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      internet: this.editForm.get(['internet'])!.value,
      calls: this.editForm.get(['calls'])!.value,
      sms: this.editForm.get(['sms'])!.value,
      price: this.editForm.get(['price'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITariffPlan>>): void {
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

  trackById(index: number, item: IAdmin): any {
    return item.id;
  }
}
