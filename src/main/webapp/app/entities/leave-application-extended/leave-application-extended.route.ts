import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILeaveApplication, LeaveApplication } from 'app/shared/model/leave-application.model';
import { OthersLeaveApplicationComponent } from 'app/entities/leave-application/others-leave-application.component';
import { ReviewLeaveApplicationComponent } from 'app/entities/leave-application/review-leave-application.component';
import { OthersLeaveApplicationHistoryComponent } from 'app/entities/leave-application/others-leave-application-history.component';
import { LeaveApplicationExtendedService } from 'app/entities/leave-application-extended/leave-application-extended.service';
import { LeaveApplicationExtendedComponent } from 'app/entities/leave-application-extended/leave-application-extended.component';
import { LeaveApplicationDetailExtendedComponent } from 'app/entities/leave-application-extended/leave-application-detail-extended.component';
import { LeaveApplicationUpdateExtendedComponent } from 'app/entities/leave-application-extended/leave-application-update-extended.component';
import { LeaveApplicationDeletePopupComponentExtended } from 'app/entities/leave-application-extended/leave-application-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class LeaveApplicationResolveExtended implements Resolve<ILeaveApplication> {
    constructor(private service: LeaveApplicationExtendedService) {}

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

export const leaveApplicationExtendedRoute: Routes = [
    {
        path: '',
        component: LeaveApplicationExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_USER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LeaveApplicationDetailExtendedComponent,
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
        component: LeaveApplicationUpdateExtendedComponent,
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
        component: LeaveApplicationUpdateExtendedComponent,
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
