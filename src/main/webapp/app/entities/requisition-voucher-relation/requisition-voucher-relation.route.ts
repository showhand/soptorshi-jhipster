import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';
import { RequisitionVoucherRelationService } from './requisition-voucher-relation.service';
import { RequisitionVoucherRelationComponent } from './requisition-voucher-relation.component';
import { RequisitionVoucherRelationDetailComponent } from './requisition-voucher-relation-detail.component';
import { RequisitionVoucherRelationUpdateComponent } from './requisition-voucher-relation-update.component';
import { RequisitionVoucherRelationDeletePopupComponent } from './requisition-voucher-relation-delete-dialog.component';
import { IRequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';

@Injectable({ providedIn: 'root' })
export class RequisitionVoucherRelationResolve implements Resolve<IRequisitionVoucherRelation> {
    constructor(private service: RequisitionVoucherRelationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRequisitionVoucherRelation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RequisitionVoucherRelation>) => response.ok),
                map((requisitionVoucherRelation: HttpResponse<RequisitionVoucherRelation>) => requisitionVoucherRelation.body)
            );
        }
        return of(new RequisitionVoucherRelation());
    }
}

export const requisitionVoucherRelationRoute: Routes = [
    {
        path: '',
        component: RequisitionVoucherRelationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RequisitionVoucherRelationDetailComponent,
        resolve: {
            requisitionVoucherRelation: RequisitionVoucherRelationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RequisitionVoucherRelationUpdateComponent,
        resolve: {
            requisitionVoucherRelation: RequisitionVoucherRelationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RequisitionVoucherRelationUpdateComponent,
        resolve: {
            requisitionVoucherRelation: RequisitionVoucherRelationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requisitionVoucherRelationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RequisitionVoucherRelationDeletePopupComponent,
        resolve: {
            requisitionVoucherRelation: RequisitionVoucherRelationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionVoucherRelations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
