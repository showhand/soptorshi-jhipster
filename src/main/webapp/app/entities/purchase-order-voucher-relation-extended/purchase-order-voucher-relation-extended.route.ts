import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PurchaseOrderVoucherRelation } from 'app/shared/model/purchase-order-voucher-relation.model';
import { PurchaseOrderVoucherRelationExtendedService } from './purchase-order-voucher-relation-extended.service';
import { PurchaseOrderVoucherRelationExtendedComponent } from './purchase-order-voucher-relation-extended.component';
import { PurchaseOrderVoucherRelationExtendedDetailComponent } from './purchase-order-voucher-relation-extended-detail.component';
import { PurchaseOrderVoucherRelationExtendedUpdateComponent } from './purchase-order-voucher-relation-extended-update.component';
import { IPurchaseOrderVoucherRelation } from 'app/shared/model/purchase-order-voucher-relation.model';
import { PurchaseOrderVoucherRelationExtendedDeletePopUpComponent } from 'app/entities/purchase-order-voucher-relation-extended/purchase-order-voucher-relation-extended-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class PurchaseOrderVoucherRelationResolve implements Resolve<IPurchaseOrderVoucherRelation> {
    constructor(private service: PurchaseOrderVoucherRelationExtendedService) {}

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

export const purchaseOrderVoucherRelationExtendedRoute: Routes = [
    {
        path: 'extended/root',
        component: PurchaseOrderVoucherRelationExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderVoucherRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'extended/:id/view',
        component: PurchaseOrderVoucherRelationExtendedDetailComponent,
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
        path: 'extended/new',
        component: PurchaseOrderVoucherRelationExtendedUpdateComponent,
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
        path: 'extended/:id/edit',
        component: PurchaseOrderVoucherRelationExtendedUpdateComponent,
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
        path: 'extended/:id/delete',
        component: PurchaseOrderVoucherRelationExtendedDeletePopUpComponent,
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
