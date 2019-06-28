import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { map } from 'rxjs/operators';
import { ILeaveBalance } from 'app/shared/model/leave-balance.model';

type EntityResponseType = HttpResponse<ILeaveBalance>;
type EntityArrayResponseType = HttpResponse<ILeaveBalance[]>;

@Injectable({
    providedIn: 'root'
})
export class LeaveBalanceService {
    public resourceUrl = SERVER_API_URL + 'api/leave-balance';

    constructor(protected http: HttpClient) {}

    find(employeeId: string, queryYear: number): Observable<EntityArrayResponseType> {
        return this.http.get<ILeaveBalance[]>(`${this.resourceUrl}/employee/${employeeId}/year/${queryYear}`, { observe: 'response' });
    }

    getOne(employeeId: string, queryYear: number, leaveType: number): Observable<EntityResponseType> {
        return this.http.get<ILeaveBalance>(`${this.resourceUrl}/employee/${employeeId}/year/${queryYear}/leave-type/${leaveType}`, {
            observe: 'response'
        });
    }
}
