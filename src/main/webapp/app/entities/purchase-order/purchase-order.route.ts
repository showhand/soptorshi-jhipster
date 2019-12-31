import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderService } from './purchase-order.service';
import { PurchaseOrderComponent } from './purchase-order.component';
import { PurchaseOrderDetailComponent } from './purchase-order-detail.component';
import { PurchaseOrderUpdateComponent } from './purchase-order-update.component';
import { PurchaseOrderDeletePopupComponent } from './purchase-order-delete-dialog.component';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';

@Injectable({ providedIn: 'root' })
export class PurchaseOrderResolve implements Resolve<IPurchaseOrder> {
    constructor(private service: PurchaseOrderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPurchaseOrder> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PurchaseOrder>) => response.ok),
                map((purchaseOrder: HttpResponse<PurchaseOrder>) => purchaseOrder.body)
            );
        }
        return of(new PurchaseOrder());
    }
}

export const purchaseOrderRoute: Routes = [
    {
        path: '',
        component: PurchaseOrderComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'PurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PurchaseOrderDetailComponent,
        resolve: {
            purchaseOrder: PurchaseOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PurchaseOrderUpdateComponent,
        resolve: {
            purchaseOrder: PurchaseOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PurchaseOrderUpdateComponent,
        resolve: {
            purchaseOrder: PurchaseOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const purchaseOrderPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PurchaseOrderDeletePopupComponent,
        resolve: {
            purchaseOrder: PurchaseOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
