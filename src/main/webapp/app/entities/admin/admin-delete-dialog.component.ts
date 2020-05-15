import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdmin } from 'app/shared/model/admin.model';
import { AdminService } from './admin.service';

@Component({
  templateUrl: './admin-delete-dialog.component.html'
})
export class AdminDeleteDialogComponent {
  admin?: IAdmin;

  constructor(protected adminService: AdminService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adminService.delete(id).subscribe(() => {
      this.eventManager.broadcast('adminListModification');
      this.activeModal.close();
    });
  }
}
