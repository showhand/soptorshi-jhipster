import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LeaveType } from 'app/shared/model/leave-type.model';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeDeletePopupComponentExtended } from 'app/entities/leave-type-extended/leave-type-delete-dialog.component.extended';
import { LeaveTypeServiceExtended } from 'app/entities/leave-type-extended/leave-type.service.extended';
import { LeaveTypeComponentExtended } from 'app/entities/leave-type-extended/leave-type.component.extended';
import { LeaveTypeDetailComponentExtended } from 'app/entities/leave-type-extended/leave-type-detail.component.extended';
import { LeaveTypeUpdateComponentExtended } from 'app/entities/leave-type-extended/leave-type-update.component.extended';

@Injectable({ providedIn: 'root' })
export class LeaveTypeResolveExtended implements Resolve<ILeaveType> {
    constructor(private service: LeaveTypeServiceExtended) {}

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

export const leaveTypeRouteExtended: Routes = [
    {
        path: '',
        component: LeaveTypeComponentExtended,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'LeaveTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LeaveTypeDetailComponentExtended,
        resolve: {
            leaveType: LeaveTypeResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'LeaveTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LeaveTypeUpdateComponentExtended,
        resolve: {
            leaveType: LeaveTypeResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'LeaveTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LeaveTypeUpdateComponentExtended,
        resolve: {
            leaveType: LeaveTypeResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'LeaveTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const leaveTypePopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: LeaveTypeDeletePopupComponentExtended,
        resolve: {
            leaveType: LeaveTypeResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'LeaveTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
