import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommercialInvoice } from 'app/shared/model/commercial-invoice.model';
import { CommercialInvoiceService } from 'app/entities/commercial-invoice';

type EntityResponseType = HttpResponse<ICommercialInvoice>;
type EntityArrayResponseType = HttpResponse<ICommercialInvoice[]>;

@Injectable({ providedIn: 'root' })
export class CommercialInvoiceExtendedService extends CommercialInvoiceService {
    public resourceUrl = SERVER_API_URL + 'api/extended/commercial-invoices';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-invoices';

    constructor(protected http: HttpClient) {
        super(http);
    }

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
            createdOn:
                commercialInvoice.createdOn != null && commercialInvoice.createdOn.isValid()
                    ? commercialInvoice.createdOn.format(DATE_FORMAT)
                    : null,
            updatedOn:
                commercialInvoice.updatedOn != null && commercialInvoice.updatedOn.isValid()
                    ? commercialInvoice.updatedOn.format(DATE_FORMAT)
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
            res.body.forEach((commercialInvoice: ICommercialInvoice) => {
                commercialInvoice.createdOn = commercialInvoice.createdOn != null ? moment(commercialInvoice.createdOn) : null;
                commercialInvoice.updatedOn = commercialInvoice.updatedOn != null ? moment(commercialInvoice.updatedOn) : null;
            });
        }
        return res;
    }
}
