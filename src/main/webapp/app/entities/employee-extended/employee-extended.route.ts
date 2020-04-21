import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Employee } from 'app/shared/model/employee.model';
import { EmployeeExtendedService } from './employee-extended.service';
import { EmployeeExtendedComponent } from './employee-extended.component';
import { EmployeeExtendedDetailComponent } from './employee-extended-detail.component';
import { EmployeeExtendedUpdateComponent } from './employee-extended-update.component';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeManagementComponent } from 'app/entities/employee/employee-management.component';
import { EmployeeDeletePopupComponent } from 'app/entities/employee/employee-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class EmployeeExtendedResolve implements Resolve<IEmployee> {
    constructor(private service: EmployeeExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmployee> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Employee>) => response.ok),
                map((employee: HttpResponse<Employee>) => employee.body)
            );
        }
        return of(new Employee());
    }
}

export const employeeExtendedRoute: Routes = [
    {
        path: '',
        component: EmployeeExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            defaultSort: 'id,desc',
            pageTitle: 'Employees',
            breadcrumb: 'Employee'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new-employee-management',
        component: EmployeeManagementComponent,
        resolve: {
            employee: EmployeeExtendedResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'Employee Management',
            breadcrumb: 'New Employee'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/employee-management',
        component: EmployeeManagementComponent,
        resolve: {
            employee: EmployeeExtendedResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'Employee Management',
            breadcrumb: 'Employee Information Management'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EmployeeExtendedDetailComponent,
        resolve: {
            employee: EmployeeExtendedResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'Employees',
            breadcrumb: 'View Employee Information'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EmployeeExtendedUpdateComponent,
        resolve: {
            employee: EmployeeExtendedResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'Employees',
            breadcrumb: 'New Employee'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EmployeeExtendedUpdateComponent,
        resolve: {
            employee: EmployeeExtendedResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'Employees',
            breadcrumb: 'Edit Employee'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EmployeeDeletePopupComponent,
        resolve: {
            employee: EmployeeExtendedResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'Employees'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
