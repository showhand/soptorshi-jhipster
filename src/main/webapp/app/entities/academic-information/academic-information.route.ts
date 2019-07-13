import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AcademicInformation } from 'app/shared/model/academic-information.model';
import { AcademicInformationService } from './academic-information.service';
import { AcademicInformationComponent } from './academic-information.component';
import { AcademicInformationDetailComponent } from './academic-information-detail.component';
import { AcademicInformationUpdateComponent } from './academic-information-update.component';
import { AcademicInformationDeletePopupComponent } from './academic-information-delete-dialog.component';
import { IAcademicInformation } from 'app/shared/model/academic-information.model';
import { Employee, IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { pipe } from 'rxjs/internal/util/pipe';

@Injectable({ providedIn: 'root' })
export class AcademicInformationResolve implements Resolve<IAcademicInformation> {
    constructor(private service: AcademicInformationService, private employeeService: EmployeeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAcademicInformation> {
        const academicInformation = new AcademicInformation();
        const id = route.params['id'] ? route.params['id'] : null;
        const employeeId = route.params['employeeId'] ? route.params['employeeId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AcademicInformation>) => response.ok),
                map((ai: HttpResponse<AcademicInformation>) => ai.body)
            );
        } else if (employeeId) {
            academicInformation.employeeId = employeeId;
            return of(academicInformation);
        }
        return of(academicInformation);
    }
}

export const academicInformationRoute: Routes = [
    {
        path: '',
        component: AcademicInformationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'AcademicInformations',
            breadcrumb: 'Academic Information'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AcademicInformationDetailComponent,
        resolve: {
            academicInformation: AcademicInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'AcademicInformations',
            breadcrumb: 'Academic Information Details'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeId/employee-based-view',
        component: AcademicInformationDetailComponent,
        resolve: {
            academicInformation: AcademicInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'AcademicInformations',
            breadcrumb: 'Academic Information of Employee'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AcademicInformationUpdateComponent,
        resolve: {
            academicInformation: AcademicInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'AcademicInformations',
            breadcrumb: 'New Academic Information'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeId/new-for-employee',
        component: AcademicInformationUpdateComponent,
        resolve: {
            academicInformation: AcademicInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'AcademicInformations',
            breadcrumb: 'New Employee Academic Information'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AcademicInformationUpdateComponent,
        resolve: {
            academicInformation: AcademicInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'AcademicInformations',
            breadcrumb: 'Edit Academic Information'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const academicInformationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AcademicInformationDeletePopupComponent,
        resolve: {
            academicInformation: AcademicInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'AcademicInformations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
