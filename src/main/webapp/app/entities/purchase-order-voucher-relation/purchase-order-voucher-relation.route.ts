import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PurchaseOrderVoucherRelation } from 'app/shared/model/purchase-order-voucher-relation.model';
import { PurchaseOrderVoucherRelationService } from './purchase-order-voucher-relation.service';
import { PurchaseOrderVoucherRelationComponent } from './purchase-order-voucher-relation.component';
import { PurchaseOrderVoucherRelationDetailComponent } from './purchase-order-voucher-relation-detail.component';
import { PurchaseOrderVoucherRelationUpdateComponent } from './purchase-order-voucher-relation-update.component';
import { PurchaseOrderVoucherRelationDeletePopupComponent } from './purchase-order-voucher-relation-delete-dialog.component';
import { IPurchaseOrderVoucherRelation } from 'app/shared/model/purchase-order-voucher-relation.model';

@Injectable({ providedIn: 'root' })
export class PurchaseOrderVoucherRelationResolve implements Resolve<IPurchaseOrderVoucherRelation> {
    constructor(private service: PurchaseOrderVoucherRelationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPurchaseOrderVoucherRelation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PurchaseOrderVoucherRelation>) => response.ok),
                map((purchaseOrderVoucherRelation: HttpResponse<PurchaseOrderVoucherRelation>) => purchaseOrderVoucherRelation.body)
            );
        }
        return of(new PurchaseOrderVoucherRelation());
    }
}

export const purchaseOrderVoucherRelationRoute: Routes = [
    {
        path: '',
        component: PurchaseOrderVoucherRelationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PurchaseOrderVoucherRelationDetailComponent,
        resolve: {
            purchaseOrderVoucherRelation: PurchaseOrderVoucherRelationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PurchaseOrderVoucherRelationUpdateComponent,
        resolve: {
            purchaseOrderVoucherRelation: PurchaseOrderVoucherRelationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PurchaseOrderVoucherRelationUpdateComponent,
        resolve: {
            purchaseOrderVoucherRelation: PurchaseOrderVoucherRelationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const purchaseOrderVoucherRelationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PurchaseOrderVoucherRelationDeletePopupComponent,
        resolve: {
            purchaseOrderVoucherRelation: PurchaseOrderVoucherRelationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderVoucherRelations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
