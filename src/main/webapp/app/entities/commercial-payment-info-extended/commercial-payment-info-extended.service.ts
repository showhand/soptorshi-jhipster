import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommercialPaymentInfo } from 'app/shared/model/commercial-payment-info.model';
import { CommercialPaymentInfoService } from 'app/entities/commercial-payment-info';

type EntityResponseType = HttpResponse<ICommercialPaymentInfo>;
type EntityArrayResponseType = HttpResponse<ICommercialPaymentInfo[]>;

@Injectable({ providedIn: 'root' })
export class CommercialPaymentInfoExtendedService extends CommercialPaymentInfoService {
    public resourceUrl = SERVER_API_URL + 'api/extended/commercial-payment-infos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-payment-infos';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(commercialPaymentInfo: ICommercialPaymentInfo): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialPaymentInfo);
        return this.http
            .post<ICommercialPaymentInfo>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(commercialPaymentInfo: ICommercialPaymentInfo): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialPaymentInfo);
        return this.http
            .put<ICommercialPaymentInfo>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICommercialPaymentInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialPaymentInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialPaymentInfo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(commercialPaymentInfo: ICommercialPaymentInfo): ICommercialPaymentInfo {
        const copy: ICommercialPaymentInfo = Object.assign({}, commercialPaymentInfo, {
            createOn:
                commercialPaymentInfo.createOn != null && commercialPaymentInfo.createOn.isValid()
                    ? commercialPaymentInfo.createOn.format(DATE_FORMAT)
                    : null,
            updatedOn:
                commercialPaymentInfo.updatedOn != null && commercialPaymentInfo.updatedOn.isValid()
                    ? commercialPaymentInfo.updatedOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createOn = res.body.createOn != null ? moment(res.body.createOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((commercialPaymentInfo: ICommercialPaymentInfo) => {
                commercialPaymentInfo.createOn = commercialPaymentInfo.createOn != null ? moment(commercialPaymentInfo.createOn) : null;
                commercialPaymentInfo.updatedOn = commercialPaymentInfo.updatedOn != null ? moment(commercialPaymentInfo.updatedOn) : null;
            });
        }
        return res;
    }
}
