import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupplySalesRepresentative } from 'app/shared/model/supply-sales-representative.model';

type EntityResponseType = HttpResponse<ISupplySalesRepresentative>;
type EntityArrayResponseType = HttpResponse<ISupplySalesRepresentative[]>;

@Injectable({ providedIn: 'root' })
export class SupplySalesRepresentativeService {
    public resourceUrl = SERVER_API_URL + 'api/supply-sales-representatives';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-sales-representatives';

    constructor(protected http: HttpClient) {}

    create(supplySalesRepresentative: ISupplySalesRepresentative): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplySalesRepresentative);
        return this.http
            .post<ISupplySalesRepresentative>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(supplySalesRepresentative: ISupplySalesRepresentative): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplySalesRepresentative);
        return this.http
            .put<ISupplySalesRepresentative>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISupplySalesRepresentative>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplySalesRepresentative[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplySalesRepresentative[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(supplySalesRepresentative: ISupplySalesRepresentative): ISupplySalesRepresentative {
        const copy: ISupplySalesRepresentative = Object.assign({}, supplySalesRepresentative, {
            createdOn:
                supplySalesRepresentative.createdOn != null && supplySalesRepresentative.createdOn.isValid()
                    ? supplySalesRepresentative.createdOn.toJSON()
                    : null,
            updatedOn:
                supplySalesRepresentative.updatedOn != null && supplySalesRepresentative.updatedOn.isValid()
                    ? supplySalesRepresentative.updatedOn.toJSON()
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
            res.body.forEach((supplySalesRepresentative: ISupplySalesRepresentative) => {
                supplySalesRepresentative.createdOn =
                    supplySalesRepresentative.createdOn != null ? moment(supplySalesRepresentative.createdOn) : null;
                supplySalesRepresentative.updatedOn =
                    supplySalesRepresentative.updatedOn != null ? moment(supplySalesRepresentative.updatedOn) : null;
            });
        }
        return res;
    }
}
