import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrderTariffPlan } from 'app/shared/model/order-tariff-plan.model';

type EntityResponseType = HttpResponse<IOrderTariffPlan>;
type EntityArrayResponseType = HttpResponse<IOrderTariffPlan[]>;

@Injectable({ providedIn: 'root' })
export class OrderTariffPlanService {
  public resourceUrl = SERVER_API_URL + 'api/order-tariff-plans';

  constructor(protected http: HttpClient) {}

  create(orderTariffPlan: IOrderTariffPlan): Observable<EntityResponseType> {
    return this.http.post<IOrderTariffPlan>(this.resourceUrl, orderTariffPlan, { observe: 'response' });
  }

  update(orderTariffPlan: IOrderTariffPlan): Observable<EntityResponseType> {
    return this.http.put<IOrderTariffPlan>(this.resourceUrl, orderTariffPlan, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderTariffPlan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderTariffPlan[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
