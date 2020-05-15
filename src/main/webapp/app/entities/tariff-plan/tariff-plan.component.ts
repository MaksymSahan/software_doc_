import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITariffPlan } from 'app/shared/model/tariff-plan.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TariffPlanService } from './tariff-plan.service';
import { TariffPlanDeleteDialogComponent } from './tariff-plan-delete-dialog.component';

@Component({
  selector: 'jhi-tariff-plan',
  templateUrl: './tariff-plan.component.html'
})
export class TariffPlanComponent implements OnInit, OnDestroy {
  tariffPlans: ITariffPlan[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected tariffPlanService: TariffPlanService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.tariffPlans = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.tariffPlanService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITariffPlan[]>) => this.paginateTariffPlans(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.tariffPlans = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTariffPlans();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITariffPlan): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTariffPlans(): void {
    this.eventSubscriber = this.eventManager.subscribe('tariffPlanListModification', () => this.reset());
  }

  delete(tariffPlan: ITariffPlan): void {
    const modalRef = this.modalService.open(TariffPlanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tariffPlan = tariffPlan;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTariffPlans(data: ITariffPlan[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.tariffPlans.push(data[i]);
      }
    }
  }
}
