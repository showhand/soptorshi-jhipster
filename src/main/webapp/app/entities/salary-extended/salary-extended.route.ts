import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Salary } from 'app/shared/model/salary.model';
import { SalaryExtendedService } from './salary-extended.service';
import { SalaryExtendedComponent } from './salary-extended.component';
import { SalaryExtendedDetailComponent } from './salary-extended-detail.component';
import { SalaryExtendedUpdateComponent } from './salary-extended-update.component';
import { ISalary } from 'app/shared/model/salary.model';
import { IProvidentFund, ProvidentFund } from 'app/shared/model/provident-fund.model';
import { SalaryExtendedDeletePopupComponent } from 'app/entities/salary-extended/salary-extended-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class SalaryExtendedResolve implements Resolve<ISalary> {
    constructor(private service: SalaryExtendedService) {}

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

export const salaryExtendedRoute: Routes = [
    {
        path: '',
        component: SalaryExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Salaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeLongId/employee',
        component: SalaryExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams,
            salary: SalaryExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Salaries',
            breadcrumb: 'Employee Salary Information'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SalaryExtendedDetailComponent,
        resolve: {
            salary: SalaryExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Salaries',
            breadcrumb: 'Salary Details'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeId/new',
        component: SalaryExtendedUpdateComponent,
        resolve: {
            salary: SalaryExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Salaries',
            breadcrumb: 'New Salary'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SalaryExtendedUpdateComponent,
        resolve: {
            salary: SalaryExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Salaries',
            breadcrumb: 'New Salary'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SalaryExtendedUpdateComponent,
        resolve: {
            salary: SalaryExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Salaries',
            breadcrumb: 'Edit Salary'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const salaryExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SalaryExtendedDeletePopupComponent,
        resolve: {
            salary: SalaryExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Salaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
