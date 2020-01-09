import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';

type EntityResponseType = HttpResponse<IRequisition>;
type EntityArrayResponseType = HttpResponse<IRequisition[]>;

@Injectable({ providedIn: 'root' })
export class RequisitionExtendedService extends RequisitionService {
    public resourceExtendedUrl = SERVER_API_URL + 'api/extended/requisitions';

    public requisitionDetailsPage = 1;
    public requisitionDetailsPreviousPage = 0;
    public requisitionDetailsReverse = 'asc';
    public requisitionDetailsPredicate = 'id';

    public quotationPage = 1;
    public quotationPreviousPage = 0;
    public quotationReverse = 'asc';
    public quotationPredicate = 'id';

    constructor(protected http: HttpClient) {
        super(http);
    }
    create(requisition: IRequisition): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(requisition);
        return this.http
            .post<IRequisition>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(requisition: IRequisition): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(requisition);
        return this.http
            .put<IRequisition>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
