import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupplyShop } from 'app/shared/model/supply-shop.model';

type EntityResponseType = HttpResponse<ISupplyShop>;
type EntityArrayResponseType = HttpResponse<ISupplyShop[]>;

@Injectable({ providedIn: 'root' })
export class SupplyShopService {
    public resourceUrl = SERVER_API_URL + 'api/supply-shops';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-shops';

    constructor(protected http: HttpClient) {}

    create(supplyShop: ISupplyShop): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyShop);
        return this.http
            .post<ISupplyShop>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(supplyShop: ISupplyShop): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyShop);
        return this.http
            .put<ISupplyShop>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISupplyShop>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyShop[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyShop[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(supplyShop: ISupplyShop): ISupplyShop {
        const copy: ISupplyShop = Object.assign({}, supplyShop, {
            createdOn: supplyShop.createdOn != null && supplyShop.createdOn.isValid() ? supplyShop.createdOn.toJSON() : null,
            updatedOn: supplyShop.updatedOn != null && supplyShop.updatedOn.isValid() ? supplyShop.updatedOn.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((supplyShop: ISupplyShop) => {
                supplyShop.createdOn = supplyShop.createdOn != null ? moment(supplyShop.createdOn) : null;
                supplyShop.updatedOn = supplyShop.updatedOn != null ? moment(supplyShop.updatedOn) : null;
            });
        }
        return res;
    }
}
