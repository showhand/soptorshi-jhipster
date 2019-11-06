import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupplyZone } from 'app/shared/model/supply-zone.model';

type EntityResponseType = HttpResponse<ISupplyZone>;
type EntityArrayResponseType = HttpResponse<ISupplyZone[]>;

@Injectable({ providedIn: 'root' })
export class SupplyZoneService {
    public resourceUrl = SERVER_API_URL + 'api/supply-zones';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-zones';

    constructor(protected http: HttpClient) {}

    create(supplyZone: ISupplyZone): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyZone);
        return this.http
            .post<ISupplyZone>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(supplyZone: ISupplyZone): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyZone);
        return this.http
            .put<ISupplyZone>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISupplyZone>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyZone[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyZone[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(supplyZone: ISupplyZone): ISupplyZone {
        const copy: ISupplyZone = Object.assign({}, supplyZone, {
            createdOn: supplyZone.createdOn != null && supplyZone.createdOn.isValid() ? supplyZone.createdOn.toJSON() : null,
            updatedOn: supplyZone.updatedOn != null && supplyZone.updatedOn.isValid() ? supplyZone.updatedOn.toJSON() : null
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
            res.body.forEach((supplyZone: ISupplyZone) => {
                supplyZone.createdOn = supplyZone.createdOn != null ? moment(supplyZone.createdOn) : null;
                supplyZone.updatedOn = supplyZone.updatedOn != null ? moment(supplyZone.updatedOn) : null;
            });
        }
        return res;
    }
}
