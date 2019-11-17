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

@Injectable({ providedIn: 'root' })
export class SalaryResolve implements Resolve<ISalary> {
    constructor(private service: SalaryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISalary> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Salary>) => response.ok),
                map((salary: HttpResponse<Salary>) => salary.body)
            );
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
            authorities: ['ROLE_USER'],
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
            authorities: ['ROLE_USER'],
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
            authorities: ['ROLE_USER'],
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
            authorities: ['ROLE_USER'],
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
            authorities: ['ROLE_USER'],
            pageTitle: 'Salaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
