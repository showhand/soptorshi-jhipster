import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MonthlySalary } from 'app/shared/model/monthly-salary.model';
import { MonthlySalaryService } from './monthly-salary.service';
import { MonthlySalaryComponent } from './monthly-salary.component';
import { MonthlySalaryDetailComponent } from './monthly-salary-detail.component';
import { MonthlySalaryUpdateComponent } from './monthly-salary-update.component';
import { MonthlySalaryDeletePopupComponent } from './monthly-salary-delete-dialog.component';
import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';

@Injectable({ providedIn: 'root' })
export class MonthlySalaryResolve implements Resolve<IMonthlySalary> {
    constructor(private service: MonthlySalaryService) {}

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

export const monthlySalaryRoute: Routes = [
    {
        path: '',
        component: MonthlySalaryComponent,
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
        component: MonthlySalaryDetailComponent,
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
        component: MonthlySalaryUpdateComponent,
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
        component: MonthlySalaryUpdateComponent,
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

export const monthlySalaryPopupRoute: Routes = [
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
