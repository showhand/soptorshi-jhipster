import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupplyArea } from 'app/shared/model/supply-area.model';

type EntityResponseType = HttpResponse<ISupplyArea>;
type EntityArrayResponseType = HttpResponse<ISupplyArea[]>;

@Injectable({ providedIn: 'root' })
export class SupplyAreaService {
    public resourceUrl = SERVER_API_URL + 'api/supply-areas';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-areas';

    constructor(protected http: HttpClient) {}

    create(supplyArea: ISupplyArea): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyArea);
        return this.http
            .post<ISupplyArea>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(supplyArea: ISupplyArea): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyArea);
        return this.http
            .put<ISupplyArea>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISupplyArea>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyArea[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyArea[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(supplyArea: ISupplyArea): ISupplyArea {
        const copy: ISupplyArea = Object.assign({}, supplyArea, {
            createdOn: supplyArea.createdOn != null && supplyArea.createdOn.isValid() ? supplyArea.createdOn.toJSON() : null,
            updatedOn: supplyArea.updatedOn != null && supplyArea.updatedOn.isValid() ? supplyArea.updatedOn.toJSON() : null
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
            res.body.forEach((supplyArea: ISupplyArea) => {
                supplyArea.createdOn = supplyArea.createdOn != null ? moment(supplyArea.createdOn) : null;
                supplyArea.updatedOn = supplyArea.updatedOn != null ? moment(supplyArea.updatedOn) : null;
            });
        }
        return res;
    }
}
