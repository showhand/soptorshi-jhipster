import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PayrollManagement } from 'app/shared/model/payroll-management.model';
import { PayrollManagementService } from './payroll-management.service';
import { PayrollManagementComponent } from './payroll-management.component';
import { PayrollManagementDetailComponent } from './payroll-management-detail.component';
import { PayrollManagementUpdateComponent } from './payroll-management-update.component';
import { PayrollManagementDeletePopupComponent } from './payroll-management-delete-dialog.component';
import { IPayrollManagement } from 'app/shared/model/payroll-management.model';

@Injectable({ providedIn: 'root' })
export class PayrollManagementResolve implements Resolve<IPayrollManagement> {
    constructor(private service: PayrollManagementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPayrollManagement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PayrollManagement>) => response.ok),
                map((payrollManagement: HttpResponse<PayrollManagement>) => payrollManagement.body)
            );
        }
        return of(new PayrollManagement());
    }
}

export const payrollManagementRoute: Routes = [
    {
        path: '',
        component: PayrollManagementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PayrollManagementDetailComponent,
        resolve: {
            payrollManagement: PayrollManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PayrollManagementUpdateComponent,
        resolve: {
            payrollManagement: PayrollManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PayrollManagementUpdateComponent,
        resolve: {
            payrollManagement: PayrollManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollManagements'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const payrollManagementPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PayrollManagementDeletePopupComponent,
        resolve: {
            payrollManagement: PayrollManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollManagements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
