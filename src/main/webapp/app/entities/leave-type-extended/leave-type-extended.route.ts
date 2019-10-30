import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILeaveType, LeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeExtendedComponent } from 'app/entities/leave-type-extended/leave-type-extended.component';
import { LeaveTypeExtendedService } from 'app/entities/leave-type-extended/leave-type-extended.service';
import { LeaveTypeDetailExtendedComponent } from 'app/entities/leave-type-extended/leave-type-detail-extended.component';
import { LeaveTypeUpdateExtendedComponent } from 'app/entities/leave-type-extended/leave-type-update-extended.component';
import { LeaveTypeDeletePopupComponentExtended } from 'app/entities/leave-type-extended/leave-type-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class LeaveTypeResolveExtended implements Resolve<ILeaveType> {
    constructor(private service: LeaveTypeExtendedService) {}

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

export const leaveTypeExtendedRoute: Routes = [
    {
        path: '',
        component: LeaveTypeExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'LeaveTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LeaveTypeDetailExtendedComponent,
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
        component: LeaveTypeUpdateExtendedComponent,
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
        component: LeaveTypeUpdateExtendedComponent,
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
