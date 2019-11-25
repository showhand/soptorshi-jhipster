import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISalaryMessages } from 'app/shared/model/salary-messages.model';
import { SalaryMessagesService } from 'app/entities/salary-messages';

type EntityResponseType = HttpResponse<ISalaryMessages>;
type EntityArrayResponseType = HttpResponse<ISalaryMessages[]>;

@Injectable({ providedIn: 'root' })
export class SalaryMessagesExtendedService extends SalaryMessagesService {
    constructor(protected http: HttpClient) {
        super(http);
    }
}
