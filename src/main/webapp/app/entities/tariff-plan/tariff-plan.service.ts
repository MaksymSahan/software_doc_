import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITariffPlan } from 'app/shared/model/tariff-plan.model';

type EntityResponseType = HttpResponse<ITariffPlan>;
type EntityArrayResponseType = HttpResponse<ITariffPlan[]>;

@Injectable({ providedIn: 'root' })
export class TariffPlanService {
  public resourceUrl = SERVER_API_URL + 'api/tariff-plans';

  constructor(protected http: HttpClient) {}

  create(tariffPlan: ITariffPlan): Observable<EntityResponseType> {
    return this.http.post<ITariffPlan>(this.resourceUrl, tariffPlan, { observe: 'response' });
  }

  update(tariffPlan: ITariffPlan): Observable<EntityResponseType> {
    return this.http.put<ITariffPlan>(this.resourceUrl, tariffPlan, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITariffPlan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITariffPlan[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
