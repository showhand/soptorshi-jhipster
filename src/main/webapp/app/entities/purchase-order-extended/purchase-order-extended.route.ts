import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map, switchMap } from 'rxjs/operators';
import { PurchaseOrder } from 'app/shared/model/purchase-order.model';

import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderExtendedComponent } from 'app/entities/purchase-order-extended/purchase-order-extended.component';
import { PurchaseOrderExtendedDetailComponent } from 'app/entities/purchase-order-extended/purchase-order-extended-detail.component';
import { PurchaseOrderExtendedUpdateComponent } from 'app/entities/purchase-order-extended/purchase-order-extended-update.component';
import { PurchaseOrderDeletePopupComponent } from 'app/entities/purchase-order';
import { PurchaseOrderExtendedService } from 'app/entities/purchase-order-extended/purchase-order-extended.service';

@Injectable({ providedIn: 'root' })
export class PurchaseOrderExtendedResolve implements Resolve<IPurchaseOrder> {
    constructor(private service: PurchaseOrderExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPurchaseOrder> {
        const id = route.params['id'] ? route.params['id'] : null;
        const requisitionId = route.params['requisitionId'] ? route.params['requisitionId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PurchaseOrder>) => response.ok),
                map((purchaseOrder: HttpResponse<PurchaseOrder>) => purchaseOrder.body)
            );
        }
        if (requisitionId) {
            const purchaseOrder = new PurchaseOrder();
            purchaseOrder.requisitionId = requisitionId;
            return of(purchaseOrder);
        }
        return of(new PurchaseOrder());
    }
}

export const purchaseOrderExtendedRoute: Routes = [
    {
        path: '',
        component: PurchaseOrderExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,desc',
            pageTitle: 'PurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PurchaseOrderExtendedDetailComponent,
        resolve: {
            purchaseOrder: PurchaseOrderExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PurchaseOrderExtendedUpdateComponent,
        resolve: {
            purchaseOrder: PurchaseOrderExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PurchaseOrderExtendedUpdateComponent,
        resolve: {
            purchaseOrder: PurchaseOrderExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':requisitionId/create',
        component: PurchaseOrderExtendedUpdateComponent,
        resolve: {
            purchaseOrder: PurchaseOrderExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const purchaseOrderExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PurchaseOrderDeletePopupComponent,
        resolve: {
            purchaseOrder: PurchaseOrderExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
