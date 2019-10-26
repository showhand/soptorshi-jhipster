import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJournalVoucher } from 'app/shared/model/journal-voucher.model';
import { JournalVoucherService } from 'app/entities/journal-voucher';

type EntityResponseType = HttpResponse<IJournalVoucher>;
type EntityArrayResponseType = HttpResponse<IJournalVoucher[]>;

@Injectable({ providedIn: 'root' })
export class JournalVoucherExtendedService extends JournalVoucherService {
    public resourceUrlExtended = SERVER_API_URL + 'api/extended/journal-vouchers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/journal-vouchers';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
