import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DepartmentHead } from 'app/shared/model/department-head.model';
import { DepartmentHeadService } from './department-head.service';
import { DepartmentHeadComponent } from './department-head.component';
import { DepartmentHeadDetailComponent } from './department-head-detail.component';
import { DepartmentHeadUpdateComponent } from './department-head-update.component';
import { DepartmentHeadDeletePopupComponent } from './department-head-delete-dialog.component';
import { IDepartmentHead } from 'app/shared/model/department-head.model';

@Injectable({ providedIn: 'root' })
export class DepartmentHeadResolve implements Resolve<IDepartmentHead> {
    constructor(private service: DepartmentHeadService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDepartmentHead> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DepartmentHead>) => response.ok),
                map((departmentHead: HttpResponse<DepartmentHead>) => departmentHead.body)
            );
        }
        return of(new DepartmentHead());
    }
}

export const departmentHeadRoute: Routes = [
    {
        path: '',
        component: DepartmentHeadComponent,
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
        component: DepartmentHeadDetailComponent,
        resolve: {
            departmentHead: DepartmentHeadResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepartmentHeads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DepartmentHeadUpdateComponent,
        resolve: {
            departmentHead: DepartmentHeadResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepartmentHeads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DepartmentHeadUpdateComponent,
        resolve: {
            departmentHead: DepartmentHeadResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepartmentHeads'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const departmentHeadPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DepartmentHeadDeletePopupComponent,
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
