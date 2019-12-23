import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISalaryVoucherRelation } from 'app/shared/model/salary-voucher-relation.model';
import { SalaryVoucherRelationService } from 'app/entities/salary-voucher-relation';

type EntityResponseType = HttpResponse<ISalaryVoucherRelation>;
type EntityArrayResponseType = HttpResponse<ISalaryVoucherRelation[]>;

@Injectable({ providedIn: 'root' })
export class SalaryVoucherRelationExtendedService extends SalaryVoucherRelationService {
    public resourceUrlExtended = SERVER_API_URL + 'api/extended/salary-voucher-relations';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(salaryVoucherRelation: ISalaryVoucherRelation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(salaryVoucherRelation);
        return this.http
            .post<ISalaryVoucherRelation>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(salaryVoucherRelation: ISalaryVoucherRelation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(salaryVoucherRelation);
        return this.http
            .put<ISalaryVoucherRelation>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
