import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupplyAreaManager } from 'app/shared/model/supply-area-manager.model';

type EntityResponseType = HttpResponse<ISupplyAreaManager>;
type EntityArrayResponseType = HttpResponse<ISupplyAreaManager[]>;

@Injectable({ providedIn: 'root' })
export class SupplyAreaManagerService {
    public resourceUrl = SERVER_API_URL + 'api/supply-area-managers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-area-managers';

    constructor(protected http: HttpClient) {}

    create(supplyAreaManager: ISupplyAreaManager): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyAreaManager);
        return this.http
            .post<ISupplyAreaManager>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(supplyAreaManager: ISupplyAreaManager): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyAreaManager);
        return this.http
            .put<ISupplyAreaManager>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISupplyAreaManager>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyAreaManager[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyAreaManager[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(supplyAreaManager: ISupplyAreaManager): ISupplyAreaManager {
        const copy: ISupplyAreaManager = Object.assign({}, supplyAreaManager, {
            createdOn:
                supplyAreaManager.createdOn != null && supplyAreaManager.createdOn.isValid() ? supplyAreaManager.createdOn.toJSON() : null,
            updatedOn:
                supplyAreaManager.updatedOn != null && supplyAreaManager.updatedOn.isValid() ? supplyAreaManager.updatedOn.toJSON() : null,
            endDate:
                supplyAreaManager.endDate != null && supplyAreaManager.endDate.isValid()
                    ? supplyAreaManager.endDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((supplyAreaManager: ISupplyAreaManager) => {
                supplyAreaManager.createdOn = supplyAreaManager.createdOn != null ? moment(supplyAreaManager.createdOn) : null;
                supplyAreaManager.updatedOn = supplyAreaManager.updatedOn != null ? moment(supplyAreaManager.updatedOn) : null;
                supplyAreaManager.endDate = supplyAreaManager.endDate != null ? moment(supplyAreaManager.endDate) : null;
            });
        }
        return res;
    }
}
