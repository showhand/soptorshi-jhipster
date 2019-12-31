import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPurchaseOrderMessages } from 'app/shared/model/purchase-order-messages.model';
import { PurchaseOrderMessagesService } from 'app/entities/purchase-order-messages';

type EntityResponseType = HttpResponse<IPurchaseOrderMessages>;
type EntityArrayResponseType = HttpResponse<IPurchaseOrderMessages[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseOrderMessagesExtendedService extends PurchaseOrderMessagesService {
    constructor(protected http: HttpClient) {
        super(http);
    }
}
