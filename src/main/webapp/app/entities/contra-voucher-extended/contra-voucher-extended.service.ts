import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContraVoucher } from 'app/shared/model/contra-voucher.model';
import { ContraVoucherService } from 'app/entities/contra-voucher';

type EntityResponseType = HttpResponse<IContraVoucher>;
type EntityArrayResponseType = HttpResponse<IContraVoucher[]>;

@Injectable({ providedIn: 'root' })
export class ContraVoucherExtendedService extends ContraVoucherService {
    public resourceUrlExtended = SERVER_API_URL + 'api/extended/contra-vouchers';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(contraVoucher: IContraVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contraVoucher);
        return this.http
            .post<IContraVoucher>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(contraVoucher: IContraVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contraVoucher);
        return this.http
            .put<IContraVoucher>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    findByVoucherNo(voucherNo: string): Observable<EntityResponseType> {
        return this.http
            .get<IContraVoucher>(`${this.resourceUrl}/voucherNo/${voucherNo}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
