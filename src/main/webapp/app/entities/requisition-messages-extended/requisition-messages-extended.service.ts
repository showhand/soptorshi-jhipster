import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRequisitionMessages } from 'app/shared/model/requisition-messages.model';
import { RequisitionMessagesService } from 'app/entities/requisition-messages';

type EntityResponseType = HttpResponse<IRequisitionMessages>;
type EntityArrayResponseType = HttpResponse<IRequisitionMessages[]>;

@Injectable({ providedIn: 'root' })
export class RequisitionMessagesExtendedService extends RequisitionMessagesService {
    public resourceExtendedUrl = SERVER_API_URL + 'api/extended/requisition-messages';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(requisitionMessages: IRequisitionMessages): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(requisitionMessages);
        return this.http
            .post<IRequisitionMessages>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(requisitionMessages: IRequisitionMessages): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(requisitionMessages);
        return this.http
            .put<IRequisitionMessages>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
