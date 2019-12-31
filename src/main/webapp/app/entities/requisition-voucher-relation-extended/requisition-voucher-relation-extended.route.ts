import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';
import { RequisitionVoucherRelationExtendedService } from './requisition-voucher-relation-extended.service';
import { RequisitionVoucherRelationExtendedComponent } from './requisition-voucher-relation-extended.component';
import { RequisitionVoucherRelationExtendedDetailComponent } from './requisition-voucher-relation-extended-detail.component';
import { RequisitionVoucherRelationExtendedUpdateComponent } from './requisition-voucher-relation-extended-update.component';
import { IRequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';

@Injectable({ providedIn: 'root' })
export class RequisitionVoucherRelationExtendedResolve implements Resolve<IRequisitionVoucherRelation> {
    constructor(private service: RequisitionVoucherRelationExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRequisitionVoucherRelation> {
        const id = route.params['id'] ? route.params['id'] : null;
        const requisitionId = route.params['requisitionId'] ? route.params['requisitionId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RequisitionVoucherRelation>) => response.ok),
                map((requisitionVoucherRelation: HttpResponse<RequisitionVoucherRelation>) => requisitionVoucherRelation.body)
            );
        } else if (requisitionId) {
            const requisitionVoucherRelation = new RequisitionVoucherRelation();
            requisitionVoucherRelation.requisitionId = requisitionId;
            return of(requisitionVoucherRelation);
        } else {
            return of(new RequisitionVoucherRelation());
        }
    }
}

export const requisitionVoucherRelationExtendedRoute: Routes = [
    {
        path: '',
        component: RequisitionVoucherRelationExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RequisitionVoucherRelationExtendedDetailComponent,
        resolve: {
            requisitionVoucherRelation: RequisitionVoucherRelationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RequisitionVoucherRelationExtendedUpdateComponent,
        resolve: {
            requisitionVoucherRelation: RequisitionVoucherRelationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':requisitionId/new',
        component: RequisitionVoucherRelationExtendedUpdateComponent,
        resolve: {
            requisitionVoucherRelation: RequisitionVoucherRelationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RequisitionVoucherRelationExtendedUpdateComponent,
        resolve: {
            requisitionVoucherRelation: RequisitionVoucherRelationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    }
];
