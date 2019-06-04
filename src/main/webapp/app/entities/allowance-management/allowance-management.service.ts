import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { AllowanceManagement, IAllowanceManagement } from 'app/shared/model/allowance-management.model';

type EntityResponseType = HttpResponse<IAllowanceManagement>;
type EntityArrayResponseType = HttpResponse<IAllowanceManagement[]>;

@Injectable({ providedIn: 'root' })
export class AllowanceManagementService {
    public allowanceManagement: AllowanceManagement;
}
