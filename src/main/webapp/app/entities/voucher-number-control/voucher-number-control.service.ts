import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVoucherNumberControl } from 'app/shared/model/voucher-number-control.model';

type EntityResponseType = HttpResponse<IVoucherNumberControl>;
type EntityArrayResponseType = HttpResponse<IVoucherNumberControl[]>;

@Injectable({ providedIn: 'root' })
export class VoucherNumberControlService {
    public resourceUrl = SERVER_API_URL + 'api/voucher-number-controls';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/voucher-number-controls';

    constructor(protected http: HttpClient) {}

    create(voucherNumberControl: IVoucherNumberControl): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(voucherNumberControl);
        return this.http
            .post<IVoucherNumberControl>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(voucherNumberControl: IVoucherNumberControl): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(voucherNumberControl);
        return this.http
            .put<IVoucherNumberControl>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IVoucherNumberControl>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IVoucherNumberControl[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IVoucherNumberControl[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(voucherNumberControl: IVoucherNumberControl): IVoucherNumberControl {
        const copy: IVoucherNumberControl = Object.assign({}, voucherNumberControl, {
            modifiedOn:
                voucherNumberControl.modifiedOn != null && voucherNumberControl.modifiedOn.isValid()
                    ? voucherNumberControl.modifiedOn.format(DATE_FORMAT)
                    : null
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
            res.body.forEach((voucherNumberControl: IVoucherNumberControl) => {
                voucherNumberControl.modifiedOn = voucherNumberControl.modifiedOn != null ? moment(voucherNumberControl.modifiedOn) : null;
            });
        }
        return res;
    }
}
