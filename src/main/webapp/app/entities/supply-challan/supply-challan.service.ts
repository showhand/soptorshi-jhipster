import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupplyChallan } from 'app/shared/model/supply-challan.model';

type EntityResponseType = HttpResponse<ISupplyChallan>;
type EntityArrayResponseType = HttpResponse<ISupplyChallan[]>;

@Injectable({ providedIn: 'root' })
export class SupplyChallanService {
    public resourceUrl = SERVER_API_URL + 'api/supply-challans';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-challans';

    constructor(protected http: HttpClient) {}

    create(supplyChallan: ISupplyChallan): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyChallan);
        return this.http
            .post<ISupplyChallan>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(supplyChallan: ISupplyChallan): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyChallan);
        return this.http
            .put<ISupplyChallan>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISupplyChallan>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyChallan[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyChallan[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(supplyChallan: ISupplyChallan): ISupplyChallan {
        const copy: ISupplyChallan = Object.assign({}, supplyChallan, {
            dateOfChallan:
                supplyChallan.dateOfChallan != null && supplyChallan.dateOfChallan.isValid()
                    ? supplyChallan.dateOfChallan.format(DATE_FORMAT)
                    : null,
            createdOn: supplyChallan.createdOn != null && supplyChallan.createdOn.isValid() ? supplyChallan.createdOn.toJSON() : null,
            updatedOn: supplyChallan.updatedOn != null && supplyChallan.updatedOn.isValid() ? supplyChallan.updatedOn.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateOfChallan = res.body.dateOfChallan != null ? moment(res.body.dateOfChallan) : null;
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((supplyChallan: ISupplyChallan) => {
                supplyChallan.dateOfChallan = supplyChallan.dateOfChallan != null ? moment(supplyChallan.dateOfChallan) : null;
                supplyChallan.createdOn = supplyChallan.createdOn != null ? moment(supplyChallan.createdOn) : null;
                supplyChallan.updatedOn = supplyChallan.updatedOn != null ? moment(supplyChallan.updatedOn) : null;
            });
        }
        return res;
    }
}
