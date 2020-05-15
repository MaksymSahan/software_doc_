import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAdmin, Admin } from 'app/shared/model/admin.model';
import { AdminService } from './admin.service';
import { IMobileUser } from 'app/shared/model/mobile-user.model';
import { MobileUserService } from 'app/entities/mobile-user/mobile-user.service';

@Component({
  selector: 'jhi-admin-update',
  templateUrl: './admin-update.component.html'
})
export class AdminUpdateComponent implements OnInit {
  isSaving = false;
  mobileusers: IMobileUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    whoHelpeds: []
  });

  constructor(
    protected adminService: AdminService,
    protected mobileUserService: MobileUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ admin }) => {
      this.updateForm(admin);

      this.mobileUserService.query().subscribe((res: HttpResponse<IMobileUser[]>) => (this.mobileusers = res.body || []));
    });
  }

  updateForm(admin: IAdmin): void {
    this.editForm.patchValue({
      id: admin.id,
      name: admin.name,
      whoHelpeds: admin.whoHelpeds
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const admin = this.createFromForm();
    if (admin.id !== undefined) {
      this.subscribeToSaveResponse(this.adminService.update(admin));
    } else {
      this.subscribeToSaveResponse(this.adminService.create(admin));
    }
  }

  private createFromForm(): IAdmin {
    return {
      ...new Admin(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      whoHelpeds: this.editForm.get(['whoHelpeds'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdmin>>): void {
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
