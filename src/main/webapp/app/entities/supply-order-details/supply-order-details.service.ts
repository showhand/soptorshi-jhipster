import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupplyOrderDetails } from 'app/shared/model/supply-order-details.model';

type EntityResponseType = HttpResponse<ISupplyOrderDetails>;
type EntityArrayResponseType = HttpResponse<ISupplyOrderDetails[]>;

@Injectable({ providedIn: 'root' })
export class SupplyOrderDetailsService {
    public resourceUrl = SERVER_API_URL + 'api/supply-order-details';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-order-details';

    constructor(protected http: HttpClient) {}

    create(supplyOrderDetails: ISupplyOrderDetails): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyOrderDetails);
        return this.http
            .post<ISupplyOrderDetails>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(supplyOrderDetails: ISupplyOrderDetails): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyOrderDetails);
        return this.http
            .put<ISupplyOrderDetails>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISupplyOrderDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyOrderDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyOrderDetails[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(supplyOrderDetails: ISupplyOrderDetails): ISupplyOrderDetails {
        const copy: ISupplyOrderDetails = Object.assign({}, supplyOrderDetails, {
            createdOn:
                supplyOrderDetails.createdOn != null && supplyOrderDetails.createdOn.isValid()
                    ? supplyOrderDetails.createdOn.toJSON()
                    : null,
            updatedOn:
                supplyOrderDetails.updatedOn != null && supplyOrderDetails.updatedOn.isValid()
                    ? supplyOrderDetails.updatedOn.toJSON()
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
            res.body.forEach((supplyOrderDetails: ISupplyOrderDetails) => {
                supplyOrderDetails.createdOn = supplyOrderDetails.createdOn != null ? moment(supplyOrderDetails.createdOn) : null;
                supplyOrderDetails.updatedOn = supplyOrderDetails.updatedOn != null ? moment(supplyOrderDetails.updatedOn) : null;
            });
        }
        return res;
    }
}
