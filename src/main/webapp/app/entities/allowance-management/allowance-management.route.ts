import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AllowanceManagement } from 'app/shared/model/allowance-management.model';
import { AllowanceManagementService } from './allowance-management.service';
import { AllowanceManagementComponent } from './allowance-management.component';
import { AllowanceManagementDetailComponent } from './allowance-management-detail.component';
import { AllowanceManagementUpdateComponent } from './allowance-management-update.component';
import { AllowanceManagementDeletePopupComponent } from './allowance-management-delete-dialog.component';
import { IAllowanceManagement } from 'app/shared/model/allowance-management.model';

@Injectable({ providedIn: 'root' })
export class AllowanceManagementResolve implements Resolve<IAllowanceManagement> {
    constructor(private service: AllowanceManagementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAllowanceManagement> {
        return of(new AllowanceManagement());
    }
}

export const allowanceManagementRoute: Routes = [
    {
        path: '',
        component: AllowanceManagementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AllowanceManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AllowanceManagementDetailComponent,
        resolve: {
            allowanceManagement: AllowanceManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AllowanceManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AllowanceManagementUpdateComponent,
        resolve: {
            allowanceManagement: AllowanceManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AllowanceManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AllowanceManagementUpdateComponent,
        resolve: {
            allowanceManagement: AllowanceManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AllowanceManagements'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const allowanceManagementPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AllowanceManagementDeletePopupComponent,
        resolve: {
            allowanceManagement: AllowanceManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AllowanceManagements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
