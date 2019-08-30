import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuotation } from 'app/shared/model/quotation.model';
import { QuotationService } from 'app/entities/quotation';

type EntityResponseType = HttpResponse<IQuotation>;
type EntityArrayResponseType = HttpResponse<IQuotation[]>;

@Injectable({ providedIn: 'root' })
export class QuotationExtendedService extends QuotationService {
    public resourceUrl = SERVER_API_URL + 'api/extended/quotations';

    constructor(protected http: HttpClient) {
        super(http);
    }

    update(quotation: IQuotation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(quotation);
        return this.http
            .put<IQuotation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
