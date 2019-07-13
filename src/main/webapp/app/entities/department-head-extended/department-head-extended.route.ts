import { Routes } from '@angular/router';
import {
    DepartmentHeadComponent,
    DepartmentHeadDeletePopupComponent,
    DepartmentHeadDetailComponent,
    DepartmentHeadResolve,
    DepartmentHeadUpdateComponent
} from 'app/entities/department-head';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { DepartmentHeadExtendedComponent } from 'app/entities/department-head-extended/department-head-extended.component';
import { DepartmentHeadExtendedDetailsComponent } from 'app/entities/department-head-extended/department-head-extended-details.component';
import { DepartmentHeadExtendedUpdateComponent } from 'app/entities/department-head-extended/department-head-extended-update.component';
import {
    DepartmentHeadExtendedDeleteDialogComponent,
    DepartmentHeadExtendedDeletePopupComponent
} from 'app/entities/department-head-extended/department-head-extended-delete-dialog.component';

export const departmentHeadExtendedRoute: Routes = [
    {
        path: '',
        component: DepartmentHeadExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'DepartmentHeads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DepartmentHeadExtendedDetailsComponent,
        resolve: {
            departmentHead: DepartmentHeadResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepartmentHeads',
            breadcrumb: 'Department Head Details'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DepartmentHeadExtendedUpdateComponent,
        resolve: {
            departmentHead: DepartmentHeadResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepartmentHeads',
            breadcrumb: 'New Department Head'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DepartmentHeadExtendedUpdateComponent,
        resolve: {
            departmentHead: DepartmentHeadResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepartmentHeads',
            breadcrumb: 'Edit Department Head'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const departmentHeadPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DepartmentHeadExtendedDeletePopupComponent,
        resolve: {
            departmentHead: DepartmentHeadResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepartmentHeads'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
