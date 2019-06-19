import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Salary } from 'app/shared/model/salary.model';
import { SalaryService } from './salary.service';
import { SalaryComponent } from './salary.component';
import { SalaryDetailComponent } from './salary-detail.component';
import { SalaryUpdateComponent } from './salary-update.component';
import { SalaryDeletePopupComponent } from './salary-delete-dialog.component';
import { ISalary } from 'app/shared/model/salary.model';
import { IProvidentFund, ProvidentFund } from 'app/shared/model/provident-fund.model';

@Injectable({ providedIn: 'root' })
export class SalaryResolve implements Resolve<ISalary> {
    constructor(private service: SalaryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISalary> {
        const id = route.params['id'] ? route.params['id'] : null;
        const employeeLongId = route.params['employeeLongId'] ? route.params['employeeLongId'] : null;
        const employeeId = route.params['employeeId'] ? route.params['employeeId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Salary>) => response.ok),
                map((salary: HttpResponse<Salary>) => salary.body)
            );
        } else if (employeeLongId) {
            const salary: ISalary = new Salary();
            salary.employeeId = employeeLongId;
            return of(salary);
        } else if (employeeId) {
            const salary: ISalary = new Salary();
            salary.employeeId = employeeId;
            return of(salary);
        }
        return of(new Salary());
    }
}

export const salaryRoute: Routes = [
    {
        path: '',
        component: SalaryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'Salaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeLongId/employee',
        component: SalaryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams,
            salary: SalaryResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'Salaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SalaryDetailComponent,
        resolve: {
            salary: SalaryResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Salaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeId/new',
        component: SalaryUpdateComponent,
        resolve: {
            salary: SalaryResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Salaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SalaryUpdateComponent,
        resolve: {
            salary: SalaryResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Salaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SalaryUpdateComponent,
        resolve: {
            salary: SalaryResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Salaries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const salaryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SalaryDeletePopupComponent,
        resolve: {
            salary: SalaryResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Salaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
