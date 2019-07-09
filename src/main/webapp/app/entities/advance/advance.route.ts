import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Advance } from 'app/shared/model/advance.model';
import { AdvanceService } from './advance.service';
import { AdvanceComponent } from './advance.component';
import { AdvanceDetailComponent } from './advance-detail.component';
import { AdvanceUpdateComponent } from './advance-update.component';
import { AdvanceDeletePopupComponent } from './advance-delete-dialog.component';
import { IAdvance } from 'app/shared/model/advance.model';
import { Loan } from 'app/shared/model/loan.model';
import { LoanResolve } from 'app/entities/loan';

@Injectable({ providedIn: 'root' })
export class AdvanceResolve implements Resolve<IAdvance> {
    constructor(private service: AdvanceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAdvance> {
        const id = route.params['id'] ? route.params['id'] : null;
        const employeeLongId = route.params['employeeLongId'] ? route.params['employeeLongId'] : null;
        const employeeId = route.params['employeeId'] ? route.params['employeeId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Advance>) => response.ok),
                map((advance: HttpResponse<Advance>) => advance.body)
            );
        } else if (employeeLongId) {
            const advance = new Advance();
            advance.employeeId = employeeLongId;
            return of(advance);
        } else if (employeeId) {
            const advance = new Advance();
            advance.employeeId = employeeId;
            return of(advance);
        }
        return of(new Advance());
    }
}

export const advanceRoute: Routes = [
    {
        path: '',
        component: AdvanceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'Advances',
            breadcrumb: 'Employee Advance'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeLongId/employee',
        component: AdvanceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams,
            advance: AdvanceResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'Advances',
            breadcrumb: 'Employee Advance Information'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AdvanceDetailComponent,
        resolve: {
            advance: AdvanceResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'Advances',
            breadcrumb: 'Employee Advance Details'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeId/new',
        component: AdvanceUpdateComponent,
        resolve: {
            advance: AdvanceResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'Advances',
            breadcrumb: 'Employee New Advance'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AdvanceUpdateComponent,
        resolve: {
            advance: AdvanceResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'Advances',
            breadcrumb: 'Employee New Advance'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AdvanceUpdateComponent,
        resolve: {
            advance: AdvanceResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'Advances',
            breadcrumb: 'Edit Employee Advance'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const advancePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AdvanceDeletePopupComponent,
        resolve: {
            advance: AdvanceResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'Advances'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
