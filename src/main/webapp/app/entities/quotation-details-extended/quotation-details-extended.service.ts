import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuotationDetails } from 'app/shared/model/quotation-details.model';
import { QuotationDetailsService } from 'app/entities/quotation-details';

type EntityResponseType = HttpResponse<IQuotationDetails>;
type EntityArrayResponseType = HttpResponse<IQuotationDetails[]>;

@Injectable({ providedIn: 'root' })
export class QuotationDetailsExtendedService extends QuotationDetailsService {
    public resourceUrlExtended = SERVER_API_URL + 'api/extended/quotation-details';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(quotationDetails: IQuotationDetails): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(quotationDetails);
        return this.http
            .post<IQuotationDetails>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(quotationDetails: IQuotationDetails): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(quotationDetails);
        return this.http
            .put<IQuotationDetails>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
