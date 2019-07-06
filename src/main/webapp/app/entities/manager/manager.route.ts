import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Manager } from 'app/shared/model/manager.model';
import { ManagerService } from './manager.service';
import { ManagerComponent } from './manager.component';
import { ManagerDetailComponent } from './manager-detail.component';
import { ManagerUpdateComponent } from './manager-update.component';
import { ManagerDeletePopupComponent } from './manager-delete-dialog.component';
import { IManager } from 'app/shared/model/manager.model';

@Injectable({ providedIn: 'root' })
export class ManagerResolve implements Resolve<IManager> {
    constructor(private service: ManagerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IManager> {
        const id = route.params['id'] ? route.params['id'] : null;
        const employeeId = route.params['employeeLongId'] ? route.params['employeeLongId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Manager>) => response.ok),
                map((manager: HttpResponse<Manager>) => manager.body)
            );
        } else if (employeeId) {
            const manager = new Manager();
            manager.parentEmployeeId = employeeId;
            return of(manager);
        }
        return of(new Manager());
    }
}

export const managerRoute: Routes = [
    {
        path: '',
        component: ManagerComponent,
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'Managers',
            breadcrumb: 'Managers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ManagerDetailComponent,
        resolve: {
            manager: ManagerResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'Managers',
            breadcrumb: 'View Manager'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeLongId/new',
        component: ManagerUpdateComponent,
        resolve: {
            manager: ManagerResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'Managers',
            breadcrumb: 'New Manager For Employee'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ManagerUpdateComponent,
        resolve: {
            manager: ManagerResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'Managers',
            breadcrumb: 'New Manager'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ManagerUpdateComponent,
        resolve: {
            manager: ManagerResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'Managers',
            breadcrumb: 'Edit Manager'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const managerPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ManagerDeletePopupComponent,
        resolve: {
            manager: ManagerResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'Managers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
