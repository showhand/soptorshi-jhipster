import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Loan } from 'app/shared/model/loan.model';
import { LoanService } from './loan.service';
import { LoanComponent } from './loan.component';
import { LoanDetailComponent } from './loan-detail.component';
import { LoanUpdateComponent } from './loan-update.component';
import { LoanDeletePopupComponent } from './loan-delete-dialog.component';
import { ILoan } from 'app/shared/model/loan.model';

@Injectable({ providedIn: 'root' })
export class LoanResolve implements Resolve<ILoan> {
    constructor(private service: LoanService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILoan> {
        const id = route.params['id'] ? route.params['id'] : null;
        const employeeLongId = route.params['employeeLongId'] ? route.params['employeeLongId'] : null;
        const employeeId = route.params['employeeId'] ? route.params['employeeId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Loan>) => response.ok),
                map((loan: HttpResponse<Loan>) => loan.body)
            );
        } else if (employeeLongId) {
            const loan = new Loan();
            loan.employeeId = employeeLongId;
            return of(loan);
        } else if (employeeId) {
            const loan = new Loan();
            loan.employeeId = employeeId;
            return of(loan);
        }
        return of(new Loan());
    }
}

export const loanRoute: Routes = [
    {
        path: '',
        component: LoanComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'Loans'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeLongId/employee',
        component: LoanComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams,
            loan: LoanResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'Loans',
            breadcrumb: 'Employee Loan Information'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LoanDetailComponent,
        resolve: {
            loan: LoanResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'Loans',
            breadcrumb: 'Loan Details'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeId/new',
        component: LoanUpdateComponent,
        resolve: {
            loan: LoanResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'Loans',
            breadcrumb: 'New Loan for Employee'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LoanUpdateComponent,
        resolve: {
            loan: LoanResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'Loans',
            breadcrumb: 'New Loan'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LoanUpdateComponent,
        resolve: {
            loan: LoanResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'Loans',
            breadcrumb: 'Edit Loan'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const loanPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LoanDeletePopupComponent,
        resolve: {
            loan: LoanResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'Loans'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
