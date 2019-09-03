import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuotationDetails } from 'app/shared/model/quotation-details.model';

type EntityResponseType = HttpResponse<IQuotationDetails>;
type EntityArrayResponseType = HttpResponse<IQuotationDetails[]>;

@Injectable({ providedIn: 'root' })
export class QuotationDetailsService {
    public resourceUrl = SERVER_API_URL + 'api/quotation-details';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/quotation-details';

    constructor(protected http: HttpClient) {}

    create(quotationDetails: IQuotationDetails): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(quotationDetails);
        return this.http
            .post<IQuotationDetails>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(quotationDetails: IQuotationDetails): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(quotationDetails);
        return this.http
            .put<IQuotationDetails>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IQuotationDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuotationDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuotationDetails[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(quotationDetails: IQuotationDetails): IQuotationDetails {
        const copy: IQuotationDetails = Object.assign({}, quotationDetails, {
            estimatedDate:
                quotationDetails.estimatedDate != null && quotationDetails.estimatedDate.isValid()
                    ? quotationDetails.estimatedDate.format(DATE_FORMAT)
                    : null,
            modifiedOn:
                quotationDetails.modifiedOn != null && quotationDetails.modifiedOn.isValid()
                    ? quotationDetails.modifiedOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.estimatedDate = res.body.estimatedDate != null ? moment(res.body.estimatedDate) : null;
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((quotationDetails: IQuotationDetails) => {
                quotationDetails.estimatedDate = quotationDetails.estimatedDate != null ? moment(quotationDetails.estimatedDate) : null;
                quotationDetails.modifiedOn = quotationDetails.modifiedOn != null ? moment(quotationDetails.modifiedOn) : null;
            });
        }
        return res;
    }
}
