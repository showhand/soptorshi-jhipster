import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITax } from 'app/shared/model/tax.model';

type EntityResponseType = HttpResponse<ITax>;
type EntityArrayResponseType = HttpResponse<ITax[]>;

@Injectable({ providedIn: 'root' })
export class TaxService {
    public resourceUrl = SERVER_API_URL + 'api/taxes';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/taxes';

    constructor(protected http: HttpClient) {}

    create(tax: ITax): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(tax);
        return this.http
            .post<ITax>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(tax: ITax): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(tax);
        return this.http
            .put<ITax>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITax>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITax[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITax[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(tax: ITax): ITax {
        const copy: ITax = Object.assign({}, tax, {
            modifiedOn: tax.modifiedOn != null && tax.modifiedOn.isValid() ? tax.modifiedOn.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((tax: ITax) => {
                tax.modifiedOn = tax.modifiedOn != null ? moment(tax.modifiedOn) : null;
            });
        }
        return res;
    }
}
