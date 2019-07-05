import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPayrollManagement } from 'app/shared/model/payroll-management.model';

type EntityResponseType = HttpResponse<IPayrollManagement>;
type EntityArrayResponseType = HttpResponse<IPayrollManagement[]>;

@Injectable({ providedIn: 'root' })
export class PayrollManagementService {
    public payrollManagement: IPayrollManagement;
    public resourceUrl = SERVER_API_URL + 'api/payroll-managements';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/payroll-managements';

    constructor(protected http: HttpClient) {}
}
