import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupplyMoneyCollection } from 'app/shared/model/supply-money-collection.model';

type EntityResponseType = HttpResponse<ISupplyMoneyCollection>;
type EntityArrayResponseType = HttpResponse<ISupplyMoneyCollection[]>;

@Injectable({ providedIn: 'root' })
export class SupplyMoneyCollectionService {
    public resourceUrl = SERVER_API_URL + 'api/supply-money-collections';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-money-collections';

    constructor(protected http: HttpClient) {}

    create(supplyMoneyCollection: ISupplyMoneyCollection): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyMoneyCollection);
        return this.http
            .post<ISupplyMoneyCollection>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(supplyMoneyCollection: ISupplyMoneyCollection): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyMoneyCollection);
        return this.http
            .put<ISupplyMoneyCollection>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISupplyMoneyCollection>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyMoneyCollection[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyMoneyCollection[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(supplyMoneyCollection: ISupplyMoneyCollection): ISupplyMoneyCollection {
        const copy: ISupplyMoneyCollection = Object.assign({}, supplyMoneyCollection, {
            createdOn:
                supplyMoneyCollection.createdOn != null && supplyMoneyCollection.createdOn.isValid()
                    ? supplyMoneyCollection.createdOn.toJSON()
                    : null,
            updatedOn:
                supplyMoneyCollection.updatedOn != null && supplyMoneyCollection.updatedOn.isValid()
                    ? supplyMoneyCollection.updatedOn.toJSON()
                    : null
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
            res.body.forEach((supplyMoneyCollection: ISupplyMoneyCollection) => {
                supplyMoneyCollection.createdOn = supplyMoneyCollection.createdOn != null ? moment(supplyMoneyCollection.createdOn) : null;
                supplyMoneyCollection.updatedOn = supplyMoneyCollection.updatedOn != null ? moment(supplyMoneyCollection.updatedOn) : null;
            });
        }
        return res;
    }
}
