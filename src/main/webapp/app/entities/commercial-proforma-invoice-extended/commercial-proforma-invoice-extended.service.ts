import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommercialProformaInvoice } from 'app/shared/model/commercial-proforma-invoice.model';
import { CommercialProformaInvoiceService } from 'app/entities/commercial-proforma-invoice';

type EntityResponseType = HttpResponse<ICommercialProformaInvoice>;
type EntityArrayResponseType = HttpResponse<ICommercialProformaInvoice[]>;

@Injectable({ providedIn: 'root' })
export class CommercialProformaInvoiceExtendedService extends CommercialProformaInvoiceService {
    public resourceUrl = SERVER_API_URL + 'api/commercial-proforma-invoices';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-proforma-invoices';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(commercialProformaInvoice: ICommercialProformaInvoice): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialProformaInvoice);
        return this.http
            .post<ICommercialProformaInvoice>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(commercialProformaInvoice: ICommercialProformaInvoice): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialProformaInvoice);
        return this.http
            .put<ICommercialProformaInvoice>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICommercialProformaInvoice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialProformaInvoice[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialProformaInvoice[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(commercialProformaInvoice: ICommercialProformaInvoice): ICommercialProformaInvoice {
        const copy: ICommercialProformaInvoice = Object.assign({}, commercialProformaInvoice, {
            proformaDate:
                commercialProformaInvoice.proformaDate != null && commercialProformaInvoice.proformaDate.isValid()
                    ? commercialProformaInvoice.proformaDate.format(DATE_FORMAT)
                    : null,
            createOn:
                commercialProformaInvoice.createOn != null && commercialProformaInvoice.createOn.isValid()
                    ? commercialProformaInvoice.createOn.format(DATE_FORMAT)
                    : null,
            updatedOn:
                commercialProformaInvoice.updatedOn != null && commercialProformaInvoice.updatedOn.isValid()
                    ? commercialProformaInvoice.updatedOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.proformaDate = res.body.proformaDate != null ? moment(res.body.proformaDate) : null;
            res.body.createOn = res.body.createOn != null ? moment(res.body.createOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((commercialProformaInvoice: ICommercialProformaInvoice) => {
                commercialProformaInvoice.proformaDate =
                    commercialProformaInvoice.proformaDate != null ? moment(commercialProformaInvoice.proformaDate) : null;
                commercialProformaInvoice.createOn =
                    commercialProformaInvoice.createOn != null ? moment(commercialProformaInvoice.createOn) : null;
                commercialProformaInvoice.updatedOn =
                    commercialProformaInvoice.updatedOn != null ? moment(commercialProformaInvoice.updatedOn) : null;
            });
        }
        return res;
    }
}
