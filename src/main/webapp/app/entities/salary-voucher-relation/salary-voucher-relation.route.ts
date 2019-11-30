import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SalaryVoucherRelation } from 'app/shared/model/salary-voucher-relation.model';
import { SalaryVoucherRelationService } from './salary-voucher-relation.service';
import { SalaryVoucherRelationComponent } from './salary-voucher-relation.component';
import { SalaryVoucherRelationDetailComponent } from './salary-voucher-relation-detail.component';
import { SalaryVoucherRelationUpdateComponent } from './salary-voucher-relation-update.component';
import { SalaryVoucherRelationDeletePopupComponent } from './salary-voucher-relation-delete-dialog.component';
import { ISalaryVoucherRelation } from 'app/shared/model/salary-voucher-relation.model';

@Injectable({ providedIn: 'root' })
export class SalaryVoucherRelationResolve implements Resolve<ISalaryVoucherRelation> {
    constructor(private service: SalaryVoucherRelationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISalaryVoucherRelation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SalaryVoucherRelation>) => response.ok),
                map((salaryVoucherRelation: HttpResponse<SalaryVoucherRelation>) => salaryVoucherRelation.body)
            );
        }
        return of(new SalaryVoucherRelation());
    }
}

export const salaryVoucherRelationRoute: Routes = [
    {
        path: '',
        component: SalaryVoucherRelationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SalaryVoucherRelationDetailComponent,
        resolve: {
            salaryVoucherRelation: SalaryVoucherRelationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SalaryVoucherRelationUpdateComponent,
        resolve: {
            salaryVoucherRelation: SalaryVoucherRelationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SalaryVoucherRelationUpdateComponent,
        resolve: {
            salaryVoucherRelation: SalaryVoucherRelationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const salaryVoucherRelationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SalaryVoucherRelationDeletePopupComponent,
        resolve: {
            salaryVoucherRelation: SalaryVoucherRelationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryVoucherRelations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
