import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupplyZoneWiseAccumulation } from 'app/shared/model/supply-zone-wise-accumulation.model';

type EntityResponseType = HttpResponse<ISupplyZoneWiseAccumulation>;
type EntityArrayResponseType = HttpResponse<ISupplyZoneWiseAccumulation[]>;

@Injectable({ providedIn: 'root' })
export class SupplyZoneWiseAccumulationService {
    public resourceUrl = SERVER_API_URL + 'api/supply-zone-wise-accumulations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-zone-wise-accumulations';

    constructor(protected http: HttpClient) {}

    create(supplyZoneWiseAccumulation: ISupplyZoneWiseAccumulation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyZoneWiseAccumulation);
        return this.http
            .post<ISupplyZoneWiseAccumulation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(supplyZoneWiseAccumulation: ISupplyZoneWiseAccumulation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyZoneWiseAccumulation);
        return this.http
            .put<ISupplyZoneWiseAccumulation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISupplyZoneWiseAccumulation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyZoneWiseAccumulation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyZoneWiseAccumulation[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(supplyZoneWiseAccumulation: ISupplyZoneWiseAccumulation): ISupplyZoneWiseAccumulation {
        const copy: ISupplyZoneWiseAccumulation = Object.assign({}, supplyZoneWiseAccumulation, {
            createdOn:
                supplyZoneWiseAccumulation.createdOn != null && supplyZoneWiseAccumulation.createdOn.isValid()
                    ? supplyZoneWiseAccumulation.createdOn.toJSON()
                    : null,
            updatedOn:
                supplyZoneWiseAccumulation.updatedOn != null && supplyZoneWiseAccumulation.updatedOn.isValid()
                    ? supplyZoneWiseAccumulation.updatedOn.toJSON()
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
            res.body.forEach((supplyZoneWiseAccumulation: ISupplyZoneWiseAccumulation) => {
                supplyZoneWiseAccumulation.createdOn =
                    supplyZoneWiseAccumulation.createdOn != null ? moment(supplyZoneWiseAccumulation.createdOn) : null;
                supplyZoneWiseAccumulation.updatedOn =
                    supplyZoneWiseAccumulation.updatedOn != null ? moment(supplyZoneWiseAccumulation.updatedOn) : null;
            });
        }
        return res;
    }
}
