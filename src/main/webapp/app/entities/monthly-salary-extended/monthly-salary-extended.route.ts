import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MonthlySalary } from 'app/shared/model/monthly-salary.model';

import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';
import { MonthlySalaryDeletePopupComponent, MonthlySalaryResolve, MonthlySalaryService } from 'app/entities/monthly-salary';
import { MonthlySalaryExtendedComponent } from 'app/entities/monthly-salary-extended/monthly-salary-extended.component';
import { MonthlySalaryExtendedDetailComponent } from 'app/entities/monthly-salary-extended/monthly-salary-extended-detail.component';
import { MonthlySalaryExtendedUpdateComponent } from 'app/entities/monthly-salary-extended/monthly-salary-extended-update.component';

@Injectable({ providedIn: 'root' })
export class MonthlySalaryExtendedResolve implements Resolve<IMonthlySalary> {
    constructor(public service: MonthlySalaryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMonthlySalary> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MonthlySalary>) => response.ok),
                map((monthlySalary: HttpResponse<MonthlySalary>) => monthlySalary.body)
            );
        }
        return of(new MonthlySalary());
    }
}

export const monthlySalaryExtendedRoute: Routes = [
    {
        path: '',
        component: MonthlySalaryExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'MonthlySalaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MonthlySalaryExtendedDetailComponent,
        resolve: {
            monthlySalary: MonthlySalaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlySalaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MonthlySalaryExtendedUpdateComponent,
        resolve: {
            monthlySalary: MonthlySalaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlySalaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MonthlySalaryExtendedUpdateComponent,
        resolve: {
            monthlySalary: MonthlySalaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlySalaries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const monthlySalaryExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MonthlySalaryDeletePopupComponent,
        resolve: {
            monthlySalary: MonthlySalaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlySalaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
