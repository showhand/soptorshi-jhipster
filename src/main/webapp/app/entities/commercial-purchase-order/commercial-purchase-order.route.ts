import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';
import { CommercialPurchaseOrderService } from './commercial-purchase-order.service';
import { CommercialPurchaseOrderComponent } from './commercial-purchase-order.component';
import { CommercialPurchaseOrderDetailComponent } from './commercial-purchase-order-detail.component';
import { CommercialPurchaseOrderUpdateComponent } from './commercial-purchase-order-update.component';
import { CommercialPurchaseOrderDeletePopupComponent } from './commercial-purchase-order-delete-dialog.component';
import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';

@Injectable({ providedIn: 'root' })
export class CommercialPurchaseOrderResolve implements Resolve<ICommercialPurchaseOrder> {
    constructor(private service: CommercialPurchaseOrderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialPurchaseOrder> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialPurchaseOrder>) => response.ok),
                map((commercialPurchaseOrder: HttpResponse<CommercialPurchaseOrder>) => commercialPurchaseOrder.body)
            );
        }
        return of(new CommercialPurchaseOrder());
    }
}

export const commercialPurchaseOrderRoute: Routes = [
    {
        path: '',
        component: CommercialPurchaseOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPurchaseOrderDetailComponent,
        resolve: {
            commercialPurchaseOrder: CommercialPurchaseOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPurchaseOrderUpdateComponent,
        resolve: {
            commercialPurchaseOrder: CommercialPurchaseOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPurchaseOrderUpdateComponent,
        resolve: {
            commercialPurchaseOrder: CommercialPurchaseOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPurchaseOrderPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPurchaseOrderDeletePopupComponent,
        resolve: {
            commercialPurchaseOrder: CommercialPurchaseOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
