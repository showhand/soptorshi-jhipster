import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LeaveApplication } from 'app/shared/model/leave-application.model';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { OthersLeaveApplicationComponent } from 'app/entities/leave-application/others-leave-application.component';
import { ReviewLeaveApplicationComponent } from 'app/entities/leave-application/review-leave-application.component';
import { LeaveBalanceComponent } from 'app/entities/leave-balance/leave-balance.component';
import { OthersLeaveApplicationHistoryComponent } from 'app/entities/leave-application/others-leave-application-history.component';
import { LeaveApplicationServiceExtended } from 'app/entities/leave-application-extended/leave-application.service.extended';
import { LeaveApplicationComponentExtended } from 'app/entities/leave-application-extended/leave-application.component.extended';
import { LeaveApplicationDetailComponentExtended } from 'app/entities/leave-application-extended/leave-application-detail.component.extended';
import { LeaveApplicationUpdateComponentExtended } from 'app/entities/leave-application-extended/leave-application-update.component.extended';
import { LeaveApplicationDeletePopupComponentExtended } from 'app/entities/leave-application-extended/leave-application-delete-dialog.component.extended';

@Injectable({ providedIn: 'root' })
export class LeaveApplicationResolveExtended implements Resolve<ILeaveApplication> {
    constructor(private service: LeaveApplicationServiceExtended) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILeaveApplication> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LeaveApplication>) => response.ok),
                map((leaveApplication: HttpResponse<LeaveApplication>) => leaveApplication.body)
            );
        }
        return of(new LeaveApplication());
    }
}

export const leaveApplicationRouteExtended: Routes = [
    {
        path: '',
        component: LeaveApplicationComponentExtended,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_USER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LeaveApplicationDetailComponentExtended,
        resolve: {
            leaveApplication: LeaveApplicationResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_USER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LeaveApplicationUpdateComponentExtended,
        resolve: {
            leaveApplication: LeaveApplicationResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_USER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new/others',
        component: OthersLeaveApplicationComponent,
        resolve: {
            leaveApplication: LeaveApplicationResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_MANAGER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LeaveApplicationUpdateComponentExtended,
        resolve: {
            leaveApplication: LeaveApplicationResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_USER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'review',
        component: ReviewLeaveApplicationComponent,
        resolve: {
            leaveApplication: LeaveApplicationResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_MANAGER'],
            pageTitle: 'ReviewLeaveApplication'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'history/others',
        component: OthersLeaveApplicationHistoryComponent,
        resolve: {
            leaveApplication: LeaveApplicationResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_MANAGER'],
            pageTitle: 'LeaveHistory'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const leaveApplicationPopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: LeaveApplicationDeletePopupComponentExtended,
        resolve: {
            leaveApplication: LeaveApplicationResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_USER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
