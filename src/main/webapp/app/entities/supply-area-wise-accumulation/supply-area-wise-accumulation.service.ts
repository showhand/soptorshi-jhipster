import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupplyAreaWiseAccumulation } from 'app/shared/model/supply-area-wise-accumulation.model';

type EntityResponseType = HttpResponse<ISupplyAreaWiseAccumulation>;
type EntityArrayResponseType = HttpResponse<ISupplyAreaWiseAccumulation[]>;

@Injectable({ providedIn: 'root' })
export class SupplyAreaWiseAccumulationService {
    public resourceUrl = SERVER_API_URL + 'api/supply-area-wise-accumulations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-area-wise-accumulations';

    constructor(protected http: HttpClient) {}

    create(supplyAreaWiseAccumulation: ISupplyAreaWiseAccumulation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyAreaWiseAccumulation);
        return this.http
            .post<ISupplyAreaWiseAccumulation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(supplyAreaWiseAccumulation: ISupplyAreaWiseAccumulation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyAreaWiseAccumulation);
        return this.http
            .put<ISupplyAreaWiseAccumulation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISupplyAreaWiseAccumulation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyAreaWiseAccumulation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyAreaWiseAccumulation[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(supplyAreaWiseAccumulation: ISupplyAreaWiseAccumulation): ISupplyAreaWiseAccumulation {
        const copy: ISupplyAreaWiseAccumulation = Object.assign({}, supplyAreaWiseAccumulation, {
            createdOn:
                supplyAreaWiseAccumulation.createdOn != null && supplyAreaWiseAccumulation.createdOn.isValid()
                    ? supplyAreaWiseAccumulation.createdOn.toJSON()
                    : null,
            updatedOn:
                supplyAreaWiseAccumulation.updatedOn != null && supplyAreaWiseAccumulation.updatedOn.isValid()
                    ? supplyAreaWiseAccumulation.updatedOn.toJSON()
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
            res.body.forEach((supplyAreaWiseAccumulation: ISupplyAreaWiseAccumulation) => {
                supplyAreaWiseAccumulation.createdOn =
                    supplyAreaWiseAccumulation.createdOn != null ? moment(supplyAreaWiseAccumulation.createdOn) : null;
                supplyAreaWiseAccumulation.updatedOn =
                    supplyAreaWiseAccumulation.updatedOn != null ? moment(supplyAreaWiseAccumulation.updatedOn) : null;
            });
        }
        return res;
    }
}
