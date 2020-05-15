import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrderTariffPlan } from 'app/shared/model/order-tariff-plan.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { OrderTariffPlanService } from './order-tariff-plan.service';
import { OrderTariffPlanDeleteDialogComponent } from './order-tariff-plan-delete-dialog.component';

@Component({
  selector: 'jhi-order-tariff-plan',
  templateUrl: './order-tariff-plan.component.html'
})
export class OrderTariffPlanComponent implements OnInit, OnDestroy {
  orderTariffPlans: IOrderTariffPlan[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected orderTariffPlanService: OrderTariffPlanService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.orderTariffPlans = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.orderTariffPlanService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IOrderTariffPlan[]>) => this.paginateOrderTariffPlans(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.orderTariffPlans = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOrderTariffPlans();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrderTariffPlan): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOrderTariffPlans(): void {
    this.eventSubscriber = this.eventManager.subscribe('orderTariffPlanListModification', () => this.reset());
  }

  delete(orderTariffPlan: IOrderTariffPlan): void {
    const modalRef = this.modalService.open(OrderTariffPlanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.orderTariffPlan = orderTariffPlan;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateOrderTariffPlans(data: IOrderTariffPlan[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.orderTariffPlans.push(data[i]);
      }
    }
  }
}
