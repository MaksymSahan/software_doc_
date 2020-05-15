import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMobileUser, MobileUser } from 'app/shared/model/mobile-user.model';
import { MobileUserService } from './mobile-user.service';
import { ITariffPlan } from 'app/shared/model/tariff-plan.model';
import { TariffPlanService } from 'app/entities/tariff-plan/tariff-plan.service';

@Component({
  selector: 'jhi-mobile-user-update',
  templateUrl: './mobile-user-update.component.html'
})
export class MobileUserUpdateComponent implements OnInit {
  isSaving = false;
  tariffplans: ITariffPlan[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    surname: [null, [Validators.required, Validators.maxLength(100)]],
    email: [null, [Validators.required, Validators.maxLength(100)]],
    phone: [null, [Validators.pattern('^(\\+\\d{1,3}[- ]?)?\\d{10}$')]],
    address: [null, [Validators.maxLength(100)]],
    tariffs: []
  });

  constructor(
    protected mobileUserService: MobileUserService,
    protected tariffPlanService: TariffPlanService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mobileUser }) => {
      this.updateForm(mobileUser);

      this.tariffPlanService.query().subscribe((res: HttpResponse<ITariffPlan[]>) => (this.tariffplans = res.body || []));
    });
  }

  updateForm(mobileUser: IMobileUser): void {
    this.editForm.patchValue({
      id: mobileUser.id,
      name: mobileUser.name,
      surname: mobileUser.surname,
      email: mobileUser.email,
      phone: mobileUser.phone,
      address: mobileUser.address,
      tariffs: mobileUser.tariffs
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mobileUser = this.createFromForm();
    if (mobileUser.id !== undefined) {
      this.subscribeToSaveResponse(this.mobileUserService.update(mobileUser));
    } else {
      this.subscribeToSaveResponse(this.mobileUserService.create(mobileUser));
    }
  }

  private createFromForm(): IMobileUser {
    return {
      ...new MobileUser(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      surname: this.editForm.get(['surname'])!.value,
      email: this.editForm.get(['email'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      address: this.editForm.get(['address'])!.value,
      tariffs: this.editForm.get(['tariffs'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMobileUser>>): void {
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

  trackById(index: number, item: ITariffPlan): any {
    return item.id;
  }

  getSelected(selectedVals: ITariffPlan[], option: ITariffPlan): ITariffPlan {
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
