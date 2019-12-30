import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';
import { RequisitionVoucherRelationService } from 'app/entities/requisition-voucher-relation';

type EntityResponseType = HttpResponse<IRequisitionVoucherRelation>;
type EntityArrayResponseType = HttpResponse<IRequisitionVoucherRelation[]>;

@Injectable({ providedIn: 'root' })
export class RequisitionVoucherRelationExtendedService extends RequisitionVoucherRelationService {
    public resourceExtendedUrl = SERVER_API_URL + 'api/extended/requisition-voucher-relations';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(requisitionVoucherRelation: IRequisitionVoucherRelation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(requisitionVoucherRelation);
        return this.http
            .post<IRequisitionVoucherRelation>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(requisitionVoucherRelation: IRequisitionVoucherRelation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(requisitionVoucherRelation);
        return this.http
            .put<IRequisitionVoucherRelation>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
