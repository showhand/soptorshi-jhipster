import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVoucherNumberControl } from 'app/shared/model/voucher-number-control.model';
import { VoucherNumberControlService } from 'app/entities/voucher-number-control';

type EntityResponseType = HttpResponse<IVoucherNumberControl>;
type EntityArrayResponseType = HttpResponse<IVoucherNumberControl[]>;

@Injectable({ providedIn: 'root' })
export class VoucherNumberControlExtendedService extends VoucherNumberControlService {
    public resourceExtendedUrl = SERVER_API_URL + 'api/extended/voucher-number-controls';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(voucherNumberControl: IVoucherNumberControl): Observable<EntityResponseType> {
        return this.http.post<IVoucherNumberControl>(this.resourceExtendedUrl, voucherNumberControl, { observe: 'response' });
    }

    update(voucherNumberControl: IVoucherNumberControl): Observable<EntityResponseType> {
        return this.http.put<IVoucherNumberControl>(this.resourceExtendedUrl, voucherNumberControl, { observe: 'response' });
    }
}
