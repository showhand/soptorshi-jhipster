import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeService } from 'app/entities/leave-type';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';

type EntityResponseType = HttpResponse<ILeaveType>;
type EntityArrayResponseType = HttpResponse<ILeaveType[]>;

@Injectable({ providedIn: 'root' })
export class LeaveTypeExtendedService extends LeaveTypeService {
    public resourceUrl = SERVER_API_URL + 'api/extended/leave-types';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/leave-types';

    constructor(protected http: HttpClient) {
        super(http);
    }

    generateReport() {
        return this.http
            .get(`${this.resourceUrl}/report/all`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', 'Leave Types');
            });
    }
}
