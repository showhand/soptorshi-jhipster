import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuotation } from 'app/shared/model/quotation.model';

type EntityResponseType = HttpResponse<IQuotation>;
type EntityArrayResponseType = HttpResponse<IQuotation[]>;

@Injectable({ providedIn: 'root' })
export class QuotationService {
    public resourceUrl = SERVER_API_URL + 'api/quotations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/quotations';

    constructor(protected http: HttpClient) {}

    create(quotation: IQuotation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(quotation);
        return this.http
            .post<IQuotation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(quotation: IQuotation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(quotation);
        return this.http
            .put<IQuotation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IQuotation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuotation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuotation[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(quotation: IQuotation): IQuotation {
        const copy: IQuotation = Object.assign({}, quotation, {
            modifiedOn: quotation.modifiedOn != null && quotation.modifiedOn.isValid() ? quotation.modifiedOn.format(DATE_FORMAT) : null
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
            res.body.forEach((quotation: IQuotation) => {
                quotation.modifiedOn = quotation.modifiedOn != null ? moment(quotation.modifiedOn) : null;
            });
        }
        return res;
    }
}
