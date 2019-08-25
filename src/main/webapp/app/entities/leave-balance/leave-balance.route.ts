import { Injectable, NgModule } from '@angular/core';
import { Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { ILeaveApplication, LeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationComponent, LeaveApplicationService } from 'app/entities/leave-application';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { ILeaveBalance, LeaveBalance } from 'app/shared/model/leave-balance.model';
import { LeaveBalanceService } from 'app/entities/leave-balance/leave-balance.service';
import { UserRouteAccessService } from 'app/core';
import { LeaveBalanceComponent } from 'app/entities/leave-balance/leave-balance.component';
import { OthersLeaveBalanceComponent } from 'app/entities/leave-balance/others-leave-balance.component';
import * as moment from 'moment';

@Injectable({ providedIn: 'root' })
export class LeaveBalanceRoute implements Resolve<ILeaveBalance[]> {
    constructor(private service: LeaveBalanceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILeaveBalance[]> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id, moment().get('year')).pipe(
                filter((response: HttpResponse<LeaveBalance[]>) => response.ok),
                map((leaveBalance: HttpResponse<LeaveBalance[]>) => leaveBalance.body)
            );
        }
        return of(Array<LeaveBalance>());
    }
}

export const leaveBalanceRoute: Routes = [
    {
        path: '',
        component: LeaveBalanceComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_APPLICATION'],
            pageTitle: 'LeaveBalance'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'balance',
        component: LeaveBalanceComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_APPLICATION'],
            pageTitle: 'LeaveBalance'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'balance/others',
        component: OthersLeaveBalanceComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_OTHERS_LEAVE_APPLICATION'],
            pageTitle: 'LeaveBalance'
        },
        canActivate: [UserRouteAccessService]
    }
];
