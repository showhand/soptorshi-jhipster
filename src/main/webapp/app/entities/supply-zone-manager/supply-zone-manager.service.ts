import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';

type EntityResponseType = HttpResponse<ISupplyZoneManager>;
type EntityArrayResponseType = HttpResponse<ISupplyZoneManager[]>;

@Injectable({ providedIn: 'root' })
export class SupplyZoneManagerService {
    public resourceUrl = SERVER_API_URL + 'api/supply-zone-managers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-zone-managers';

    constructor(protected http: HttpClient) {}

    create(supplyZoneManager: ISupplyZoneManager): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyZoneManager);
        return this.http
            .post<ISupplyZoneManager>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(supplyZoneManager: ISupplyZoneManager): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyZoneManager);
        return this.http
            .put<ISupplyZoneManager>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISupplyZoneManager>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyZoneManager[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyZoneManager[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(supplyZoneManager: ISupplyZoneManager): ISupplyZoneManager {
        const copy: ISupplyZoneManager = Object.assign({}, supplyZoneManager, {
            endDate:
                supplyZoneManager.endDate != null && supplyZoneManager.endDate.isValid()
                    ? supplyZoneManager.endDate.format(DATE_FORMAT)
                    : null,
            createdOn:
                supplyZoneManager.createdOn != null && supplyZoneManager.createdOn.isValid() ? supplyZoneManager.createdOn.toJSON() : null,
            updatedOn:
                supplyZoneManager.updatedOn != null && supplyZoneManager.updatedOn.isValid() ? supplyZoneManager.updatedOn.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((supplyZoneManager: ISupplyZoneManager) => {
                supplyZoneManager.endDate = supplyZoneManager.endDate != null ? moment(supplyZoneManager.endDate) : null;
                supplyZoneManager.createdOn = supplyZoneManager.createdOn != null ? moment(supplyZoneManager.createdOn) : null;
                supplyZoneManager.updatedOn = supplyZoneManager.updatedOn != null ? moment(supplyZoneManager.updatedOn) : null;
            });
        }
        return res;
    }
}
