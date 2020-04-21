import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPurchaseOrderVoucherRelation } from 'app/shared/model/purchase-order-voucher-relation.model';
import { PurchaseOrderVoucherRelationService } from 'app/entities/purchase-order-voucher-relation';

type EntityResponseType = HttpResponse<IPurchaseOrderVoucherRelation>;
type EntityArrayResponseType = HttpResponse<IPurchaseOrderVoucherRelation[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseOrderVoucherRelationExtendedService extends PurchaseOrderVoucherRelationService {
    public resourceUrl = SERVER_API_URL + 'api/purchase-order-voucher-relations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/purchase-order-voucher-relations';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
