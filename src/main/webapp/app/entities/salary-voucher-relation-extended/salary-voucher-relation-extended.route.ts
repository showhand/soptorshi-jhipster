import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MonthType, SalaryVoucherRelation } from 'app/shared/model/salary-voucher-relation.model';
import { SalaryVoucherRelationExtendedService } from './salary-voucher-relation-extended.service';
import { SalaryVoucherRelationExtendedComponent } from './salary-voucher-relation-extended.component';
import { SalaryVoucherRelationExtendedDetailComponent } from './salary-voucher-relation-extended-detail.component';
import { SalaryVoucherRelationExtendedUpdateComponent } from './salary-voucher-relation-extended-update.component';
import { ISalaryVoucherRelation } from 'app/shared/model/salary-voucher-relation.model';
import { Salary } from 'app/shared/model/salary.model';

@Injectable({ providedIn: 'root' })
export class SalaryVoucherRelationExtendedResolve implements Resolve<ISalaryVoucherRelation> {
    constructor(private service: SalaryVoucherRelationExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISalaryVoucherRelation> {
        const id = route.params['id'] ? route.params['id'] : null;
        const officeId = route.params['officeId'] ? route.params['officeId'] : null;
        const year = route.params['year'] ? route.params['year'] : null;
        const monthType = route.params['monthType'] ? route.params['monthType'] : null;

        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SalaryVoucherRelation>) => response.ok),
                map((salaryVoucherRelation: HttpResponse<SalaryVoucherRelation>) => salaryVoucherRelation.body)
            );
        } else if (officeId && year && monthType) {
            let salaryVoucherRelation = new SalaryVoucherRelation();
            salaryVoucherRelation.officeId = officeId;
            salaryVoucherRelation.year = year;
            salaryVoucherRelation.month = monthType;
            return of(salaryVoucherRelation);
        }
        return of(new SalaryVoucherRelation());
    }
}

export const salaryVoucherRelationExtendedRoute: Routes = [
    {
        path: '',
        component: SalaryVoucherRelationExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':officeId/:year/:monthType/home',
        component: SalaryVoucherRelationExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryVoucherRelations'
        },
        resolve: {
            salaryVoucherRelation: SalaryVoucherRelationExtendedResolve
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SalaryVoucherRelationExtendedDetailComponent,
        resolve: {
            salaryVoucherRelation: SalaryVoucherRelationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SalaryVoucherRelationExtendedUpdateComponent,
        resolve: {
            salaryVoucherRelation: SalaryVoucherRelationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':officeId/:year/:monthType/new',
        component: SalaryVoucherRelationExtendedUpdateComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryVoucherRelations'
        },
        resolve: {
            salaryVoucherRelation: SalaryVoucherRelationExtendedResolve
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SalaryVoucherRelationExtendedUpdateComponent,
        resolve: {
            salaryVoucherRelation: SalaryVoucherRelationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    }
];
