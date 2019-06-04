import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LoanManagement } from 'app/shared/model/loan-management.model';
import { LoanManagementService } from './loan-management.service';
import { LoanManagementComponent } from './loan-management.component';
import { LoanManagementDetailComponent } from './loan-management-detail.component';
import { LoanManagementUpdateComponent } from './loan-management-update.component';
import { LoanManagementDeletePopupComponent } from './loan-management-delete-dialog.component';
import { ILoanManagement } from 'app/shared/model/loan-management.model';

@Injectable({ providedIn: 'root' })
export class LoanManagementResolve implements Resolve<ILoanManagement> {
    constructor(private service: LoanManagementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILoanManagement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LoanManagement>) => response.ok),
                map((loanManagement: HttpResponse<LoanManagement>) => loanManagement.body)
            );
        }
        return of(new LoanManagement());
    }
}

export const loanManagementRoute: Routes = [
    {
        path: '',
        component: LoanManagementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LoanManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LoanManagementDetailComponent,
        resolve: {
            loanManagement: LoanManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LoanManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LoanManagementUpdateComponent,
        resolve: {
            loanManagement: LoanManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LoanManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LoanManagementUpdateComponent,
        resolve: {
            loanManagement: LoanManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LoanManagements'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const loanManagementPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LoanManagementDeletePopupComponent,
        resolve: {
            loanManagement: LoanManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LoanManagements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
