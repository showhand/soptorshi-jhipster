import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILeaveType, LeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeService } from './leave-type.service';
import { LeaveTypeComponent } from './leave-type.component';
import { LeaveTypeDetailComponent } from './leave-type-detail.component';
import { LeaveTypeUpdateComponent } from './leave-type-update.component';
import { LeaveTypeDeletePopupComponent } from './leave-type-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class LeaveTypeResolve implements Resolve<ILeaveType> {
    constructor(private service: LeaveTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILeaveType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LeaveType>) => response.ok),
                map((leaveType: HttpResponse<LeaveType>) => leaveType.body)
            );
        }
        return of(new LeaveType());
    }
}

export const leaveTypeRoute: Routes = [
    {
        path: '',
        component: LeaveTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LeaveTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LeaveTypeDetailComponent,
        resolve: {
            leaveType: LeaveTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LeaveTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LeaveTypeUpdateComponent,
        resolve: {
            leaveType: LeaveTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LeaveTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LeaveTypeUpdateComponent,
        resolve: {
            leaveType: LeaveTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LeaveTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const leaveTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LeaveTypeDeletePopupComponent,
        resolve: {
            leaveType: LeaveTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LeaveTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
