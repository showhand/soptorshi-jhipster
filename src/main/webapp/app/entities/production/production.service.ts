import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProduction } from 'app/shared/model/production.model';

type EntityResponseType = HttpResponse<IProduction>;
type EntityArrayResponseType = HttpResponse<IProduction[]>;

@Injectable({ providedIn: 'root' })
export class ProductionService {
    public resourceUrl = SERVER_API_URL + 'api/productions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/productions';

    constructor(protected http: HttpClient) {}

    create(production: IProduction): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(production);
        return this.http
            .post<IProduction>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(production: IProduction): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(production);
        return this.http
            .put<IProduction>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProduction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProduction[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProduction[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(production: IProduction): IProduction {
        const copy: IProduction = Object.assign({}, production, {
            createdOn: production.createdOn != null && production.createdOn.isValid() ? production.createdOn.toJSON() : null,
            updatedOn: production.updatedOn != null && production.updatedOn.isValid() ? production.updatedOn.toJSON() : null
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
            res.body.forEach((production: IProduction) => {
                production.createdOn = production.createdOn != null ? moment(production.createdOn) : null;
                production.updatedOn = production.updatedOn != null ? moment(production.updatedOn) : null;
            });
        }
        return res;
    }
}
