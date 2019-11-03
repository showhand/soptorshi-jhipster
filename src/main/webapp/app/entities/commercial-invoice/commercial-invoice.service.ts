import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommercialInvoice } from 'app/shared/model/commercial-invoice.model';

type EntityResponseType = HttpResponse<ICommercialInvoice>;
type EntityArrayResponseType = HttpResponse<ICommercialInvoice[]>;

@Injectable({ providedIn: 'root' })
export class CommercialInvoiceService {
    public resourceUrl = SERVER_API_URL + 'api/commercial-invoices';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-invoices';

    constructor(protected http: HttpClient) {}

    create(commercialInvoice: ICommercialInvoice): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialInvoice);
        return this.http
            .post<ICommercialInvoice>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(commercialInvoice: ICommercialInvoice): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialInvoice);
        return this.http
            .put<ICommercialInvoice>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICommercialInvoice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialInvoice[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialInvoice[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(commercialInvoice: ICommercialInvoice): ICommercialInvoice {
        const copy: ICommercialInvoice = Object.assign({}, commercialInvoice, {
            createOn:
                commercialInvoice.createOn != null && commercialInvoice.createOn.isValid()
                    ? commercialInvoice.createOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createOn = res.body.createOn != null ? moment(res.body.createOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((commercialInvoice: ICommercialInvoice) => {
                commercialInvoice.createOn = commercialInvoice.createOn != null ? moment(commercialInvoice.createOn) : null;
            });
        }
        return res;
    }
}
