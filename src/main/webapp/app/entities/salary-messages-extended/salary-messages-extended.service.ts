import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISalaryMessages } from 'app/shared/model/salary-messages.model';
import { SalaryMessagesService } from 'app/entities/salary-messages';
import { map } from 'rxjs/operators';

type EntityResponseType = HttpResponse<ISalaryMessages>;
type EntityArrayResponseType = HttpResponse<ISalaryMessages[]>;

@Injectable({ providedIn: 'root' })
export class SalaryMessagesExtendedService extends SalaryMessagesService {
    public resourceUrlExtended = SERVER_API_URL + 'api/extended/salary-messages';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(salaryMessages: ISalaryMessages): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(salaryMessages);
        return this.http
            .post<ISalaryMessages>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(salaryMessages: ISalaryMessages): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(salaryMessages);
        return this.http
            .put<ISalaryMessages>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
