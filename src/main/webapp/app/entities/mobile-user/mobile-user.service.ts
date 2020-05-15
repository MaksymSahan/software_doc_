import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMobileUser } from 'app/shared/model/mobile-user.model';

type EntityResponseType = HttpResponse<IMobileUser>;
type EntityArrayResponseType = HttpResponse<IMobileUser[]>;

@Injectable({ providedIn: 'root' })
export class MobileUserService {
  public resourceUrl = SERVER_API_URL + 'api/mobile-users';

  constructor(protected http: HttpClient) {}

  create(mobileUser: IMobileUser): Observable<EntityResponseType> {
    return this.http.post<IMobileUser>(this.resourceUrl, mobileUser, { observe: 'response' });
  }

  update(mobileUser: IMobileUser): Observable<EntityResponseType> {
    return this.http.put<IMobileUser>(this.resourceUrl, mobileUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMobileUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMobileUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
