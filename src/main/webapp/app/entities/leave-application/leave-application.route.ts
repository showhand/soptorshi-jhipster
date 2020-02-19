import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILeaveApplication, LeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationService } from './leave-application.service';
import { LeaveApplicationComponent } from './leave-application.component';
import { LeaveApplicationDetailComponent } from './leave-application-detail.component';
import { LeaveApplicationUpdateComponent } from './leave-application-update.component';
import { LeaveApplicationDeletePopupComponent } from './leave-application-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class LeaveApplicationResolve implements Resolve<ILeaveApplication> {
    constructor(private service: LeaveApplicationService) {}

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

export const leaveApplicationRoute: Routes = [
    {
        path: '',
        component: LeaveApplicationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LeaveApplicationDetailComponent,
        resolve: {
            leaveApplication: LeaveApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LeaveApplicationUpdateComponent,
        resolve: {
            leaveApplication: LeaveApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LeaveApplicationUpdateComponent,
        resolve: {
            leaveApplication: LeaveApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const leaveApplicationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LeaveApplicationDeletePopupComponent,
        resolve: {
            leaveApplication: LeaveApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
